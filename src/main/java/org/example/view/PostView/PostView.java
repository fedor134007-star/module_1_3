package org.example.view.PostView;

import org.example.model.Post;
import org.example.model.Writer;

import java.util.List;

public interface PostView {

    public void update(Post post);

    public void create(Post post);

    public void delete(long id);

    public List<Post> getAll();

}
