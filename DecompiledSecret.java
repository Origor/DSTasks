//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DecompiledSecret {
    private static final byte[] s = new byte[]{-57, -52, -52, -50, -50, -53, -50, -50, -49, -50, -52, -62, -53, -54, -64, -61};
    private Object a = null;

    public DecompiledSecret() {
    }

    public void verifyPassword(String var1) {
        if (var1 == null) {
            throw new IllegalArgumentException("null password");
        } else {
            byte[] var2 = var1.getBytes();
            if (var2.length != s.length) {
                throw new IllegalArgumentException("Wrong password length");
            } else {
                byte[] var3 = new byte[var2.length];

                for(int var4 = 0; var4 < var2.length; ++var4) {
                    var3[var4] = (byte)(s[var4] ^ 255 - var4);
                    if (var2[var4] != var3[var4]) {
                        return;
                    }

                    d();
                }

                this.a = var3;
            }
        }
    }

    public String getSecret() {
        if (this.a == null) {
            return null;
        } else {
            try {
                MessageDigest var1 = MessageDigest.getInstance("SHA1");
                byte[] var2 = var1.digest((byte[])((byte[])this.a));
                String var3 = "";
                byte[] var4 = var2;
                int var5 = var2.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    byte var7 = var4[var6];
                    var3 = var3 + String.format("%02X", var7);
                }

                return var3;
            } catch (NoSuchAlgorithmException var8) {
                return null;
            }
        }
    }

    private static void d() {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException var1) {
        }

    }
}