package org.example.repository.PostRepository;

import org.example.model.Post;
import org.example.model.Status;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository {


    private final File file = Path.of("src", "main", "resources", "post.json").toFile();

    @Override
    public Post create(Post post) {
        List<Post> currentWriters = getPostsFromFile();
        Long newId = generateNewId(currentWriters);
        post.setId(newId);

        currentWriters.add(post);
        writePostToFile(currentWriters);

        return post;
    }

    @Override
    public Post update(Post post) {
        List<Post> currentPosts = getPostsFromFile()
                .stream()
                .map(p-> {
                    if (p.getId().equals(post.getId())) {
                        return post;
                    }
                    return p;
                }).toList();

        writePostToFile(currentPosts);
        return post;
    }
    @Override
    public Post getById(Long id) {
        return getPostsFromFile()
                .stream()
                .filter(w -> {
                    return w.getId().equals(id);
                })
                .findFirst()
                .orElse(null);
    }
    @Override
    public void deleteById(Long id) {
        List<Post> currentPosts = getPostsFromFile();
        currentPosts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .ifPresent(writer -> writer.setStatus(Status.DELETED));
        writePostToFile(currentPosts);
    }



    @Override
    public List<Post> getAll() {
        return getPostsFromFile();
    }


    private void writePostToFile(List<Post> posts) {
        try (FileWriter fWriter = new FileWriter(file);) {
            for (Post post : posts) {
                fWriter.write(post.toJson() + System.lineSeparator());
            }
        } catch (IOException e) {
            IO.println("Не удалось записать в файл");
        }
    }

    private List<Post> getPostsFromFile() {
        List<Post> post = new ArrayList<>();
        String line;
        try (FileReader reader = new FileReader(file);
             BufferedReader buffer = new BufferedReader(reader)
        ) {
            while ((line = buffer.readLine()) != null) {
                post.add(Post.fromJson(line));
            }
        } catch (IOException e) {
            IO.println("Не удалось получить пользователей");
        }
        return post;
    }

    private Long generateNewId(List<Post> writers) {
        return writers.stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0L) + 1;
    }
}