package gov.iti.jets.api.user.mapper;

import gov.iti.jets.api.product.mapper.ProductMapper;
import gov.iti.jets.api.product.model.ProductModel;
import gov.iti.jets.api.user.models.UserModel;
import gov.iti.jets.persistance.entity.Product;
import gov.iti.jets.persistance.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE= Mappers.getMapper(UserMapper.class);
    UserModel entityToModel(User user);

    List<UserModel> toUserModelListFromUserList(List<User> users);

    User fromUserModelToUser(UserModel userModel);
}
