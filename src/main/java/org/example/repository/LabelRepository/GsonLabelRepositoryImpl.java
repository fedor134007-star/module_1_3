package org.example.repository.LabelRepository;

import org.example.model.Label;
import org.example.model.Status;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository {


    private final File file = Path.of("src", "main", "resources", "label.json").toFile();

    @Override
    public Label create(Label label) {
        List<Label> labelsFromFile = getLabelsFromFile();
        Long newId = generateNewId(labelsFromFile);
        label.setId(newId);

        labelsFromFile.add(label);
        writeLabelToFile(labelsFromFile);

        return label;
    }

    @Override
    public Label update(Label label) {
        List<Label> currentLabels = getLabelsFromFile()
                .stream()
                .map(p-> {
                    if (p.getId().equals(label.getId())) {
                        return label;
                    }
                    return p;
                }).toList();

        writeLabelToFile(currentLabels);
        return label;
    }
    @Override
    public Label getById(Long id) {
        return getLabelsFromFile()
                .stream()
                .filter(w -> {
                    return w.getId().equals(id);
                })
                .findFirst()
                .orElse(null);
    }
    @Override
    public void deleteById(Long id) {
        List<Label> currentPosts = getLabelsFromFile();
        currentPosts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .ifPresent(writer -> writer.setStatus(Status.DELETED));
        writeLabelToFile(currentPosts);
    }




    @Override
    public List<Label> getAll() {
        return getLabelsFromFile();
    }


    private void writeLabelToFile(List<Label> labels) {
        try (FileWriter fWriter = new FileWriter(file);) {
            for (Label label : labels) {
                fWriter.write(label.toJson() + System.lineSeparator());
            }
        } catch (IOException e) {
            IO.println("Не удалось записать в файл");
        }
    }

    private List<Label> getLabelsFromFile() {
        List<Label> label = new ArrayList<>();
        String line;
        try (FileReader reader = new FileReader(file);
             BufferedReader buffer = new BufferedReader(reader)
        ) {
            while ((line = buffer.readLine()) != null) {
                label.add(Label.fromJson(line));
            }
        } catch (IOException e) {
            IO.println("Не удалось получить пользователей");
        }
        return label;
    }

    private Long generateNewId(List<Label> writers) {
        return writers.stream()
                .mapToLong(Label::getId)
                .max()
                .orElse(0L) + 1;
    }
}