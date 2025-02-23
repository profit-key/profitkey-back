package com.profitkey.stock.service.stock;

import com.profitkey.stock.dto.response.stock.StockInfoResponse;
import com.profitkey.stock.entity.StockInfo;
import com.profitkey.stock.repository.stock.StockInfoRepository;
import com.profitkey.stock.util.DateTimeUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockInfoService {

	private final StockInfoRepository stockInfoRepository;

	public List<StockInfoResponse> getMarketCap() {
		String baseDate = DateTimeUtil.curDate("");
		List<StockInfo> stockInfos = stockInfoRepository.findByBaseDate(baseDate);
		return stockInfos.stream()
			.map(StockInfoResponse::new)
			.collect(Collectors.toList());
	}

}
