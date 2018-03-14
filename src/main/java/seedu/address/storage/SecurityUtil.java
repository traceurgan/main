package seedu.address.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * A class containing utility methods to encrypt and decrypt files for Storage
 */
public class SecurityUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static SecretKey secretKey;
    private static final String AES_KEY_FILEPATH = "data/aeskey.key";

    /**
     * Loads a SecretKey from the given path {@code AES_KEY_FILEPATH}.
     * If the SecretKey does not exist, create a new key.
     */
    private static void loadKey() {
        File keyFile = new File(AES_KEY_FILEPATH);
        if (keyFile.exists()) {
            readKey();
        }
        else {
            logger.info("Key does not exist. Creating key.");
            createKey();
        }
    }

    /**
     * Reads the key from the path given by {@code AES_KEY_FILEPATH}
     */
    private static void readKey() {
        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(AES_KEY_FILEPATH));
            secretKey = (SecretKey) ois.readObject();
            ois.close();
        } catch (IOException oie) {
            logger.severe("Failed to read key from file");
        } catch (ClassNotFoundException cnfe) {
            logger.severe("Failed to typecast to class SecretKey");
        }
        logger.fine("Key successfully read from file");
    }

    /**
     * Initializes and creates a SecretKey
     */
    private static void createKey() {
        initKey();
        saveKey();
    }

    /**
     * Creates a new SecretKey with the filepath {@code AES_KEY_FILEPATH}
     */
    private static void initKey() {
        KeyGenerator keyGen = null;

        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Failed to generate AES key");
        }

        keyGen.init(128);
        secretKey = keyGen.generateKey();
        logger.fine("Key generated");
    }

    /**
     * Saves the SecretKey at the filepath {@code AES_KEY_FILEPATH}
     */
    private static void saveKey() {
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(AES_KEY_FILEPATH));
            oos.writeObject(secretKey);
            oos.close();
        } catch (IOException e) {
            logger.severe("Failed to save key to file");
        }
        logger.fine("Key saved to file");
    }
}
