package gov.iti.jets.persistance.repo.impl;

import gov.iti.jets.api.user.models.UserModel;
import gov.iti.jets.persistance.entity.User;
import gov.iti.jets.persistance.entity.enums.Role;
import gov.iti.jets.persistance.repo.UserRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl extends RepositoryImpl<User,Integer> implements UserRepo  {
        private volatile static UserRepoImpl instance;
        private UserRepoImpl() {}
        public static UserRepoImpl getInstance() {
            if (instance == null) {
                synchronized (UserRepoImpl.class) {
                    if (instance == null) instance = new UserRepoImpl();
                }
            }
            return instance;
        }

        @Override
        public List<User> getFilteredUsers(int pageNumber, int recordsPerPage, List<Role> roles) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        List<Predicate> predicatesList = new ArrayList<>();
        for (Role role : roles) {
            Predicate predicate = cb.equal(root.get("role"), role);
            predicatesList.add(predicate);
        }
        Predicate[] finalPredicates = new Predicate[predicatesList.size()];
        Predicate predicate = cb.or(predicatesList.toArray(finalPredicates));
        criteriaQuery.where(predicate);


        List<User> result =
                entityManager
                        .createQuery(criteriaQuery)
                        .setMaxResults(recordsPerPage)
                        .setFirstResult((pageNumber - 1) * recordsPerPage)
                        .getResultList();

        return result;
    }


}
