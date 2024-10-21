package com.stickynotes.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO
{
    public String refreshToken;

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public String setRefreshToken(String refrehToken)
    {
        return this.refreshToken=refreshToken;
    }

}
