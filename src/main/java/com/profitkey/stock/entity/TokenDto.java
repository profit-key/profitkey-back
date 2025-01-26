package com.profitkey.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenDto {

	private String grantType;
	private String accessToken;
	private String refreshToken;
	private Long accessTokenExpiresIn;
}
