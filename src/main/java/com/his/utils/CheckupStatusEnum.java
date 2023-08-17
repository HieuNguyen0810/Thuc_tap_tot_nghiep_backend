package com.his.utils;

public enum CheckupStatusEnum {
    PENDING("Đang chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    REJECTED("Đã từ chối");

    private String status;
    CheckupStatusEnum(String status) {}

    public String getStatus() {
        return this.status;
    }
}
