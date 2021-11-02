package co.id.wahyu.auth.jwt.service.base;

import java.util.List;

/**
 *
 * @param <T> Entity type
 * @param <ID> Id type of entity
 */
public interface BaseCrudService <T, ID> {

    List<T> getAll();

    T getById(ID id);

    T create(T data);

    T update(ID id, T data);

    T delete(ID id);
}
