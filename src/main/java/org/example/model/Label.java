package org.example.model;

import com.google.gson.Gson;

import java.util.Objects;

public class Label {
    private Long id;
    private Long postId;
    private String name;
    private Status status;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static public Label fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Label.class);
    }

    public Long getPostId() {
        return postId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id == label.id && postId == label.postId && Objects.equals(name, label.name) && status == label.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postId, name, status);
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", postId=" + postId +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
