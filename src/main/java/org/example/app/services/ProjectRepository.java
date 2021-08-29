package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T item);

    boolean removeItemById(Integer itemIdToRemove);

    boolean removeItemByParameter(T item);
}
