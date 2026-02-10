package org.example.repository.WriterRepository;

import org.example.model.Writer;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository {


    File file = Path.of("src", "main", "resources", "writer.json").toFile();

    @Override
    public void create(Writer writer) throws IOException {
        try (FileWriter fWriter = new FileWriter(file, true)) {
            fWriter.append(writer.toJson()).append(System.lineSeparator());
            System.out.println("Файл сохранен: " + file.getAbsolutePath());
        }
    }


    @Override
    public List<Writer> getAll() throws IOException {
        List<Writer> writers = new ArrayList<>();
        String line;
        try (FileReader reader = new FileReader(file);
             BufferedReader buffer = new BufferedReader(reader)
        ) {
            while ((line = buffer.readLine()) != null) {
                writers.add(Writer.fromJson(line));
            }
        }
        return writers;
    }


    @Override
    public void saveAll(List<Writer> writers) throws IOException {
        try (FileWriter fWriter = new FileWriter(file)) {
            for (Writer writer : writers) {
                fWriter.write(writer.toJson() + System.lineSeparator());
            }
        }
    }


}
