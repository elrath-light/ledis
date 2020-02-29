package me.ledis.data;

import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static me.ledis.constant.ResponseMessage.RESTORE_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.SAVE_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class LedisData {

    private LedisData() {
    }

    private static Map<String, Object> data = new HashMap<>();
    private static Map<String, LocalDateTime> keyExpireTime = new HashMap<>();

    public static Object get(String key) {
        return data.get(key);
    }

    public static void set(String key, Object object) {
        data.put(key, object);
    }

    public static void saveCurrentState() {
        File snapshot = createOrGetSnapshot();
        try (
                FileOutputStream fOutStream = new FileOutputStream(snapshot);
                ObjectOutputStream oOutStream = new ObjectOutputStream(fOutStream);
        ) {
            oOutStream.writeObject(data);
        } catch (Exception ex) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, SAVE_ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadLastSavedSnapshot() {
        File savedSnapShot = createOrGetSnapshot();
        try (
                FileInputStream fInStream = new FileInputStream(savedSnapShot);
                ObjectInputStream oInStream = new ObjectInputStream(fInStream);
        ) {
            LedisData.data = (Map<String, Object>) oInStream.readObject();
        } catch (Exception ex) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, RESTORE_ERROR_MESSAGE);
        }
    }

    private static File createOrGetSnapshot() {
        String backupFolderPath = "I:/backup";
        String snapshotPath = backupFolderPath + "/snapshot.ledis";
        try {
            File directory = new File(backupFolderPath);
            if (!directory.exists()) {
                Files.createDirectory(Paths.get(backupFolderPath));
            }
            File snapshot = new File(snapshotPath);
            snapshot.createNewFile();
            return snapshot;
        } catch (IOException ex) {

        }
        return null;
    }

    public static void clear() {
        data.clear();
    }

    public static Object[] getAllKeys() {
        return data.keySet().toArray();
    }

    public static Object remove(String key) {
        return data.remove(key);
    }

    public static void setExpireTime(String key, LocalDateTime dateTime) {
        keyExpireTime.put(key, dateTime);
    }

    public static LocalDateTime getExpireTime(String key) {
        return keyExpireTime.get(key);
    }

    public static void removeExpireTime(String key) {
        keyExpireTime.remove(key);
    }

    public static boolean hasNoExpireTimeSet() {
        return keyExpireTime.isEmpty();
    }

    public static Set<String> getAllKeyWithTimeout() {
        return keyExpireTime.keySet();
    }

    public static void clearAllExpireTime() {
        keyExpireTime.clear();
    }
}
