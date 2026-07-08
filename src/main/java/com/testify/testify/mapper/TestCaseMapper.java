package com.testify.testify.mapper;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.entity.TestCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TestCaseMapper {

    @Mapping(source = "module.id", target = "moduleId")
    @Mapping(source = "createdBy.id", target = "createdById")
    TestCaseResponse entityToResponse(TestCase testCase);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "testPlans", ignore = true)
    void updateEntityFromRequest(TestCaseCreateRequest request, @MappingTarget TestCase testCase);

    List<TestCaseResponse> entitiesToResponses(List<TestCase> testCases);
}
