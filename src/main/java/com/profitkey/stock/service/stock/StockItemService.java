package com.profitkey.stock.service.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.dto.request.stock.FinancialRatioRequest;
import com.profitkey.stock.dto.request.stock.GrowthRatioRequest;
import com.profitkey.stock.dto.request.stock.IncomeStatementRequest;
import com.profitkey.stock.dto.request.stock.ProfitRatioRequest;
import com.profitkey.stock.dto.request.stock.StabilityRatioRequest;
import com.profitkey.stock.util.HeaderUtil;
import com.profitkey.stock.util.HttpClientUtil;
import java.io.IOException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockItemService {
	private final KisApiProperties kisApiProperties;

	public ResponseEntity<Object> getIncomeStatement(IncomeStatementRequest request) {
		Object result = null;
		String urlData = kisApiProperties.getIncomeStatementUrl();

		String trId = request.getTr_id();
		String custtype = request.getCusttype();
		String fidDivClsCode = request.getFidDivClsCode();
		String fidCondMrktDivCode = request.getFidCondMrktDivCode();
		String fidInputIscd = request.getFidInputIscd();

		StringBuilder paramDataBuilder = new StringBuilder("?");
		paramDataBuilder.append("tr_id=").append(trId).append("&");
		paramDataBuilder.append("custtype=").append(custtype).append("&");
		paramDataBuilder.append("FID_DIV_CLS_CODE=").append(fidDivClsCode).append("&");
		paramDataBuilder.append("fid_cond_mrkt_div_code=").append(fidCondMrktDivCode).append("&");
		paramDataBuilder.append("fid_input_iscd=").append(fidInputIscd).append("&");
		paramDataBuilder.setLength(paramDataBuilder.length() - 1);

		String fullUrl = urlData + paramDataBuilder.toString();

		IncomeStatementRequest requestParam = new IncomeStatementRequest(
			trId, custtype, fidDivClsCode, fidCondMrktDivCode, fidInputIscd
		);

		log.info("full url : {}", fullUrl);

		try {
			URL url = new URL(fullUrl);
			String jsonString = HttpClientUtil.sendGetRequest(url, HeaderUtil.getCommonHeaders(), requestParam);
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(jsonString, Object.class);
		} catch (IOException e) {
			log.error("Error fetching data: {}", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Object> getFinancialRatio(IncomeStatementRequest request) {
		Object result = null;
		String urlData = kisApiProperties.getFinancialRatioUrl();

		String trId = request.getTr_id();
		String custtype = request.getCusttype();
		String fidDivClsCode = request.getFidDivClsCode();
		String fidCondMrktDivCode = request.getFidCondMrktDivCode();
		String fidInputIscd = request.getFidInputIscd();

		StringBuilder paramDataBuilder = new StringBuilder("?");
		paramDataBuilder.append("tr_id=").append(trId).append("&");
		paramDataBuilder.append("custtype=").append(custtype).append("&");
		paramDataBuilder.append("FID_DIV_CLS_CODE=").append(fidDivClsCode).append("&");
		paramDataBuilder.append("fid_cond_mrkt_div_code=").append(fidCondMrktDivCode).append("&");
		paramDataBuilder.append("fid_input_iscd=").append(fidInputIscd).append("&");
		paramDataBuilder.setLength(paramDataBuilder.length() - 1);

		String fullUrl = urlData + paramDataBuilder.toString();

		FinancialRatioRequest requestParam = new FinancialRatioRequest(
			trId, custtype, fidDivClsCode, fidCondMrktDivCode, fidInputIscd
		);

		log.info("full url : {}", fullUrl);

		try {
			URL url = new URL(fullUrl);
			String jsonString = HttpClientUtil.sendGetRequest(url, HeaderUtil.getCommonHeaders(), requestParam);
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(jsonString, Object.class);
		} catch (IOException e) {
			log.error("Error fetching data: {}", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Object> getProfitRatio(ProfitRatioRequest request) {
		Object result = null;
		String urlData = kisApiProperties.getProfitRatioUrl();

		String trId = request.getTr_id();
		String fidInputIscd = request.getFidInputIscd();
		String fidDivClsCode = request.getFidDivClsCode();
		String fidCondMrktDivCode = request.getFidCondMrktDivCode();

		StringBuilder paramDataBuilder = new StringBuilder("?");
		paramDataBuilder.append("tr_id=").append(trId).append("&");
		paramDataBuilder.append("fid_input_iscd=").append(fidInputIscd).append("&");
		paramDataBuilder.append("FID_DIV_CLS_CODE=").append(fidDivClsCode).append("&");
		paramDataBuilder.append("fid_cond_mrkt_div_code=").append(fidCondMrktDivCode).append("&");
		paramDataBuilder.setLength(paramDataBuilder.length() - 1);

		String fullUrl = urlData + paramDataBuilder.toString();

		ProfitRatioRequest requestParam = new ProfitRatioRequest(
			trId, fidInputIscd, fidDivClsCode, fidCondMrktDivCode
		);

		log.info("full url : {}", fullUrl);

		try {
			URL url = new URL(fullUrl);
			String jsonString = HttpClientUtil.sendGetRequest(url, HeaderUtil.getCommonHeaders(), requestParam);
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(jsonString, Object.class);
		} catch (IOException e) {
			log.error("Error fetching data: {}", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Object> getStabilityRatio(StabilityRatioRequest request) {
		Object result = null;
		String urlData = kisApiProperties.getStabilityRatioUrl();

		String trId = request.getTr_id();
		String custtype = request.getCusttype();
		String fidInputIscd = request.getFidInputIscd();
		String fidDivClsCode = request.getFidDivClsCode();
		String fidCondMrktDivCode = request.getFidCondMrktDivCode();

		StringBuilder paramDataBuilder = new StringBuilder("?");
		paramDataBuilder.append("tr_id=").append(trId).append("&");
		paramDataBuilder.append("custtype=").append(custtype).append("&");
		paramDataBuilder.append("fid_input_iscd=").append(fidInputIscd).append("&");
		paramDataBuilder.append("fid_div_cls_code=").append(fidDivClsCode).append("&");
		paramDataBuilder.append("fid_cond_mrkt_div_code=").append(fidCondMrktDivCode).append("&");
		paramDataBuilder.setLength(paramDataBuilder.length() - 1);

		String fullUrl = urlData + paramDataBuilder.toString();

		StabilityRatioRequest requestParam = new StabilityRatioRequest(
			trId, custtype, fidInputIscd, fidDivClsCode, fidCondMrktDivCode
		);

		log.info("full url : {}", fullUrl);

		try {
			URL url = new URL(fullUrl);
			String jsonString = HttpClientUtil.sendGetRequest(url, HeaderUtil.getCommonHeaders(), requestParam);
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(jsonString, Object.class);
		} catch (IOException e) {
			log.error("Error fetching data: {}", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Object> getGrowthRatio(GrowthRatioRequest request) {
		Object result = null;
		String urlData = kisApiProperties.getGrowthRatioUrl();

		String trId = request.getTr_id();
		String custtype = request.getCusttype();
		String fidInputIscd = request.getFidInputIscd();
		String fidDivClsCode = request.getFidDivClsCode();
		String fidCondMrktDivCode = request.getFidCondMrktDivCode();

		StringBuilder paramDataBuilder = new StringBuilder("?");
		paramDataBuilder.append("tr_id=").append(trId).append("&");
		paramDataBuilder.append("custtype=").append(custtype).append("&");
		paramDataBuilder.append("fid_input_iscd=").append(fidInputIscd).append("&");
		paramDataBuilder.append("fid_div_cls_code=").append(fidDivClsCode).append("&");
		paramDataBuilder.append("fid_cond_mrkt_div_code=").append(fidCondMrktDivCode).append("&");
		paramDataBuilder.setLength(paramDataBuilder.length() - 1);

		String fullUrl = urlData + paramDataBuilder.toString();

		GrowthRatioRequest requestParam = new GrowthRatioRequest(
			trId, custtype, fidInputIscd, fidDivClsCode, fidCondMrktDivCode
		);

		log.info("full url : {}", fullUrl);

		try {
			URL url = new URL(fullUrl);
			String jsonString = HttpClientUtil.sendGetRequest(url, HeaderUtil.getCommonHeaders(), requestParam);
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(jsonString, Object.class);
		} catch (IOException e) {
			log.error("Error fetching data: {}", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}
}
