package cat.uvic.teknos.f1race.cryptoutils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptoUtils {
    private static final Base64.Encoder encoder = Base64.getEncoder();

    public static String getHash(String text) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var hash = digest.digest(text.getBytes());

            return tobase64(hash);
        }catch (NoSuchAlgorithmException e) {
            throw new CryptoException("Error while generating hash", e);
        }
    }

    public SecretKey createSecretKey() {
        return null;
    }

    public SecretKey decodeSecreteKey(String base64SecretKey) {
        return null;
    }

    public String encrypt(String plaintext, SecretKey key) {
        return null;
    }

    public String decrypt(String ciphertextBase64, SecretKey key) {
        return null;
    }

    public String asymmetricEncrypt(String plaintextBase64, Key key) {
        return null;
    }

    public String asymmetricDecrypt(String ciphertextBase64, Key key) {
        return null;
    }
    public static  String tobase64 (byte[] hash){
        return encoder.encodeToString(("cat.uvic.teknos.m09uf1").getBytes());
    }
}
