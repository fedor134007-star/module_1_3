package org.example.view.PostView;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.Status;
import org.example.model.Writer;
import org.example.repository.LabelRepository.LabelRepository;
import org.example.repository.PostRepository.PostRepository;
import org.example.repository.WriterRepository.WriterRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostViewImpl implements PostView {


    private final WriterRepository writerRepository;
    private final PostRepository postRepository;
    private final LabelRepository labelRepository;


    public PostViewImpl(PostRepository postRepository, LabelRepository labelRepository, WriterRepository writerRepository) {
        this.postRepository = postRepository;
        this.labelRepository = labelRepository;
        this.writerRepository = writerRepository;
    }

    @Override
    public void update(Post post) {
        try {
            ArrayList<Post> allPosts = new ArrayList<>(getAll());

            for (var i = 0; i < allPosts.size(); i++) {
                if (allPosts.get(i).getId() == post.getId()) {
                    allPosts.set(i, post);
                }
            }
            postRepository.saveAll(allPosts);
        } catch (IOException e) {
            IO.println("Не удалось обновить");
        }
    }

    @Override
    public void create(Post post) {
        try {
            List<Long> listValidId = writerRepository.getAll().stream().map(Writer::getId).toList();
            if (listValidId.contains(post.getUserId())) {
                postRepository.create(post);
            } else {
                IO.println("Вы выбрали не вaлидный ID поста");
            }
        } catch (IOException e) {
            IO.println("Не удалось создать запись");
        }
    }

    @Override
    public void delete(long id) {
        try {
            ArrayList<Post> posts = new ArrayList<>(getAll());
            for (var i = 0; i < posts.size(); i++) {
                Post post = posts.get(i);
                if (post.getId() == id) {
                    post.setStatus(Status.DELETED);
                    posts.set(i, post);
                }
            }
            postRepository.saveAll(posts);
        } catch (IOException e) {
            IO.println("Не удалось удалить");
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> allPosts = new ArrayList<>();
        List<Label> allLabels;

        try {
            // Получаем все посты и все лейблы
            allPosts = postRepository.getAll();
            allLabels = labelRepository.getAll();

            // Для каждого поста находим его лейблы
            for (Post post : allPosts) {
                List<Label> postLabels = allLabels.stream()
                        .filter(label -> label.getPostId() == post.getId())
                        .collect(Collectors.toList());

                // Устанавливаем лейблы для поста
                post.setLabels(postLabels);
            }

        } catch (IOException e) {
            IO.println("Не удалось получить записи");
        } catch (Exception e) {
            IO.println("Не удалось получить записи произошла ошибка с данными");
            return new ArrayList<>();
        }
        return allPosts;
    }
}
