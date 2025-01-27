package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquirePriceRequest {
	@Schema(description = "거래ID FHKST01010100 : 주식현재가 시세", example = "FHKST01010100")
	private String tr_id;
	@Schema(description = "FID 조건 시장 분류 코드 J : 주식, ETF, ETN", example = "J")
	private String mrktDivCode;
	@Schema(description = "FID 입력 종목코드", example = "000660")
	private String fidInput;
	@Schema(description = "고객 타입 P: 개인, B: 법인", example = "P")
	private String custtype;
	// @Schema(description = "시퀀스 번호", example = " ")
	// private String seq_no;
	// @Schema(description = "MAC 주소", example = "00:1A:2B:3C:4D:5E")
	// private String mac_address;
	// @Schema(description = "전화번호", example = "P01012345678")
	// private String phone_num;
	// @Schema(description = "IP 주소", example = "000.000.000")
	// private String ip_addr;
	// @Schema(description = "해시 키", example = "12345")
	// private String hashkey;
	// @Schema(description = "Global UID", example = "12345")
	// private String gt_uid;

	public InquirePriceRequest(String tr_id, String fid_cond_mrkt_div_code, String fid_input_iscd) {
		this.tr_id = tr_id;
		this.mrktDivCode = fid_cond_mrkt_div_code;
		this.fidInput = fid_input_iscd;
	}

}
