package org.example.repository;

import java.io.IOException;
import java.util.List;

public interface GenericRepository<T, ID> {
    public void create(T t) throws IOException;

    public void saveAll(List<T> t) throws IOException;

    public List<T> getAll() throws IOException;
}
