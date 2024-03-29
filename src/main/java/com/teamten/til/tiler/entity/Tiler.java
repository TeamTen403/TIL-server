package com.teamten.til.tiler.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Entity
@Table(name = "tiler")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Tiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;

    private String nickName;
    private String passwd; // 암호화해서 저장
    private String job; // 직무정보

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider; // 로그인정보

}