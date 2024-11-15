package com.sboot.kaja.vo;

public class SawonVO {

    private String id;              // ID 필드
    private Integer credit;         // 크레딧 필드

    // 생성자
    public SawonVO(String id, Integer credit) {
        this.id = id;
        this.credit = credit;
    }

    // 기본 생성자 (필요 시 추가)
    public SawonVO() {}

    // Getter와 Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    // toString() 메서드 (디버깅 시 유용)
    @Override
    public String toString() {
        return "SawonVO [id=" + id + ", credit=" + credit + "]";
    }
}
