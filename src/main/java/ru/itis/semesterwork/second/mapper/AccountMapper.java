package ru.itis.semesterwork.second.mapper;

import org.mapstruct.*;
import ru.itis.semesterwork.second.dto.request.account.AccountUpdateRequest;
import ru.itis.semesterwork.second.dto.request.auth.RegistrationRequest;
import ru.itis.semesterwork.second.dto.response.account.AccountDetailedResponse;
import ru.itis.semesterwork.second.dto.response.account.AccountResponse;
import ru.itis.semesterwork.second.model.AccountEntity;
import ru.itis.semesterwork.second.validation.model.NullableField;

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
    AccountEntity toEntity(RegistrationRequest accountRequest);

    @Mapping(target = "username", source = "username")
    @Mapping(
            target = "description",
            source = "accountInfoEntity.description",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AccountResponse toResponse(AccountEntity accountEntity);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(
            target = "description",
            source = "accountInfoEntity.description",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AccountDetailedResponse toDetailedResponse(AccountEntity accountEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "description", target = "accountInfoEntity.description")
    void updateAccountEntity(AccountUpdateRequest request, @MappingTarget AccountEntity accountEntity);

    @Condition
    default boolean isPresent(NullableField<?> optional) {
        return optional.isPresent();
    }

    default String mapOptionalToString(NullableField<String> optional) {
        return optional.value();
    }
}
