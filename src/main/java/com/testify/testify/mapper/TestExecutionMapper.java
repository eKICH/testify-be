package com.testify.testify.mapper;

import com.testify.testify.dto.TestExecutionResponse;
import com.testify.testify.entity.TestExecution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TestExecutionMapper {

    TestExecutionMapper INSTANCE = Mappers.getMapper(TestExecutionMapper.class);

    @Mapping(source = "executedBy", target = "executedBy")
    @Mapping(source = "testRun.id", target = "testRunId")
    @Mapping(source = "testCase.id", target = "testCaseId")
    TestExecutionResponse toTestExecutionResponse(TestExecution testExecution);
}
