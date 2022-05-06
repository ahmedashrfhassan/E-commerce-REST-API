package gov.iti.jets.api.order.mappers;

import gov.iti.jets.api.order.models.OrderModel;
import gov.iti.jets.api.product.mapper.ProductMapper;
import gov.iti.jets.persistance.entity.Order;
import gov.iti.jets.persistance.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE= Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "maker", target = "userName", qualifiedByName = "getUserName")
    @Mapping(source = "maker", target = "userId", qualifiedByName = "getUserId")
    List<OrderModel> fromOrdersToOrderModels(List<Order> orders);

    @Named("getUserName")
    public static String getUserName(User user){
        return user.getFirstName();
    }
    @Named("getUserId")
    public static int getUserId(User user){
        return user.getId();
    }
}
