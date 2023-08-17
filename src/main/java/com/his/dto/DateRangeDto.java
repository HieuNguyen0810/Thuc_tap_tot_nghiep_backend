package com.his.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DateRangeDto {
    private LocalDateTime from;
    private LocalDateTime to;
}
