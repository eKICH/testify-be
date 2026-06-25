package com.testify.testify.mapper;

import com.testify.testify.dto.TestSuiteCreateRequest;
import com.testify.testify.dto.TestSuiteResponse;
import com.testify.testify.entity.TestSuite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = UserMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestSuiteMapper {

    @Mapping(source = "createdBy", target = "createdBy")
    TestSuiteResponse toTestSuiteResponse(TestSuite testSuite);

    TestSuite toTestSuite(TestSuiteCreateRequest request);
}
