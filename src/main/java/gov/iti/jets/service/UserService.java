package gov.iti.jets.service;

import gov.iti.jets.api.user.models.UserModel;

import java.util.List;

public interface UserService {
    public List<UserModel> getAllUsers(int pageNumber, int recordsPerPage);
    public List<UserModel> getCertainUsers(int pageNumber, int recordsPerPage,String role);
    public UserModel getUserById(int id);

    public void insertUser(UserModel userModel);

    void updateUser(UserModel userModel);

    void deleteUser(int id);

}
