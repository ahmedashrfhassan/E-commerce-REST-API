package gov.iti.jets.service.impls;

import gov.iti.jets.api.exceptions.IllegalInputException;
import gov.iti.jets.api.exceptions.MyNotFoundException;
import gov.iti.jets.api.user.mapper.UserMapper;
import gov.iti.jets.api.user.models.UserModel;
import gov.iti.jets.persistance.entity.User;
import gov.iti.jets.persistance.entity.enums.Role;
import gov.iti.jets.persistance.repo.UserRepo;
import gov.iti.jets.persistance.repo.impl.UserRepoImpl;
import gov.iti.jets.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final UserServiceImpl INSTANCE=new UserServiceImpl();
    private UserServiceImpl(){}
    public static UserServiceImpl getInstance(){
        return INSTANCE;
    }
    UserRepo userRepo = UserRepoImpl.getInstance();

    @Override
    public List<UserModel> getAllUsers(int pageNumber, int recordsPerpage) {
        List<UserModel> userModels = new ArrayList<>();
        List<User> users = new ArrayList<>();
        users = UserRepoImpl.getInstance().getSinglePageContent(pageNumber,recordsPerpage );
        userModels = UserMapper.INSTANCE.toUserModelListFromUserList(users);
        return userModels;
    }

    @Override
    public List<UserModel> getCertainUsers(int pageNumber, int recordsPerPage, String role) {
        List<UserModel> userModels = new ArrayList<>();
        if(!role.isEmpty()||role==null){
            if (role.equalsIgnoreCase("all")){
                List<User> users = userRepo.getSinglePageContent(pageNumber,recordsPerPage);
                userModels = UserMapper.INSTANCE.toUserModelListFromUserList(users);
            }else {
                List<Role> roles = new ArrayList<>();
                try{
                    roles.add(Role.valueOf((role).toUpperCase()));
                }catch (IllegalArgumentException e){
                    throw new IllegalInputException("wrongly entered role");
                }
                List<User> users = userRepo.getFilteredUsers(pageNumber,recordsPerPage,roles);
                userModels = UserMapper.INSTANCE.toUserModelListFromUserList(users);
            }
        }else throw new IllegalInputException("role is empty!!");
      return userModels;
    }

    @Override
    public UserModel getUserById(int id) {
        if (id <= 0) {
            throw new IllegalInputException("userId entry is not accepted");
        }
        User user = UserRepoImpl.getInstance().findById(id);
        if(user==null){
            throw new MyNotFoundException("user not found");
        }
        UserModel userModel = UserMapper.INSTANCE.entityToModel(user);
        return userModel;
    }

    @Override
    public void insertUser(UserModel userModel) {
        User user = UserMapper.INSTANCE.fromUserModelToUser(userModel);
        user.setRole(Role.CUSTOMER);
        user.setId(0);
        UserRepoImpl.getInstance().save(user);
    }

    @Override
    public void updateUser(UserModel userModel) {
        if (userModel.getId() < 0) {
            throw new MyNotFoundException("user not found");
        }
        User user = UserRepoImpl.getInstance().findById(userModel.getId());
        if (user == null) {
            user = new User(userModel.getId(),userModel.getFirstName(),userModel.getLastName(),userModel.getEmail(), Role.CUSTOMER);
        }
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        UserRepoImpl.getInstance().update(user);
    }

    @Override
    public void deleteUser(int id) {
        if (id < 0) {
            throw new MyNotFoundException("user not found");
        }
        User user = UserRepoImpl.getInstance().findById(id);
        if (user == null) {
            throw new MyNotFoundException("user not found");
        }
        UserRepoImpl.getInstance().delete(user);
    }
}
