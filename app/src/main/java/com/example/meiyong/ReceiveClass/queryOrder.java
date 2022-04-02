package com.example.meiyong.ReceiveClass;

import java.time.LocalDateTime;

public class queryOrder {

    private Long orderId;

    private String receiverName;
    private String phone;
    private String deliveryAddress;
    private String packagePos;

    private LocalDateTime startTime;

    private LocalDateTime deliveryStartTime;

    private Integer stationId;

    private LocalDateTime endTime;
    private Integer deliveryDeviceId;

    private Integer status;

    public Long getOrderId() {
        return orderId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getPhone() {
        return phone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getPackagePos() {
        return packagePos;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public Integer getStationId() {
        return stationId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Integer getDeliveryDeviceId() {
        return deliveryDeviceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setPackagePos(String packagePos) {
        this.packagePos = packagePos;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDeliveryStartTime(LocalDateTime deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setDeliveryDeviceId(Integer deliveryDeviceId) {
        this.deliveryDeviceId = deliveryDeviceId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
