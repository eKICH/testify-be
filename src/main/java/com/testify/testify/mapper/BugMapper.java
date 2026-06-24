package com.testify.testify.mapper;

import com.testify.testify.dto.BugCreateRequest;
import com.testify.testify.dto.BugResponse;
import com.testify.testify.entity.Bug;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = UserMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BugMapper {

    BugMapper INSTANCE = Mappers.getMapper(BugMapper.class);

    @Mapping(source = "assignedTo", target = "assignedTo")
    @Mapping(source = "reportedBy", target = "reportedBy")
    BugResponse toBugResponse(Bug bug);

    Bug toBug(BugCreateRequest request);
}
