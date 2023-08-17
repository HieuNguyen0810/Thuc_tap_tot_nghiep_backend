package com.his.dto;

import com.his.entity.Target;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class TargetDto {
    private Long id;
    private String code;
    private String name;
    private LocalDateTime createTime;
    private String createdBy;
    private boolean status;

    public TargetDto(Target entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.createTime = entity.getCreateTime();
        this.createdBy = entity.getCreatedBy();
        this.status = entity.isStatus();
    }
}
