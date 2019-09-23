package com.wizlah.es.mapper;

import com.wizlah.es.entity.AccountEntity;
import com.wizlah.es.web.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

  @Mapping(source = "id", target = "id", ignore = true)
  AccountEntity dtoToEntity(AccountDTO accountDto);

  @Mapping(source = "id", target = "id", ignore = true)
  void dtoToEntity(AccountDTO accountDto, @MappingTarget AccountEntity accountEntity);
}
