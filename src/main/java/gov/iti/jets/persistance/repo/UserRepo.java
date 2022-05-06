package gov.iti.jets.persistance.repo;

import gov.iti.jets.persistance.entity.Product;
import gov.iti.jets.persistance.entity.User;
import gov.iti.jets.persistance.entity.enums.Role;

import java.util.List;

public interface UserRepo extends Repository<User,Integer>{
    public List<User> getFilteredUsers(int pageNumber, int recordsPerPage, List<Role> roles);
}
