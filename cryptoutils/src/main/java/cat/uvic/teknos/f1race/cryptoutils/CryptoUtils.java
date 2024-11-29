package cat.uvic.teknos.f1race.cryptoutils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class CryptoUtils {
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static String getHash(String text) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var hash = digest.digest(text.getBytes());

            return toBase64(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException();
        }
    }

    public static SecretKey createSecretKey() {
        try {
            return KeyGenerator.getInstance("AES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }

    public static SecretKey decodeSecretKey(String base64SecretKey) {
        var bytes = decoder.decode(base64SecretKey);

        return new SecretKeySpec(bytes, 0, bytes.length, "AES");
    }

    public static String encrypt(String plainText, SecretKey key) {
        try {
            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

            byte[] encryptedData = cipher.doFinal(plainText.getBytes());

            byte[] encryptedWithIv = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
            System.arraycopy(encryptedData, 0, encryptedWithIv, iv.length, encryptedData.length);

            return toBase64(encryptedWithIv);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new CryptoException(e);
        }
    }

    public static String decrypt(String encryptedTextBase64, SecretKey key) {
        try {
            byte[] encryptedDataWithIv = fromBase64(encryptedTextBase64);

            byte[] iv = new byte[16];
            System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);

            byte[] encryptedData = new byte[encryptedDataWithIv.length - iv.length];
            System.arraycopy(encryptedDataWithIv, iv.length, encryptedData, 0, encryptedData.length);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            byte[] decryptedData = cipher.doFinal(encryptedData);
            return new String(decryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new CryptoException(e);
        }
    }

    public static String asymmetricEncrypt(String plainTextBase64, Key key) {
        try {
            var cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return toBase64(cipher.doFinal(fromBase64(plainTextBase64)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new CryptoException(e);
        }
    }

    public static String asymmetricDecrypt(String encryptedTextBase64, Key key) {
        try {
            var cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return toBase64(cipher.doFinal(fromBase64(encryptedTextBase64)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new CryptoException(e);
        }
    }

    public static String toBase64(byte[] bytes) {
        return encoder.encodeToString(bytes);
    }

    public static byte[] fromBase64(String base64) {
        return decoder.decode(base64);
    }
}
