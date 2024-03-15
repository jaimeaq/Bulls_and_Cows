package bullscows.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SecretCode {
    private boolean isValid;
    private String characters = "0123456789abcdefghijklmnopqrstuvwxyz";
    private String code;
    private String codeAsStars;
    private int charRange;

    public String starred() {
        return codeAsStars;
    }

    public SecretCode() {
        isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    public SecretCode generate(int length, int charRange) {
        StringBuilder sb = new StringBuilder();
        this.charRange = charRange;
        List<Character> usedCharacters = new ArrayList<>();
        while(!isValid) {
            Random rnd = new Random();
            char currChar = characters.charAt(rnd.nextInt(charRange));
            if(!usedCharacters.contains(currChar)) {
                sb.append(currChar);
                usedCharacters.add(currChar);
                isValid = sb.length() == length;
            }
        }
        setCode(sb.toString());
        return this;
    }

    private void setCode(String s) {
        code = s;
        codeAsStars = code.replaceAll(".", "*");
    }

    public String getCode() {
        return code;
    }

    public String charRangeToString() {
        StringBuilder sb = new StringBuilder("(0-");
        int length = charRange;
//        System.out.println("Creating Secret Code Char Range with code: " + code);
        if(length <= 10) {
            sb.append(characters.charAt(length - 1));
        } //length = 11 case has no behaviour yet
        if(length > 11) {
            sb.append("9, a-").append(characters.charAt(length - 1));
        }
        sb.append(").");
        return sb.toString();
    }

    public String getCharacters() {
        return characters;
    }
}
