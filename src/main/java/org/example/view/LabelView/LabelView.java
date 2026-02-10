package org.example.view.LabelView;

import org.example.model.Label;

import java.util.List;

public interface LabelView {

    public void update(Label label);

    public void create(Label label);

    public void delete(long id);

    public List<Label> getAll();

}
