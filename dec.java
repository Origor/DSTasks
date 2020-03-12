import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class dec {
    private static final String keyString = "[24, 48, -63, 4]";

    private static void printByte(byte[] a) {
        String b = Arrays.toString(a);
        if (!b.equals(keyString)) {
            System.out.println(b); // all keys that are not 24, 48, -63, 4 are only 0os
        }
    }

    public static void main(String[] args) {
        File encryptedFile = new File("C:\\Users\\ajgor\\IdeaProjects\\task2\\src\\mfc100.dll.encrypted");
        File file = new File("C:\\Users\\ajgor\\IdeaProjects\\task2\\src\\mfc100.dll");

        System.out.println(encryptedFile.length());
        System.out.println(file.length());

        byte[] encryptedBytes = new byte[(int) file.length()];
        byte[] bytes = new byte[(int) file.length()];

        try {
            FileInputStream encinput = new FileInputStream(encryptedFile);
            FileInputStream input = new FileInputStream(file);

            byte[] key = new byte[4];

            int j = 0;
            while (j != 4397384) {
                for (int i = 0; i < 4; i++) {
                    bytes[i] = (byte) input.read();
                    encryptedBytes[i] = (byte) encinput.read();
                    key[i] = (byte) (bytes[i] ^ encryptedBytes[i]);
                }
                printByte(key); // this is the key [24, 48, -63, 4]
                j++;
            }
            encinput.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
