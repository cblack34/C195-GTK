package dao.interfaces;

import javafx.collections.ObservableList;

import java.util.Optional;

public interface Dao <T> {
    Optional<T> get(long id);

    ObservableList<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}
