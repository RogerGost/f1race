package cat.uvic.teknos.f1race.clients.console.utils;

import cat.uvic.teknos.f1race.clients.console.exceptions.RequestException;
import rawhttp.core.RawHttp;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Base64;

import cat.uvic.teknos.f1race.cryptoutils.CryptoUtils;

public class RestClientImpl implements RestClient {
    private final int port;
    private final String host;
    private final PublicKey serverPublicKey;

    public RestClientImpl(String host, int port, PublicKey serverPublicKey) {
        this.host = host;
        this.port = port;
        this.serverPublicKey = serverPublicKey;
    }

    public <T> T get(String path, Class<T> returnType) throws RequestException {
        return execRequest("GET", path, null, returnType);
    }

    public <T> T[] getAll(String path, Class<T[]> returnType) throws RequestException {
        return execRequest("GET", path, null, returnType);
    }

    public void post(String path, String body) throws RequestException {
        execRequest("POST", path, body, Void.class);
    }

    public void put(String path, String body) throws RequestException {
        execRequest("PUT", path, body, Void.class);
    }

    public void delete(String path, String body) throws RequestException {
        execRequest("DELETE", path, body, Void.class);
    }

    protected <T> T execRequest(String method, String path, String body, Class<T> returnType) throws RequestException {
        var rawHttp = new RawHttp();
        try (var socket = new Socket(host, port)) {
            if (body == null) {
                body = "";
            }

            SecretKey symmetricKey = CryptoUtils.createSecretKey();
            String encryptedBody = CryptoUtils.encrypt(body, symmetricKey);
            System.out.println("Encrypted body: " + encryptedBody);

            String bodyHash = CryptoUtils.getHash(encryptedBody);
            System.out.println("Body Hash: " + bodyHash);

            String encryptedSymmetricKey = CryptoUtils.asymmetricEncrypt(Base64.getEncoder().encodeToString(symmetricKey.getEncoded()), serverPublicKey);
            System.out.println("Encrypted Symmetric Key: " + encryptedSymmetricKey);

            var request = rawHttp.parseRequest(
                    method + " " + String.format("http://%s:%d/%s", host, port, path) + " HTTP/1.1\r\n" +
                            "Host: " + host + "\r\n" +
                            "User-Agent: RawHTTP\r\n" +
                            "Content-Type: application/json\r\n" +
                            "X-Symmetric-Key: " + encryptedSymmetricKey + "\r\n" +
                            "X-Body-Hash: " + bodyHash + "\r\n" +
                            "Content-Length: " + encryptedBody.getBytes().length + "\r\n" +
                            "\r\n" +
                            encryptedBody
            );

            request.writeTo(socket.getOutputStream());

            var response = rawHttp.parseResponse(socket.getInputStream()).eagerly();

            int statusCode = response.getStatusCode();
            if (statusCode != 200) {
                throw new RequestException("Unexpected response status: " + statusCode);
            }

            String encryptedResponseBody = response.getBody()
                    .map(bodyPart -> {
                        try {
                            return new String(bodyPart.decodeBody(), "UTF-8");
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to decode response body", e);
                        }
                    })
                    .orElse("");
            if (encryptedResponseBody.isEmpty()) {
                throw new RequestException("Empty response body.");
            }

            String responseHash = response.getHeaders().getFirst("X-Body-Hash").orElse("");
            String calculatedHash = CryptoUtils.getHash(encryptedResponseBody);
            if (!calculatedHash.equals(responseHash)) {
                throw new RequestException("Invalid hash for the response body.");
            }
            System.out.println("Calculated Hash of Response Body: " + calculatedHash);

            String decryptedResponseBody = CryptoUtils.decrypt(encryptedResponseBody, symmetricKey);
            System.out.println("Decrypted Response Body: " + decryptedResponseBody);
            if (returnType != Void.class) {
                return Mappers.get().readValue(decryptedResponseBody, returnType);
            }

            return null;

        } catch (IOException e) {
            throw new RequestException("Request failed: " + e.getMessage(), e);
        }
    }
}
