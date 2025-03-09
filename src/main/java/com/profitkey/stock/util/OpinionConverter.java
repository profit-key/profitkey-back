package com.profitkey.stock.util;

import com.profitkey.stock.dto.InvestmentOpinion;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OpinionConverter {
	public static List<Map<String, Object>> convertOpinions(List<Map<String, Object>> responseList) {
		return responseList.stream()
			.map(data -> {
				String opinion = (String)data.get("invt_opnn");
				String translatedOpinion = InvestmentOpinion.translate(opinion);
				data.put("invt_opnn", translatedOpinion);
				return data;
			})
			.collect(Collectors.toList());
	}
}
