package org.example.controller;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.Status;
import org.example.model.Writer;
import org.example.repository.GenericRepository;
import org.example.repository.PostRepository.PostRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class PostController {

    private final GenericRepository<Post, Long> postRepository;
    private final GenericRepository<Label, Long> labelRepository;
    private final GenericRepository<Writer, Long> writerRepository;

    public PostController(GenericRepository<Post, Long> postRepository,
                          GenericRepository<Label, Long> labelRepository,
                          GenericRepository<Writer, Long> writerRepository) {
        this.postRepository = postRepository;
        this.labelRepository = labelRepository;
        this.writerRepository = writerRepository;
    }

    // получаем все посты и лейблы, добавляем лейблы к постам по совпавшим ID
    public List<Post> getAll() {
        Map<Long, List<Label>> labelsByPostId = labelRepository.getAll().stream()
                .filter(l -> l.getStatus() == Status.ACTIVE && l.getPostId() != null)
                .collect(Collectors.groupingBy(Label::getPostId));

        return postRepository.getAll().stream()
                .filter(p -> p.getStatus() == Status.ACTIVE)
                .peek(p -> p.setLabels(labelsByPostId.getOrDefault(p.getId(), new ArrayList<>())))
                .collect(Collectors.toList());
    }


    public List<Writer> getAllActiveWriters() {
        return writerRepository.getAll()
                .stream()
                .filter(el -> el.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
        // Никаких проверок на пустоту и выводов в консоль!
    }

    public Post create(String title, String content, Long userId) {
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setContent(content);
        post.setStatus(Status.ACTIVE);
        return postRepository.create(post);
    }

    public Post update(Long id, String title, String content) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setContent(content);
        post.setStatus(Status.ACTIVE);
        return postRepository.update(post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post getById(Long id) {
        return postRepository.getById(id);
    }
}