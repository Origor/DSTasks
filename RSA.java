import java.math.BigInteger;

public class RSA {
    private BigInteger n, e, d;

    public RSA(String p, String q) {
        BigInteger P = new BigInteger(p);
        BigInteger Q = new BigInteger(q);

        this.n = P.multiply(Q);
        System.out.println("N = " + n);

        // W = (P-1)x(Q-1),  (Eulers totient)
        BigInteger w = P.subtract(BigInteger.ONE).multiply(Q.subtract(BigInteger.ONE));
        System.out.println("W = " + w);

        // E = next prime of max(p,q)
        this.e = P.max(Q).nextProbablePrime();
        System.out.println("E = " + this.e);

        this.d = this.e.modInverse(w);
        System.out.println("D = " + d);
    }

    public BigInteger encrypt(BigInteger m) { return m.modPow(e, n); }

    public BigInteger decrypt(BigInteger c) { return c.modPow(d, n); }

    public static void main(String[] args) {
        RSA rsa = new RSA("7", "11");
        BigInteger c = rsa.encrypt(new BigInteger("7"));
        System.out.println("enc(65) = " + c);
        System.out.println("dec(c) = " + rsa.decrypt(c));
    }
}
