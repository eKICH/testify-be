package com.testify.testify.mapper;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.entity.TestCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestCaseMapper {

    TestCaseMapper INSTANCE = Mappers.getMapper(TestCaseMapper.class);

    @Mapping(source = "createdBy.id", target = "createdBy.id")
    @Mapping(source = "createdBy.username", target = "createdBy.username")
    @Mapping(source = "createdBy.firstName", target = "createdBy.firstName")
    @Mapping(source = "createdBy.lastName", target = "createdBy.lastName")
    TestCaseResponse toTestCaseResponse(TestCase testCase);

    TestCase toTestCase(TestCaseCreateRequest request);
}
