package model;

import java.util.ArrayList;
import java.util.Arrays;

public class JournalDecoder extends JournalConstants {
    private char[] password = DEFAULT_PW;
    private int offset;
    private static JournalDecoder jd;

    private static String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789`/|{}[]()@#$%^&*()+_-+=~";
    private static ArrayList<String> exempt = new ArrayList<>(
            Arrays.asList(" ", ".", ",", "?", "!", ":", ";", "\'", "\""));
    private static final String NEWLINE = "\n";
    private static final String DELIM = "`";

    private JournalDecoder(char[] password) {
        setPassword(password);
        setOffset(password);
    }

    public static JournalDecoder getInstance(char[] password) {
        if (jd == null && password.length >= 5)
            return jd = new JournalDecoder(password);
        return jd;
    }

    protected char[] getPassword() {
        return password;
    }

    public boolean facadeModifyPassword(char[] password) {
        setPassword(password);
        setOffset(password);

        if (!this.password.equals(DEFAULT_PW) && this.password != null)
            return true;
        return false;
    }

    private void setPassword(char[] password) {
        if (password != null && password.length > 0)
            this.password = password;
    }

    protected int getOffset() {
        return offset;
    }

    private void setOffset(char[] password) {
        this.offset = convertToOffset(password);
    }

    public static int convertToOffset(char[] password) {
        int offset = 0;

        for (char val : password)
            offset += val - 'a' + alphabet.length();

        return offset;
    }

    public String encode(String message) {
        String encrypted = "";

        String messageDelim = message.replaceAll(NEWLINE, DELIM);

        for (char val : messageDelim.toCharArray()) {
            if (!exempt.contains(String.valueOf(val))) {
                int initPosition = alphabet.indexOf(val);
                int newPosition = (initPosition + getOffset()) % alphabet.length();
                char newVal = alphabet.charAt(newPosition);
                encrypted = encrypted + newVal;
            } else
                encrypted += val;
        }

        return encrypted;
    }

    public String decode(String encrypted) {
        String decrypted = "";

        for (char val : encrypted.toCharArray()) {
            if (!exempt.contains(String.valueOf(val))) {
                int initPosition = alphabet.indexOf(val);
                int newPosition = (initPosition - getOffset()) % alphabet.length();
                if (newPosition < 0)
                    newPosition = newPosition + alphabet.length();
                char newVal = alphabet.charAt(newPosition);
                decrypted += newVal;
            } else
                decrypted += val;
        }

        String messageDelim = decrypted.replace(DELIM, NEWLINE);

        return messageDelim;
    }

    protected void closeJournal() {
        this.password = "".toCharArray();
        this.offset = 0;
    }

}
