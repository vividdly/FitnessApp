import java.io.*;

public class DataManager {
    private static final String FILE_NAME = "fitness_data.ser";

    public static void saveData(UserData data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
        }
    }

    public static UserData loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (UserData) ois.readObject();
        } catch (Exception e) {
            return new UserData();
        }
    }
}