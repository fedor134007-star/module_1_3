package org.example.model;

import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

public class Writer {
    private long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Status status;


    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }


    static public Writer fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Writer.class);
    }


    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return id == writer.id && Objects.equals(firstName, writer.firstName) && Objects.equals(lastName, writer.lastName) && Objects.equals(posts, writer.posts) && status == writer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, posts, status);
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=" + posts +
                ", status=" + status +
                '}';
    }
}
