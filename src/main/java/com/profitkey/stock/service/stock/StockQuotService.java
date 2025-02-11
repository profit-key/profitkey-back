package com.profitkey.stock.service.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.dto.request.stock.InquireDailyRequest;
import com.profitkey.stock.dto.request.stock.InquirePriceRequest;
import com.profitkey.stock.dto.request.stock.InvestOpinionRequest;
import com.profitkey.stock.util.HeaderUtil;
import com.profitkey.stock.util.HttpClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockQuotService {
    private final KisApiProperties kisApiProperties;

    public ResponseEntity<Object> getInquirePrice(InquirePriceRequest request) {
        Object result = null;
        String urlData = kisApiProperties.getInquirePriceUrl();
        String trId = request.getTr_id();
        String mrktDivCode = request.getMrktDivCode();
        String fidInput = request.getFidInput();
        String paramData = String.format("?fid_cond_mrkt_div_code=%s&fid_input_iscd=%s", mrktDivCode, fidInput);
        String fullUrl = urlData + paramData;

        InquirePriceRequest requestParam = new InquirePriceRequest(trId, mrktDivCode, fidInput);

        log.info("full url : {}", fullUrl);
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

        log.info("full url : {}", fullUrl);
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
}
