package com.testify.testify.mapper;

import com.testify.testify.dto.ApplicationResponse;
import com.testify.testify.dto.ModuleResponse;
import com.testify.testify.entity.Application;
import com.testify.testify.entity.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    ApplicationResponse toResponse(Application application);

    /**
     * Maps a Module entity to a ModuleResponse DTO.
     * The @Mapping annotations handle nested object properties.
     *
     * @param module The source Module entity.
     * @return The mapped ModuleResponse DTO.
     */
    @Mapping(source = "application.id", target = "applicationId")
    @Mapping(source = "parentModule.id", target = "parentModuleId")
    ModuleResponse toResponse(Module module);
}