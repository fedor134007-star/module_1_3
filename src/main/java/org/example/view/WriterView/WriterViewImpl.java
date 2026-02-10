package org.example.view.WriterView;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.Status;
import org.example.model.Writer;
import org.example.repository.LabelRepository.LabelRepository;
import org.example.repository.PostRepository.PostRepository;
import org.example.repository.WriterRepository.WriterRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WriterViewImpl implements WriterView {

    private final WriterRepository writerRepository;
    private final PostRepository postRepository;
    private final LabelRepository labelRepository;

    public WriterViewImpl(WriterRepository writerRepository, PostRepository postRepository, LabelRepository labelRepository) {
        this.writerRepository = writerRepository;
        this.postRepository = postRepository;
        this.labelRepository = labelRepository;
    }

    @Override
    public void update(Writer writer) {
        try {
            ArrayList<Writer> allWriters = new ArrayList<>(getAll());

            for (var i = 0; i < allWriters.size(); i++) {
                if (allWriters.get(i).getId() == writer.getId()) {
                    allWriters.set(i, writer);
                }
            }
            writerRepository.saveAll(allWriters);
        } catch (IOException e) {
            IO.println("Не удалось обновить");
        }
    }

    @Override
    public void create(Writer writer) {
        try {
            writerRepository.create(writer);
        } catch (IOException e) {
            IO.println("Не удалось создать запись");
        }
    }


    @Override
    public void delete(long id) {
        try {
            ArrayList<Writer> writers = new ArrayList<>(getAll());
            for (var i = 0; i < writers.size(); i++) {
                Writer writer = writers.get(i);
                if (writer.getId() == id) {
                    writer.setStatus(Status.DELETED);
                    writers.set(i, writer);
                }
            }
            writerRepository.saveAll(writers);
        } catch (IOException e) {
            IO.println("Не удалось удалить");
        }
    }

    @Override
    public List<Writer> getAll() {
        try {
            // 1. Получаем все данные
            List<Writer> allWriters = writerRepository.getAll();
            List<Post> allPosts = postRepository.getAll();
            List<Label> allLabels = labelRepository.getAll();

            // Для каждого поста находим его лейблы
            for (Post post : allPosts) {
                List<Label> postLabels = allLabels.stream()
                        .filter(label -> label.getPostId() == post.getId())
                        .collect(Collectors.toList());

                // Устанавливаем лейблы для поста
                post.setLabels(postLabels);
            }
            // Для каждого пользователя находим посты
            for (Writer writer : allWriters) {
                List<Post> writerPosts = allPosts.stream()
                        .filter(post -> post.getUserId() == writer.getId())
                        .collect(Collectors.toList());

                // Устанавливаем посты для пользователя
                writer.setPosts(writerPosts);
            }
            return allWriters;

        } catch (IOException e) {
            IO.println("Не удалось получить записи: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            IO.println("Не удалось получить записи произошла ошибка с данными");
            return new ArrayList<>();
        }
    }


}
