package model;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class JournalDecoder extends JournalConstants {
    private String password = DEFAULT_PW;
    private static final String ALGO = "Blowfish";
    private static final String MODE = "Blowfish/CBC/PKCS5Padding";
    private static final String IV = "cIJK%^&O";
    private static JournalDecoder jd;

    private JournalDecoder(String password) {
        setPassword(password);
    }

    public static JournalDecoder getInstance(String defaultPw) {
        if (jd == null && defaultPw.length() >= 5)
            return jd = new JournalDecoder(defaultPw);
        return jd;
    }

    protected String getPassword() {
        return password;
    }

    public boolean facadeModifyPassword(String password) {
        setPassword(password);

        if (!this.password.equals(DEFAULT_PW) && this.password != null)
            return true;
        return false;
    }

    private void setPassword(String password) {
        if (password != null && password.length() > 0)
            this.password = password;
    }

    public String encrypt(String password, String value) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec spec = new SecretKeySpec(password.getBytes(), ALGO);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.ENCRYPT_MODE, spec, new IvParameterSpec(IV.getBytes()));
        byte[] val = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(val);
    }

    public String decrypt(String password, String value) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] val = Base64.getDecoder().decode(value);
        SecretKeySpec spec = new SecretKeySpec(password.getBytes(), ALGO);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.DECRYPT_MODE, spec, new IvParameterSpec(IV.getBytes()));
        return new String(cipher.doFinal(val));
    }

    protected void closeJournal() {
        this.password = "";
    }

}
