package org.example.model;

import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

public class Post {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private List<Label> labels;
    private Status status;


    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static public Post fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Post.class);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public long getUserId() {
        return userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && userId == post.userId && Objects.equals(title, post.title) && Objects.equals(content, post.content) && Objects.equals(labels, post.labels) && status == post.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, content, labels, status);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", labels=" + labels +
                ", status=" + status +
                '}';
    }
}
