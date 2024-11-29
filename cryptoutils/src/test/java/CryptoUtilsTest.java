import cat.uvic.teknos.f1race.cryptoutils.CryptoUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class CryptoUtilsTest {

    @Test
    void testSymmetricEncryption() throws Exception {
        System.out.println("Generant clau secreta per a xifrat simètric...");
        SecretKey secretKey = CryptoUtils.createSecretKey();
        System.out.println("Clau secreta generada: " + CryptoUtils.toBase64(secretKey.getEncoded()));

        String plainText = "Hello World! This is a test message.";
        System.out.println("Text original: " + plainText);

        System.out.println("Xifrant el text...");
        String encryptedText = CryptoUtils.encrypt(plainText, secretKey);
        System.out.println("Text xifrat: " + encryptedText);
        assertNotNull(encryptedText, "El text xifrat no hauria de ser nul");

        System.out.println("Desxifrant el text...");
        String decryptedText = CryptoUtils.decrypt(encryptedText, secretKey);
        System.out.println("Text desxifrat: " + decryptedText);
        assertEquals(plainText, decryptedText, "El text desxifrat ha de coincidir amb el text original");
    }

    @Test
    void testAsymmetricEncryption() throws NoSuchAlgorithmException {
        System.out.println("Generant parell de claus RSA...");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        System.out.println("Clau pública: " + CryptoUtils.toBase64(keyPair.getPublic().getEncoded()));
        System.out.println("Clau privada: " + CryptoUtils.toBase64(keyPair.getPrivate().getEncoded()));

        String plainText = "Hello World! This is a test message.";
        System.out.println("Text original: " + plainText);

        System.out.println("Xifrant amb la clau pública...");
        String encryptedText = CryptoUtils.asymmetricEncrypt(CryptoUtils.toBase64(plainText.getBytes()), keyPair.getPublic());
        System.out.println("Text xifrat (Base64): " + encryptedText);
        assertNotNull(encryptedText, "El text xifrat no hauria de ser nul");

        System.out.println("Desxifrant amb la clau privada...");
        String decryptedTextBase64 = CryptoUtils.asymmetricDecrypt(encryptedText, keyPair.getPrivate());
        String decryptedText = new String(CryptoUtils.fromBase64(decryptedTextBase64));
        System.out.println("Text desxifrat: " + decryptedText);
        assertEquals(plainText, decryptedText, "El text desxifrat ha de coincidir amb el text original");
    }

    @Test
    void testGetHash() {
        var text = "Some text...";
        System.out.println("Calculant hash per al text: " + text);
        var expectedBase64Hash = "quonJ6BjRSC1DBOGuBWNdqixj8z20nuP+QH7cVvp7PI=";

        String hash = CryptoUtils.getHash(text);
        System.out.println("Hash calculat: " + hash);
        assertEquals(expectedBase64Hash, hash, "El hash ha de coincidir amb el valor esperat codificat en Base64");
    }
}
