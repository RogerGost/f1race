
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
    void testHashing() {
        String text = "Password123!";
        String hash1 = CryptoUtils.getHash(text);
        String hash2 = CryptoUtils.getHash(text);

        // Ensure hash values are consistent
        assertNotNull(hash1, "Hash should not be null");
        assertEquals(hash1, hash2, "Hashes for the same input should be identical");

        // Ensure different inputs produce different hashes
        String differentText = "DifferentPassword!";
        String differentHash = CryptoUtils.getHash(differentText);
        assertNotEquals(hash1, differentHash, "Hashes for different inputs should not be identical");
    }
}
