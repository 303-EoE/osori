package com.eoe.osori.domain.chat.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {
    
    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_image")
    private String memberImage;
}
