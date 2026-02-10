package org.example.repository.LabelRepository;

import org.example.model.Label;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository {

    File file = Path.of("src", "main", "resources", "label.json").toFile();

    @Override
    public void create(Label label) throws IOException {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append(label.toJson()).append(System.lineSeparator());
            System.out.println("Файл сохранен: " + file.getAbsolutePath());
        }
    }


    @Override
    public List<Label> getAll() throws IOException {
        List<Label> labels = new ArrayList<>();
        String line;
        try (FileReader reader = new FileReader(file);
             BufferedReader buffer = new BufferedReader(reader)
        ) {
            while ((line = buffer.readLine()) != null) {
                labels.add(Label.fromJson(line));
            }
        }
        return labels;
    }


    @Override
    public void saveAll(List<Label> labels) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (Label label : labels) {
                writer.write(label.toJson() + System.lineSeparator());
            }
        }
    }
}



