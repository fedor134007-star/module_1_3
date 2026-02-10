package org.example.view.WriterView;

import org.example.model.Writer;

import java.util.List;

public interface WriterView {

    public void update(Writer writer);

    public void create(Writer writer);

    public void delete(long id);

    public List<Writer> getAll();

}
