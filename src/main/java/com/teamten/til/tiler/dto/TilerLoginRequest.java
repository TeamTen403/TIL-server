package com.teamten.til.tiler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TilerLoginRequest {

    private String email;
    private String passwd;
}
