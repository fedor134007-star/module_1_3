package org.example.controller;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.Status;
import org.example.model.Writer;
import org.example.repository.GenericRepository;
import org.example.repository.LabelRepository.LabelRepository;
import org.example.repository.PostRepository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;


public class LabelController {

    private final GenericRepository<Label, Long> labelRepository;
    private final GenericRepository<Post, Long> postRepository;

    public LabelController(GenericRepository<Label, Long> labelRepository,
                           GenericRepository<Post, Long> postRepository) {
        this.labelRepository = labelRepository;
        this.postRepository = postRepository;
    }

    public List<Label> getAll() {
        return labelRepository.getAll().stream()
                .filter(label -> label.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
    }

    public List<Post> getAllActivePosts() {
        return postRepository.getAll()
                .stream()
                .filter(el -> el.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
        // Никаких проверок на пустоту и выводов в консоль!
    }


    public Label create(String name, Long postId) {
        Label label = new Label();
        label.setPostId(postId);
        label.setName(name);
        label.setStatus(Status.ACTIVE);
        return labelRepository.create(label);
    }

    public Label update(Long id, String name) {
        Label label = new Label();
        label.setId(id);
        label.setName(name);
        label.setStatus(Status.ACTIVE);
        return labelRepository.update(label);
    }

    public void delete(Long id) {
        labelRepository.deleteById(id);
    }

    public Label getById(Long id) {
        return labelRepository.getById(id);
    }
}