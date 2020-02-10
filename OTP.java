public class OTP {
    private static String encrypt(String message, String key) {
        if (message.length() != key.length()) throw new AssertionError();
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        char[] msg = message.toUpperCase().toCharArray();
        char[] ky = key.toUpperCase().toCharArray();

        int[] msgIndex = new int[message.length()];
        int[] keyIndex = new int[key.length()];

        char[] encrypt = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            msgIndex[i] = upper.indexOf(msg[i]);
            keyIndex[i] = upper.indexOf(ky[i]);

            encrypt[i] = upper.charAt((msgIndex[i] + keyIndex[i]) % 26);
        }
        return new String(encrypt);
    }

    private static String decrypt(String eMessage, String key) {
        if (eMessage.length() != key.length()) throw new AssertionError();
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        char[] msg = eMessage.toUpperCase().toCharArray();
        char[] ky = key.toUpperCase().toCharArray();

        int[] msgIndex = new int[eMessage.length()];
        int[] keyIndex = new int[key.length()];

        char[] decrypt = new char[eMessage.length()];
        for (int i = 0; i < eMessage.length(); i++) {
            msgIndex[i] = upper.indexOf(msg[i]);
            keyIndex[i] = upper.indexOf(ky[i]);

            int temp = (msgIndex[i] - keyIndex[i]) % 26;

            if (temp < 0)
                decrypt[i] = upper.charAt(temp + 26);
            else
                decrypt[i] = upper.charAt(temp);
        }
        return new String(decrypt);
    }

    private static String findKey (String message, String eMessage) {
        if (message.length() != eMessage.length()) throw new AssertionError();
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        char[] msg = eMessage.toUpperCase().toCharArray();
        char[] eMsg = message.toUpperCase().toCharArray();

        int[] msgIndex = new int[eMessage.length()];
        int[] eMsgIndex = new int[message.length()];

        char[] key = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            msgIndex[i] = upper.indexOf(msg[i]);
            eMsgIndex[i] = upper.indexOf(eMsg[i]);

            int temp = msgIndex[i] - eMsgIndex[i];
            if (temp < 0)
                key[i] = upper.charAt(temp + 26);
            else
                key[i] = upper.charAt(temp);

        }

        return new String(key);
    }

    public static void main(String[] args) {
        String message = "HELLO", key = "XMCKL";

        System.out.println("Message: " + message);
        System.out.println("Key: " + key);

        String a = encrypt(message, key);
        System.out.println("Encrypted message: " + a);
        System.out.println("Decrypted message: " + decrypt(a,key) + "\n");

        String key1 = "TRTSH";

        System.out.println("Message: " + message);
        System.out.println("Key: " + key1);

        System.out.println("Encrypted message: " + a);
        System.out.println("Decrypted message: " + decrypt(a,key1) + "\n");

        System.out.println("Encrypted message: " + a + "\n" +
                "Expected message: LATER" + "\n" +
                "Found key for those values: " + findKey("LATER", a) + "\n");

        System.out.println("Encrypted message: " + a + "\n" +
                "Expected message: NEVER" + "\n" +
                "Found key for those values: " + findKey("NEVER", a) + "\n");

        System.out.println("Encrypted message: " + a + "\n" +
            "Expected message: HELLO" + "\n" +
            "Found key for those values: " + findKey("HELLO", a) + "\n");
    }
}
