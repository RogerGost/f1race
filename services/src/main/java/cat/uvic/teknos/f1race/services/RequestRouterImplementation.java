package cat.uvic.teknos.f1race.services;

import cat.uvic.teknos.f1race.services.controllers.Controller;
import cat.uvic.teknos.f1race.services.exeption.ResourceNotFoundExeption;
import cat.uvic.teknos.f1race.services.exeption.ServerErrorExeption;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;

import cat.uvic.teknos.f1race.cryptoutils.CryptoUtils;

public class RequestRouterImplementation implements RequestRouter {
    private static final RawHttp rawHttp = new RawHttp();
    private final Map<String, Controller> controllers;
    private final PrivateKey serverPrivateKey;

    public RequestRouterImplementation(Map<String, Controller> controllers, PrivateKey serverPrivateKey) {
        this.controllers = controllers;
        this.serverPrivateKey = serverPrivateKey;
    }

    @Override
    public RawHttpResponse<?> execRequest(RawHttpRequest request) {
        var path = request.getUri().getPath();
        var pathParts = path.split("/");

        if (pathParts.length < 3) {
            return createJsonErrorResponse(404, "Controller not found.");
        }

        var controllerName = pathParts[2];
        var method = request.getMethod();
        String responseJsonBody = "";

        try {
            var controller = controllers.get(controllerName);
            if (controller == null) {
                throw new ResourceNotFoundExeption("Controller not found: " + controllerName);
            }

            List<String> symmetricKeyHeader = request.getHeaders().get("X-Symmetric-Key");
            if (symmetricKeyHeader == null || symmetricKeyHeader.isEmpty()) {
                return createJsonErrorResponse(400, "Missing X-Symmetric-Key header.");
            }

            String encryptedSymmetricKeyBase64 = symmetricKeyHeader.get(0);
            System.out.println("Encrypted Symmetric Key received: " + encryptedSymmetricKeyBase64);

            SecretKey symmetricKey = CryptoUtils.decodeSecretKey(
                    CryptoUtils.asymmetricDecrypt(encryptedSymmetricKeyBase64, serverPrivateKey)
            );
            System.out.println("Decrypted Symmetric Key: " + Base64.getEncoder().encodeToString(symmetricKey.getEncoded())); // Depuración

            List<String> bodyHashHeader = request.getHeaders().get("X-Body-Hash");
            if (bodyHashHeader == null || bodyHashHeader.isEmpty()) {
                return createJsonErrorResponse(400, "Missing X-Body-Hash header.");
            }

            String bodyHash = bodyHashHeader.get(0);
            byte[] encryptedBody = request.getBody().map(body -> {
                try {
                    return body.decodeBody();
                } catch (IOException e) {
                    throw new RuntimeException("Error decoding body", e);
                }
            }).orElse(new byte[0]);
            System.out.println("Encrypted body received: " + new String(encryptedBody));

            String calculatedHash = CryptoUtils.getHash(new String(encryptedBody));
            System.out.println("Calculated Hash of Body: " + calculatedHash);

            if (!calculatedHash.equals(bodyHash)) {
                return createJsonErrorResponse(400, "Invalid hash for the request body.");
            }

            String decryptedBody = CryptoUtils.decrypt(new String(encryptedBody), symmetricKey);
            System.out.println("Decrypted Body: " + decryptedBody);

            if ("POST".equals(method)) {
                controller.post(decryptedBody);
            } else if ("GET".equals(method)) {
                if (pathParts.length == 4) {
                    var resourceId = Integer.parseInt(pathParts[3]);
                    responseJsonBody = controller.get(resourceId);
                } else {
                    responseJsonBody = controller.get();
                }
            } else if ("PUT".equals(method)) {
                if (pathParts.length < 4) {
                    return createJsonErrorResponse(400, "Resource ID is required for PUT requests.");
                }
                var resourceId = Integer.parseInt(pathParts[3]);
                controller.put(resourceId, decryptedBody);
            } else if ("DELETE".equals(method)) {
                if (pathParts.length < 4) {
                    return createJsonErrorResponse(400, "Resource ID is required for DELETE requests.");
                }
                var resourceId = Integer.parseInt(pathParts[3]);
                controller.delete(resourceId);
            } else {
                return createJsonErrorResponse(405, "Method not allowed.");
            }

            if (responseJsonBody.isEmpty()) {
                responseJsonBody = "{\"message\": \"No content available\"}";
            }

            String encryptedResponseBody = CryptoUtils.encrypt(responseJsonBody, symmetricKey);
            String responseHash = CryptoUtils.getHash(encryptedResponseBody);
            System.out.println("Encrypted Response Body: " + encryptedResponseBody);
            System.out.println("Response Hash: " + responseHash);

            return rawHttp.parseResponse(
                    "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: application/json\r\n" +
                            "X-Body-Hash: " + responseHash + "\r\n" +
                            "Content-Length: " + encryptedResponseBody.length() + "\r\n" +
                            "\r\n" +
                            encryptedResponseBody
            );

        } catch (Exception e) {
            System.err.println("Error processing request: " + e.getMessage());
            e.printStackTrace();
            return createJsonErrorResponse(500, "Error processing request: " + e.getMessage());
        }
    }

    private RawHttpResponse<?> createJsonErrorResponse(int statusCode, String message) {
        String jsonResponse = "{\"error\": \"" + message + "\"}";
        return rawHttp.parseResponse(
                "HTTP/1.1 " + statusCode + " " + (statusCode == 404 ? "Not Found" : "Error") + "\r\n" +
                        "Content-Type: application/json\r\n" +
                        "Content-Length: " + jsonResponse.length() + "\r\n" +
                        "\r\n" +
                        jsonResponse
        );
    }
}
