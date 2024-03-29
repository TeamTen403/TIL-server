package com.teamten.til.tiler.dto;

import com.teamten.til.tiler.entity.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TilerJoinRequest {
    private String email;
    private String passwd;
    private String nickName;
    private String job;
    private AuthProvider authProvider;

}
