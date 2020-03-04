import java.util.concurrent.TimeUnit;

public class Main {
    public static int passwordLength(Secret s) {
        StringBuilder passLength = new StringBuilder();
        while (true) {
            try {
                s.verifyPassword(passLength.toString());
                return passLength.length(); // password length found
            } catch (IllegalArgumentException e) {
                passLength.append("1");
            }
        }
    }

    public static StringBuilder passwordGuess (int[] currentGuess) {
        StringBuilder currentPass = new StringBuilder();
        for (int guess : currentGuess) {
            currentPass.append(guess);
        }
        return currentPass;
    }

    public static int[] bruteForcePassword (Secret s, int leng) {
        int[] currentGuess = new int[leng];
        for (int i = 0; i < leng; i++) {
            currentGuess[i] = 0;
        }
        StringBuilder passGuess;
        long startTime,endTime;

        for (int i = 1; i < currentGuess.length+1; i++) {
            for (int j = 0; j < 10; j++) {
                currentGuess[i-1] = j;

                passGuess = passwordGuess(currentGuess);
                String guesspass = passGuess.toString();

                startTime = System.currentTimeMillis();
                s.verifyPassword(guesspass);
                endTime = System.currentTimeMillis();

                if ((endTime - startTime) >= 10*i) {
                    break;
                }
            }
        }
        return currentGuess;
    }

    public static void main(String[] args) {
        Secret s = new Secret();

        System.out.println("Password length: " + passwordLength(s));

        long startRun = System.currentTimeMillis();
        int[] currentGuess = bruteForcePassword(s, passwordLength(s));
        long endRun = System.currentTimeMillis();

        long diff = endRun-startRun;
        System.out.println("It took " + (TimeUnit.MILLISECONDS.toSeconds(diff)) + " seconds to run.");
        System.out.println("The password is: " + passwordGuess(currentGuess).toString());
        System.out.println("The flag: " + s.getSecret());
    }
}