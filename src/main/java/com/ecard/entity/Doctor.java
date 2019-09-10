package com.ecard.entity;

public class Doctor {
    private Long doctorNo;

    private Long cloudPassNo;

    private String name;

    private Integer age;

    private String idCard;

    private String phone;

    public Long getDoctorNo() {
        return doctorNo;
    }

    public void setDoctorNo(Long doctorNo) {
        this.doctorNo = doctorNo;
    }

    public Long getCloudPassNo() {
        return cloudPassNo;
    }

    public void setCloudPassNo(Long cloudPassNo) {
        this.cloudPassNo = cloudPassNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}