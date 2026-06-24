package com.testify.testify.mapper;

import com.testify.testify.dto.TestRunCreateRequest;
import com.testify.testify.dto.TestRunResponse;
import com.testify.testify.entity.TestRun;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TestRunMapper {

    TestRunMapper INSTANCE = Mappers.getMapper(TestRunMapper.class);

    @Mapping(source = "createdBy", target = "createdBy")
    TestRunResponse toTestRunResponse(TestRun testRun);

    TestRun toTestRun(TestRunCreateRequest request);
}
