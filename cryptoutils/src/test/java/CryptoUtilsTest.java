
import cat.uvic.teknos.f1race.cryptoutils.CryptoUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class CryptoUtilsTest {

    @Test
    void testSymmetricEncryption() throws NoSuchAlgorithmException {
        // Generate a secret key
        SecretKey secretKey = CryptoUtils.createSecretKey();

        String plainText = "Hello World! This is a test message.";

        // Encrypt the plaintext
        String encryptedText = CryptoUtils.encrypt(plainText, secretKey);
        assertNotNull(encryptedText, "Encrypted text should not be null");

        // Decrypt the ciphertext
        String decryptedText = CryptoUtils.decrypt(encryptedText, secretKey);
        assertEquals(plainText, decryptedText, "Decrypted text should match the original plaintext");
    }

    @Test
    void testAsymmetricEncryption() throws NoSuchAlgorithmException {
        // Generate a key pair for RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        String plainText = "Hello World! This is a test message.";

        // Encrypt the plaintext with the public key
        String encryptedText = CryptoUtils.asymmetricEncrypt(plainText, keyPair.getPublic());
        assertNotNull(encryptedText, "Encrypted text should not be null");

        // Decrypt the ciphertext with the private key
        String decryptedText = CryptoUtils.asymmetricDecrypt(encryptedText, keyPair.getPrivate());
        assertEquals(plainText, decryptedText, "Decrypted text should match the original plaintext");
    }

    @Test
    void getHash() {
        var text = "Some text...";
        var base64Text = "quonJ6BjRSC1DBOGuBWNdqixj8z20nuP+QH7cVvp7PI=";

        assertEquals(base64Text, CryptoUtils.getHash(text));
    }
}
