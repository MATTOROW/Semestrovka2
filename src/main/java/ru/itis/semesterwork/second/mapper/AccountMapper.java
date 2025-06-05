package ru.itis.semesterwork.second.mapper;

import org.mapstruct.*;
import ru.itis.semesterwork.second.dto.request.AccountUpdateRequest;
import ru.itis.semesterwork.second.dto.request.RegistrationRequest;
import ru.itis.semesterwork.second.dto.response.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.AccountResponse;
import ru.itis.semesterwork.second.model.AccountEntity;

import java.util.Optional;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "hashed_password", ignore = true)
    @Mapping(
            target = "accountInfoEntity.description",
            ignore = true
    )
    @Mapping(
            target = "accountInfoEntity.iconUrl",
            ignore = true
    )
    AccountEntity toEntity(RegistrationRequest accountRequest);

    @Mapping(target = "username", source = "username")
    @Mapping(
            target = "description",
            source = "accountInfoEntity.description",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "iconUrl",
            source = "accountInfoEntity.iconUrl",
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
            target = "iconUrl",
            source = "accountInfoEntity.iconUrl",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    AccountDetailedResponse toDetailedResponse(AccountEntity accountEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "description", target = "accountInfoEntity.description")
    void updateAccountEntity(AccountUpdateRequest request, @MappingTarget AccountEntity accountEntity);

    @Condition
    default boolean isPresent(Optional<?> optional) {
        return optional.isPresent();
    }

    default String mapOptionalToString(Optional<String> optional) {
        return optional.orElse(null);
    }
}
