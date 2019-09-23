package com.wizlah.es.mapper;

import com.wizlah.es.entity.RoleEntity;
import com.wizlah.es.web.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {
    @Mapping(source = "id", target = "id", ignore = true)
    RoleEntity dtoToEntity(RoleDTO roleDto);

    @Mapping(source = "id", target = "id", ignore = true)
    void dtoToEntity(RoleDTO roleDto, @MappingTarget RoleEntity roleEntity);
}
