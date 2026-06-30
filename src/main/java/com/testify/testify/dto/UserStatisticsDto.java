package com.testify.testify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticsDto {
    private long totalUsers;
    private long newUsersToday;

    // Explicit All-Arguments Constructor
    public UserStatisticsDto(long totalUsers, long newUsersToday) {
        this.totalUsers = totalUsers;
        this.newUsersToday = newUsersToday;
    }
}
