package com.testify.testify.mapper;

import com.testify.testify.dto.TestPlanCreateRequest;
import com.testify.testify.dto.TestPlanResponse;
import com.testify.testify.entity.TestPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = UserMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestPlanMapper {

    TestPlanMapper INSTANCE = Mappers.getMapper(TestPlanMapper.class);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(target = "totalTestCases", expression = "java(testPlan.getTestCases().size())")
    TestPlanResponse toTestPlanResponse(TestPlan testPlan);

    TestPlan toTestPlan(TestPlanCreateRequest request);
}
