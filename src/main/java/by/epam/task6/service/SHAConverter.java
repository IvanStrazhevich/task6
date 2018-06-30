package by.epam.task6.service;

import by.epam.task6.exception.EncriptingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAConverter {
    private static Logger logger = LogManager.getLogger();
    private static final String ENCODE="SHA-1";

    public SHAConverter() {
    }

    public String convertToSHA1(String string) throws EncriptingException {
        byte[] digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance(ENCODE);
            md.reset();
            md.update(string.getBytes());
            digest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new EncriptingException(e);
        }
        BigInteger bigInt = new BigInteger(1, digest);
        String SHA1Hex = bigInt.toString(16);
        while (SHA1Hex.length() < 32) {
            SHA1Hex = "0" + SHA1Hex;
        }
        return SHA1Hex;
    }
}

