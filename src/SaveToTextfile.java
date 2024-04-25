import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveToTextfile {
    private String filePath;

    public SaveToTextfile(String filePath) {
        this.filePath = filePath;
    }

    public void writeToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
