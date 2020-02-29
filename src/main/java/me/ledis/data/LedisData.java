package me.ledis.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LedisData {

    private LedisData() {
    }

    private static Map<String, Object> data = new HashMap<>();

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

        }
    }

    @SuppressWarnings("unchecked")
    public static void loadSavedSnapshot() {
        File savedSnapShot = createOrGetSnapshot();
        try (
                FileInputStream fInStream = new FileInputStream(savedSnapShot);
                ObjectInputStream oInStream = new ObjectInputStream(fInStream);
        ) {
            LedisData.data = (Map<String, Object>) oInStream.readObject();
        } catch (Exception ex) {

        }
        System.out.println();
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
}
