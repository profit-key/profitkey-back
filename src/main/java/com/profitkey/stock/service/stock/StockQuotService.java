package com.profitkey.stock.service.stock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.dto.request.stock.DividendRequest;
import com.profitkey.stock.dto.request.stock.InquireDailyRequest;
import com.profitkey.stock.dto.request.stock.InquirePriceRequest;
import com.profitkey.stock.dto.request.stock.InvestOpinionRequest;
import com.profitkey.stock.dto.request.stock.StockDetailRequest;
import com.profitkey.stock.util.HeaderUtil;
import com.profitkey.stock.util.HttpClientUtil;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockQuotService {
	private final KisApiProperties kisApiProperties;
	private final StockItemService stockItemService;

	public ResponseEntity<Object> getInquirePrice(InquirePriceRequest request) {
		Object result = null;
		String urlData = kisApiProperties.getInquirePriceUrl();
		String trId = request.getTr_id();
		String mrktDivCode = request.getMrktDivCode();
		String fidInput = request.getFidInput();
		String paramData = String.format("?fid_cond_mrkt_div_code=%s&fid_input_iscd=%s", mrktDivCode, fidInput);
		String fullUrl = urlData + paramData;

		InquirePriceRequest requestParam = new InquirePriceRequest(trId, mrktDivCode, fidInput);

		try {
			URL url = new URL(fullUrl);
			String jsonString = HttpClientUtil.sendGetRequest(url, HeaderUtil.getCommonHeaders(), requestParam);
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(jsonString, Object.class);
		} catch (IOException e) {
			e.getMessage();
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Object> getInquireDaily(InquireDailyRequest request) {
		Object result = null;
		String urlData = kisApiProperties.getInquireDailyUrl();
		String trId = request.getTr_id();
		String fidCondMrktDivCode = request.getFidCondMrktDivCode();
		String fidInputIscd = request.getFidInputIscd();
		String fidInputDate1 = request.getFidInputDate1();
		String fidInputDate2 = request.getFidInputDate2();
		String fidPeriodDivCode = request.getFidPeriodDivCode();
		String fidOrgAdjPrc = request.getFidOrgAdjPrc();

		StringBuilder paramDataBuilder = new StringBuilder("?");

		paramDataBuilder.append("fid_cond_mrkt_div_code=").append(fidCondMrktDivCode).append("&");
		paramDataBuilder.append("fid_input_iscd=").append(fidInputIscd).append("&");
		paramDataBuilder.append("fid_input_date_1=").append(fidInputDate1).append("&");
		paramDataBuilder.append("fid_input_date_2=").append(fidInputDate2).append("&");
		paramDataBuilder.append("fid_period_div_code=").append(fidPeriodDivCode).append("&");
		paramDataBuilder.append("fid_org_adj_prc=").append(fidOrgAdjPrc);
		String paramData = paramDataBuilder.toString();

		String fullUrl = urlData + paramData;

		InquireDailyRequest requestParam = new InquireDailyRequest(
			trId, fidCondMrktDivCode, fidInputIscd, fidInputDate1, fidInputDate2, fidPeriodDivCode, fidOrgAdjPrc
		);

		try {
			URL url = new URL(fullUrl);
			String jsonString = HttpClientUtil.sendGetRequest(url, HeaderUtil.getCommonHeaders(), requestParam);
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(jsonString, Object.class);
		} catch (IOException e) {
			e.getMessage();
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Object> getInvestOpinion(InvestOpinionRequest request) {
		Object result = null;
		String urlData = kisApiProperties.getInvestOpinionUrl();

		String trId = request.getTr_id();
		String custtype = request.getCusttype();
		String fidCondMrktDivCode = request.getFidCondMrktDivCode();
		String fidCondScrDivCode = request.getFidCondScrDivCode();
		String fidInputIscd = request.getFidInputIscd();
		String fidInputDate1 = request.getFidInputDate1();
		String fidInputDate2 = request.getFidInputDate2();

		StringBuilder paramDataBuilder = new StringBuilder("?");
		paramDataBuilder.append("tr_id=").append(trId).append("&");
		paramDataBuilder.append("fid_cond_mrkt_div_code=").append(fidCondMrktDivCode).append("&");
		paramDataBuilder.append("fid_cond_scr_div_code=").append(fidCondScrDivCode).append("&");
		paramDataBuilder.append("fid_input_iscd=").append(fidInputIscd).append("&");
		paramDataBuilder.append("fid_input_date_1=").append(fidInputDate1).append("&");
		paramDataBuilder.append("fid_input_date_2=").append(fidInputDate2).append("&");
		paramDataBuilder.setLength(paramDataBuilder.length() - 1);

		String fullUrl = urlData + paramDataBuilder.toString();

		InvestOpinionRequest requestParam = new InvestOpinionRequest(
			trId, custtype, fidCondMrktDivCode, fidCondScrDivCode, fidInputIscd, fidInputDate1, fidInputDate2
		);

		try {
			URL url = new URL(fullUrl);
			String jsonString = HttpClientUtil.sendGetRequest(url, HeaderUtil.getCommonHeaders(), requestParam);
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(jsonString, Object.class);
		} catch (IOException e) {
			e.getMessage();
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Object> getStockDetail(StockDetailRequest request) {
		ObjectMapper objectMapper = new ObjectMapper();
		String trId1 = "FHKST01010100";
		String trId2 = "HHKDB669102C0";
		String custtype = request.getCusttype();

		String mrktDivCode = request.getMrktDivCode();
		String fidInput = request.getFidInput();

		String cts = request.getCts();
		String gb1 = request.getGb1();
		String fDt = request.getFdt();
		String tDt = request.getTdt();
		String shtCd = request.getFidInput();
		String highGb = request.getHighGb();

		InquirePriceRequest inquirePriceRequest = new InquirePriceRequest(trId1, mrktDivCode, fidInput);
		DividendRequest dividendRequest = new DividendRequest(trId2, custtype, cts, gb1, fDt, tDt, shtCd, highGb);

		ResponseEntity<Object> inquirePriceResponse = getInquirePrice(inquirePriceRequest);
		ResponseEntity<Object> dividendResponse = stockItemService.getDividend(dividendRequest);

		try {
			Map<String, Object> inquirePriceMap =
				objectMapper.convertValue(inquirePriceResponse.getBody(), new TypeReference<Map<String, Object>>() {
				});
			Map<String, Object> output = (Map<String, Object>)inquirePriceMap.get("output");

			Map<String, Object> filteredOutput = new HashMap<>();
			if (output != null) {
				filteredOutput.put("w52_hgpr", output.get("w52_hgpr"));
				filteredOutput.put("w52_lwpr", output.get("w52_lwpr"));
				filteredOutput.put("per", output.get("per"));
				filteredOutput.put("pbr", output.get("pbr"));
				filteredOutput.put("eps", output.get("eps"));
				filteredOutput.put("bps", output.get("bps"));
				filteredOutput.put("dryy_hgpr_vrss_prpr_rate", output.get("dryy_hgpr_vrss_prpr_rate"));
				filteredOutput.put("dryy_lwpr_vrss_prpr_rate", output.get("dryy_lwpr_vrss_prpr_rate"));
			}

			Map<String, Object> dividendMap =
				objectMapper.convertValue(dividendResponse.getBody(), new TypeReference<Map<String, Object>>() {
				});
			List<Map<String, Object>> output1 = (List<Map<String, Object>>)dividendMap.get("output1");

			if (output1 != null && !output1.isEmpty()) {
				Map<String, Object> firstRecord = output1.get(0);
				filteredOutput.put("per_sto_divi_amt", firstRecord.get("per_sto_divi_amt"));
				filteredOutput.put("divi_rate", firstRecord.get("divi_rate"));
			}

			return ResponseEntity.ok(filteredOutput);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL_SERVER_ERROR");
		}
	}
}
