package algorithms.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {
    private final String filePath;

    public CSVWriter(String filePath) {
        this.filePath = filePath;
    }

    public void write(List<String[]> rows) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] row : rows) {
                writer.write(String.join(",", row));
                writer.write("\n");
            }
        }
    }
}