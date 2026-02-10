package org.example.repository.WriterRepository;

import org.example.model.Status;
import org.example.model.Writer;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository {


    private final File file = Path.of("src", "main", "resources", "writer.json").toFile();

    @Override
    public Writer create(Writer writer) {
        List<Writer> currentWriters = getWritersFromFile();
        Long newId = generateNewId(currentWriters);
        writer.setId(newId);

        currentWriters.add(writer);
        writeWriterToFile(currentWriters);

        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        List<Writer> currentWriters = getWritersFromFile()
                .stream()
                .map(w -> {
                    if (w.getId().equals(writer.getId())) {
                        return writer;
                    }
                    return w;
                }).toList();

        writeWriterToFile(currentWriters);
        return writer;
    }
    @Override
    public Writer getById(Long id) {
        return getWritersFromFile()
                .stream()
                .filter(w -> {
                    return w.getId().equals(id);
                })
                .findFirst()
                .orElse(null);
    }
    @Override
    public void deleteById(Long id) {
        List<Writer> currentWriters = getWritersFromFile();
        currentWriters.stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst()
                .ifPresent(writer -> writer.setStatus(Status.DELETED));
        writeWriterToFile(currentWriters);
    }


    @Override
    public List<Writer> getAll() {
        return getWritersFromFile();
    }


    private void writeWriterToFile(List<Writer> writers) {
        try (FileWriter fWriter = new FileWriter(file);) {
            for (Writer writer : writers) {
                fWriter.write(writer.toJson() + System.lineSeparator());
            }
        } catch (IOException e) {
            IO.println("Не удалось записать в файл");
        }
    }

    private List<Writer> getWritersFromFile() {
        List<Writer> writers = new ArrayList<>();
        String line;
        try (FileReader reader = new FileReader(file);
             BufferedReader buffer = new BufferedReader(reader)
        ) {
            while ((line = buffer.readLine()) != null) {
                writers.add(Writer.fromJson(line));
            }
        } catch (IOException e) {
           IO.println("Не удалось получить пользователей");
        }
        return writers;
    }

    private Long generateNewId(List<Writer> writers) {
        return writers.stream()
                .mapToLong(Writer::getId)
                .max()
                .orElse(0L) + 1;
    }
}