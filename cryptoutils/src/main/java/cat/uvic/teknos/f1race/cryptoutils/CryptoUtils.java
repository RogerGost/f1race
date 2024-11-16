package cat.uvic.teknos.f1race.cryptoutils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CryptoUtils {
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static String getHash(String text) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var hash = digest.digest(text.getBytes());

            return toBase64(hash);
        }catch (NoSuchAlgorithmException e) {
            throw new CryptoException("Error while generating hash", e);
        }
    }

    public static SecretKey createSecretKey() throws NoSuchAlgorithmException {
        try {
            return KeyGenerator.getInstance("AES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }

    public static SecretKey decodeSecreteKey(String base64SecretKey) {
        var bytes = decoder.decode(base64SecretKey);

        return new SecretKeySpec(bytes, 0, bytes.length, "AES");
    }

    public String encrypt(String plaintext, SecretKey key) {
        try {
            var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            var iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            var ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            var encryptedBytes = cipher.doFinal(plaintext.getBytes());

            // Combine IV and ciphertext to ensure decryption
            var combined = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

            return toBase64(combined);
        } catch (Exception e) {
            throw new CryptoException("Error during encryption", e);
        }
    }

    public String decrypt(String ciphertextBase64, SecretKey key) {
        try {
            var combined = fromBase64(ciphertextBase64);
            var iv = new byte[16];
            System.arraycopy(combined, 0, iv, 0, 16);
            var ivSpec = new IvParameterSpec(iv);

            var encryptedBytes = new byte[combined.length - 16];
            System.arraycopy(combined, 16, encryptedBytes, 0, encryptedBytes.length);

            var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            var decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new CryptoException("Error during decryption", e);
        }
    }

    public String asymmetricEncrypt(String plaintextBase64, Key key) {
        try {
            var cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            var encryptedBytes = cipher.doFinal(plaintextBase64.getBytes());
            return toBase64(encryptedBytes);
        } catch (Exception e) {
            throw new CryptoException("Error during asymmetric encryption", e);
        }
    }

    public String asymmetricDecrypt(String ciphertextBase64, Key key) {
        try {
            var cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            var decryptedBytes = cipher.doFinal(fromBase64(ciphertextBase64));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new CryptoException("Error during asymmetric decryption", e);
        }
    }
    private static String toBase64(byte[] bytes) {
        return encoder.encodeToString(bytes);
    }

    private static byte[] fromBase64(String base64) {
        return decoder.decode(base64);
    }
}
