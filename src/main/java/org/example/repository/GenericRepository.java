package org.example.repository;

import org.example.model.Writer;

import java.io.IOException;
import java.util.List;

public interface GenericRepository<T, ID> {
    T create(T t);

    List<T> getAll();

    T update(T t);

    T getById(ID id);

    void deleteById(ID t);


}
