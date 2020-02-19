package assignment_1._11;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DecryptAES {

    private IvParameterSpec ivSpec;
    private SecretKeySpec sKeySpec;
    private Cipher cipher;


    DecryptAES(String iv, String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        sKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }

    private byte[] decodeHex(String hex){
        if (hex.length() % 2 != 0) throw new IllegalArgumentException("input need to be dividable 2");
        byte[] res = new byte[hex.length() / 2];

        for (int i = 0; i < hex.length(); i += 2) {
            int _1st = Character.digit(hex.charAt(i), 16);
            int _2nd = Character.digit(hex.charAt(i+1), 16);

            if (_1st == -1) throw new IllegalArgumentException("chars need to be hexadecimal: " + _1st);
            else if (_2nd == -1) throw new IllegalArgumentException("chars need to be hexadecimal: " + _2nd);
            res[i / 2] = (byte) ((_1st << 4) + _2nd);
        }
        return res;
    }

    public String decrypt(String message) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivSpec);
//        return new String(cipher.doFinal(Base64.getDecoder().decode(message)));
        return new String(cipher.doFinal(decodeHex(message)));
    }


    public static void main(String[] args) {

        try {
            DecryptAES aes = new DecryptAES("0000000000000000","0123456789abcdef");
            DecryptAES aes2 = new DecryptAES("0123456789abcdef", "0011223344556677");

            System.out.println(aes.decrypt("1ff4ec7cef0e00d81b2d55a4bfdad4ba"));
            System.out.println(aes2.decrypt("9e4816cc13810b8424d788fbcd4b006b31bf45f5f9191072820ae0a545500c966cf22afda1002466a78b7e4ddf02587f"));

        } catch (InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException |
                NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
