package gov.iti.jets.persistance.repo.impl;

import gov.iti.jets.persistance.repo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryImpl<T, I> implements Repository<T, I> {

    protected final EntityManager entityManager = Connector.getInstance().getEntityManager();
    private final Class<T> clazz;

    protected RepositoryImpl() {
        clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T save(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public boolean delete(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public T findById(I id) {
        // entityManager.refresh(id);
        return entityManager.find(clazz, id);
    }

    @Override
    public List<T> findAll() {
        List<T> resultList = (List<T>) entityManager.createQuery("FROM " + clazz.getSimpleName()).getResultList();
        return resultList;
    }

    @Override
    public T update(T entity) {
        System.out.println("inside Repo Impl");
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public int getCount() {
        Query queryTotal = entityManager.createQuery("Select count(p) from " + clazz.getSimpleName() + " p");
        long countResult = (long) queryTotal.getSingleResult();
        return (int) countResult;
    }

    @Override
    public List<T> getSinglePageContent(int pageNumber, int recordsPerPage) {
        Query query = entityManager.createQuery("FROM " + clazz.getSimpleName());
        query.setFirstResult((pageNumber - 1) * recordsPerPage);
        query.setMaxResults(recordsPerPage);
        List<T> BeansPerSinglePage = query.getResultList();
        return BeansPerSinglePage;
    }

    @Override
    public List<Object[]> getSinglePageContent(int pageNumber, int recordsPerPage, List<Field> fields) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

        Root<T> root = query.from(clazz);
        List<Selection<?>> selectionList = new ArrayList<>();
        fields.forEach(f -> selectionList.add(root.get(f.getName())));
        query.multiselect(selectionList);
        List<Object[]> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }


}
