import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAPerformance {
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    public RSAPerformance(RSAPrivateKey p, RSAPublicKey q) {
        this.privateKey = p;
        this.publicKey = q;
    }

    public BigInteger encrypt(BigInteger m) { return m.modPow(publicKey.getPublicExponent(), publicKey.getModulus()); }

    public BigInteger decrypt(BigInteger c) { return c.modPow(privateKey.getPrivateExponent(), privateKey.getModulus()); }

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

        RSAPerformance rsa = new RSAPerformance((RSAPrivateKey) pair.getPrivate(), (RSAPublicKey) pair.getPublic());
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        byte[] message = "My super duper secret password that is super encrypted :)".getBytes();
        System.out.println("Message: " + new String(message));

        //-------------------------------------------------API----------------------------------------------------------
        long startEncApi = System.nanoTime();
        byte[] encmsg = rsa.encrypt(cipher, message);
        long endEncApi = System.nanoTime();
        double etis = (double) (endEncApi-startEncApi) / 1_000_000_000;
        System.out.println("\nAPI encryption time of a message: " + etis + " seconds.");

        long startdecOur = System.nanoTime();
        byte [] apidec = rsa.decrypt(cipher, encmsg);
        long enddecOur = System.nanoTime();
        double tvais = (double) (enddecOur-startdecOur) / 1_000_000_000;
        System.out.println("Our decryption time of the same message: " + tvais + " seconds.\n");

        if (tvais - etis < 0) {
            System.out.println("It's faster to DECRYPT with API.");
        }
        else {
            System.out.println("It's faster to ENCRYPT with API.");
        }

        System.out.println("\nEncrypted message: " + new String(encmsg));
        System.out.println("Decrypted message: " + new String(apidec).trim());
        //---------------------------------------------------OUR--------------------------------------------------------
        System.out.println("\n" +
                "-----------------------------------------------------------------------------------------------------"+
                "-------------------" + "\n");
        startEncApi = System.nanoTime();
        BigInteger enmsg = rsa.encrypt(new BigInteger(message));
        endEncApi = System.nanoTime();
        double et = (double) (endEncApi-startEncApi) / 1_000_000_000;
        System.out.println("\nOur encryption time of a message: " + etis + " seconds.");

        startdecOur = System.nanoTime();
        BigInteger ourdec = rsa.decrypt(enmsg);
        enddecOur = System.nanoTime();
        double tva = (double) (enddecOur-startdecOur) / 1_000_000_000;
        System.out.println("Our decryption time of the same message: " + tvais + " seconds.\n");

        if (tva - et < 0)
            System.out.println("It's faster to DECRYPT with OUR.");
        else
            System.out.println("It's faster to ENCRYPT with OUR.");


        System.out.println("\nEncrypted message: " + new String(enmsg.toByteArray()));
        System.out.println("Decrypted message: " + new String(ourdec.toByteArray()) + "\n");

        double ourdiff = Math.abs((tva-et));
        double apidiff = Math.abs((tvais-etis));

        if (ourdiff - apidiff < 0)
            System.out.println("Encryption & Decryption together is faster with OUR.");
        else
            System.out.println("Encryption & Decryption together is faster with API.");
    }
}
