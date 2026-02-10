package org.example.controller;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.Status;
import org.example.model.Writer;
import org.example.repository.GenericRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class WriterController {

    private final GenericRepository<Writer, Long> writerRepository;
    private final GenericRepository<Post, Long> postRepository;
    private final GenericRepository<Label, Long> labelRepository;

    public WriterController(GenericRepository<Writer, Long> writerRepository, GenericRepository<Post, Long> postRepository, GenericRepository<Label, Long> labelRepository) {
        this.writerRepository = writerRepository;
        this.postRepository = postRepository;
        this.labelRepository = labelRepository;
    }

    public List<Writer> getAllActiveWriters() {
        Map<Long, List<Label>> labelsByPostId = labelRepository.getAll().stream()
                .filter(l -> l.getStatus() == Status.ACTIVE && l.getPostId() != null)
                .collect(Collectors.groupingBy(Label::getPostId));

        Map<Long, List<Post>> postsByWriterId = postRepository.getAll().stream()
                .filter(p -> p.getStatus() == Status.ACTIVE)
                .peek(p -> p.setLabels(labelsByPostId.getOrDefault(p.getId(), new ArrayList<>())))
                .collect(Collectors.groupingBy(Post::getUserId, Collectors.toList()));

        return writerRepository.getAll().stream()
                .filter(w -> w.getStatus() == Status.ACTIVE)
                .peek(w -> w.setPosts(postsByWriterId.getOrDefault(w.getId(), new ArrayList<>())))
                .collect(Collectors.toList());
    }

    public Writer create(String firstName, String lastName) {
        Writer writer = new Writer();
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        writer.setStatus(Status.ACTIVE);
        return writerRepository.create(writer);
    }

    public Writer update(Long id, String firstName, String lastName) {
        Writer writer = new Writer();
        writer.setId(id);
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        return writerRepository.update(writer);
    }

    public void delete(Long id) {
        writerRepository.deleteById(id);
    }

    public Writer getById(Long id) {
        return writerRepository.getById(id);
    }
}