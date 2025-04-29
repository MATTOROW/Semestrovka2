package ru.itis.semesterwork.second.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.itis.semesterwork.second.dto.request.AccountRequest;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.model.AccountEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "hashed_password", source = "password")
    @Mapping(
            target = "accountInfoEntity.description",
            source = "description",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(
            target = "accountInfoEntity.imageUrl",
            source = "imageUrl",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    AccountEntity toEntity(AccountRequest accountRequest);

    @Mapping(target = "username", source = "username")
    @Mapping(
            target = "description",
            source = "accountInfoEntity.description",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "imageUrl",
            source = "accountInfoEntity.imageUrl",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    AccountResponse toResponse(AccountEntity accountEntity);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(
            target = "description",
            source = "accountInfoEntity.description",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "imageUrl",
            source = "accountInfoEntity.imageUrl",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    AccountDetailedResponse toDetailedResponse(AccountEntity accountEntity);
}
