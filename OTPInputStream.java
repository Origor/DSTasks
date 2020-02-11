package assignment_1;

import java.io.IOException;
import java.io.InputStream;

public class OTPInputStream extends InputStream {
    InputStream key;
    InputStream input;
    boolean bit_mod = true;
    boolean enc_dec = true;


    OTPInputStream(InputStream input, InputStream key){
        this.key = key;
        this.input = input;
    }

    /** Sets whether to use bitwise or modulo addition.
     * @param type to "bitwise" for bitwise, "modulo" for modulo addition.
     */
    public void setType(String type){
        if (type.equals("bitwise")){
            this.bit_mod = true;
        } else if(type.equals("modulo")){
            this.bit_mod = false;
        }
    }

    /** Sets whether to encrypt or decrypt.
     * @param mode set to "encryption" for encryption, "decryption" for decryption.
     */
    public void setMode(String mode){
        if (mode.equals("encryption")){
            this.enc_dec = true;
        } else if(mode.equals("decryption")){
            this.enc_dec = false;
        }
    }

    @Override
    public int read() throws IOException {
        if (key == null) throw new IOException("Encryption key cannot be null.");
        if (input == null) throw new IOException("InputStream to encrypt cannot be null.");

        return enc_dec ? (bit_mod ? encryptBW() : encryptMA()) : (bit_mod ? decryptBW() : decryptMA());
    }

//TODO: fix
    private int encryptBW() throws IOException {
        int inputd = input.read(), keyd = key.read();
        if(inputd != -1) return inputd ^ keyd;
        key.close();
        input.close();
        return -1;
    }
    private int decryptBW() throws IOException {
        return encryptBW();
    }

    private int encryptMA() throws IOException {
        int inputd = input.read(), keyd = key.read();

        if (inputd != -1) {
            if (inputd < 65 || inputd > 90) throw new IOException("Inputstream 'input' provided non capital letter: " + (char) inputd);
            if (keyd < 65 || keyd > 90) throw new IOException("Inputstream 'key' provided non capital letter: " + (char) keyd);

            return (((inputd - 65) + (keyd - 65)) % 26) + 65;
        }
        input.close();
        key.close();
        return -1;
    }

    private int decryptMA() throws IOException {
        int inputd = input.read(), keyd = key.read();

        if (inputd != -1) {
            if (inputd < 65 || inputd > 90) throw new IOException("Input not all capital letter.");
            if (keyd < 65 || keyd > 90) throw new IOException("Encryption key not all capital letters.");

            int decm = (inputd - 65) - (keyd - 65);
            if (decm > 0) return (decm % 26) + 65;
            return (decm + 26) + 65;
        }
        input.close();
        key.close();
        return -1;
    }

}
