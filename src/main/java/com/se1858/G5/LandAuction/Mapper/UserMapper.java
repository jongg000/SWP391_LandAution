package com.se1858.G5.LandAuction.Mapper;

import com.se1858.G5.LandAuction.DTO.Request.UserRegisterRequest;
import com.se1858.G5.LandAuction.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRegisterRequest request);

}
