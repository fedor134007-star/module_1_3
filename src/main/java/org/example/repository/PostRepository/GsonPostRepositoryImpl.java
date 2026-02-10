package org.example.repository.PostRepository;

import org.example.model.Post;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository{


    File file = Path.of("src", "main", "resources", "post.json").toFile();

    @Override
    public void create(Post post) throws IOException {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append(post.toJson()).append(System.lineSeparator());
            System.out.println("Файл сохранен: " + file.getAbsolutePath());
        }
    }


    @Override
    public List<Post> getAll() throws IOException {
        List<Post> posts = new ArrayList<>();
        String line;
        try (FileReader reader = new FileReader(file);
             BufferedReader buffer = new BufferedReader(reader)
        ) {
            while ((line = buffer.readLine()) != null) {
                posts.add(Post.fromJson(line));
            }
        }
        return posts;
    }


    @Override
    public void saveAll(List<Post> posts) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (Post post : posts) {
                writer.write(post.toJson() + System.lineSeparator());
            }
        }
    }
}



