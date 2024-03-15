package bullscows.model;

public class Grade {
    public boolean isPerfect;
    public int bulls;
    public int cows;

    public Grade() {
        isPerfect = false;
        bulls = 0;
        cows = 0;
    }

    public Grade evalGuess(SecretCode secretCode, String guess) {
        if (secretCode.getCode().equals(guess)) {
            cows = 0;
            bulls = guess.length();
            isPerfect = true;
            return this;
        }
        cows = calculateCows(secretCode, guess);
        bulls = calculateBulls(secretCode, guess);
        return this;
    }

    private int calculateCows(SecretCode secretCode, String guess) {
        int cowsCount = 0;
        String code = secretCode.getCode();
        for (int i = 0; i < guess.length(); i++) {
            for (int j = 0; j < code.length(); j++) {
                if (i == j) {
                    continue;
                }
                if (guess.charAt(i) == code.charAt(j)) {
                    cowsCount++;
                    break;
                }
            }
        }
        return cowsCount;
    }

    private int calculateBulls(SecretCode secretCode, String guess) {
        int bullsCount = 0;
        String code = secretCode.getCode();
        for (int i = 0; i < guess.length(); i++) {
            if(guess.charAt(i) == code.charAt(i)) {
                bullsCount++;
            }
        }
        return bullsCount;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grade: ");
        if (bulls == 0 && cows == 0) {
            sb.append("None.");
            return sb.toString();
        }
        if(bulls > 0) {
            sb.append(bulls).append(" bull(s)");
        }
        if(bulls > 0 && cows > 0) {
            sb.append(" and ");
        }
        if(cows > 0) {
            sb.append(cows).append(" cow(s)");
        }
        sb.append(".");

        return sb.toString();
    }
}
