package gov.iti.jets.persistance.repo;

import java.lang.reflect.Field;
import java.util.List;

public interface Repository<T, I> {

    T save(T entity);

    boolean delete(T entity);

    T findById(I id);

    List<T> findAll();

    T update(T entity);

    int getCount();

    List<T> getSinglePageContent(int pageNumber, int recordsPerPage);

    List<Object[]> getSinglePageContent(int pageNumber, int recordsPerPage, List<Field> fields);

}
