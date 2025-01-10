package org.user_management_project_JDBC.repository;

import java.util.List;

public interface Repository<T> {
    List<T> list();
    T perId(Integer id);
    void save(T t);
    void delete(Integer id);

}
