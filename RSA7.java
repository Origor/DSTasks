import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSA7 {
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    public RSA7(RSAPrivateKey p, RSAPublicKey q) {
        this.privateKey = p;
        this.publicKey = q;
    }

    public BigInteger encrypt(BigInteger m) { 
        return m.modPow(publicKey.getPublicExponent(), publicKey.getModulus()); 
    }

    public BigInteger decrypt(BigInteger c) { 
        return c.modPow(privateKey.getPrivateExponent(), privateKey.getModulus()); 
    }

    public byte[] encrypt(Cipher a, byte[] input) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        a.init(Cipher.ENCRYPT_MODE, publicKey);
        a.update(input);
        return a.doFinal();
    }

    public byte[] decrypt(Cipher a, byte[] cipher) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        a.init(Cipher.DECRYPT_MODE, privateKey);
        return a.doFinal(cipher);
    }

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
        key.initialize(2048);
        KeyPair pair = key.generateKeyPair();

        RSA7 rsa = new RSA7((RSAPrivateKey) pair.getPrivate(), (RSAPublicKey) pair.getPublic());
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        byte[] message = "My super duper secret password that is super encrypted :)".getBytes();
        System.out.println("Message: " + new String(message));

        //--------------------------------------------------------------------------------------------------------------
        byte[] encmsg = rsa.encrypt(cipher, message);
        System.out.println("\n" + "Encrypted message (api byte[]): " + new String(encmsg) + "\n");
        System.out.println("Decrypted message (our bigInt): " + rsa.decrypt(new BigInteger(encmsg))+ "\n");
        System.out.println("Decrypted message (api bigInt): " + new BigInteger(rsa.decrypt(cipher, encmsg))+ "\n");
        System.out.println("Decrypted message (api string): " + new String(rsa.decrypt(cipher, encmsg))+ "\n");
        //--------------------------------------------------------------------------------------------------------------
        System.out.println("---------------------------------------------------------------------------------------------------" + "\n");
        encmsg = rsa.encrypt(new BigInteger(message)).toByteArray();
        System.out.println("Encrypted message (our byte[]): " + new String(encmsg) + "\n");
        System.out.println("Decrypted message (api bigInt): " + new BigInteger(rsa.decrypt(cipher, encmsg))+ "\n");
        System.out.println("Decrypted message (our bigInt): " + rsa.decrypt(new BigInteger(encmsg)) + "\n");
        System.out.println("Decrypted message (our String): " + new String(rsa.decrypt(new BigInteger(encmsg)).toByteArray()));
    }
}
