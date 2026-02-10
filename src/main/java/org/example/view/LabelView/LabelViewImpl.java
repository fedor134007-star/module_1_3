package org.example.view.LabelView;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.Status;
import org.example.repository.LabelRepository.LabelRepository;
import org.example.repository.PostRepository.PostRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LabelViewImpl implements LabelView {

    private final LabelRepository labelRepository;
    private final PostRepository postRepository;

    public LabelViewImpl(LabelRepository labelRepository, PostRepository postRepository) {
        this.labelRepository = labelRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void update(Label label) {
        ArrayList<Label> allLabels = new ArrayList<>(getAll());

        for (var i = 0; i < allLabels.size(); i++) {
            if (allLabels.get(i).getId() == label.getId()) {
                allLabels.set(i, label);
            }
        }
        try {
            labelRepository.saveAll(allLabels);
        } catch (IOException e) {
            IO.println("Не удалось обновить");
        }
    }

    @Override
    public void create(Label label) {
        try {
            List<Long> listValidId = postRepository.getAll().stream().map(Post::getId).toList();
            if (listValidId.contains(label.getPostId())) {
                labelRepository.create(label);
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
            ArrayList<Label> allLabels = new ArrayList<>(getAll());
            for (var i = 0; i < allLabels.size(); i++) {
                Label label = allLabels.get(i);
                if (label.getId() == id) {
                    label.setStatus(Status.DELETED);
                    allLabels.set(i, label);
                }
            }
            labelRepository.saveAll(allLabels);
        } catch (IOException e) {
            IO.println("Не удалось удалить");
        }
    }

    @Override
    public List<Label> getAll() {
        List<Label> allLabels = new ArrayList<>();
        try {
            allLabels = labelRepository.getAll();
        } catch (IOException e) {
            IO.println("Не удалось получить записи");
        }
        return allLabels;
    }


}
