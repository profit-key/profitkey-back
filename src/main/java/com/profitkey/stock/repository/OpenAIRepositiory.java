package com.profitkey.stock.repository;

import com.profitkey.stock.entity.AiAnalysisOpinion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenAIRepositiory extends JpaRepository<AiAnalysisOpinion, String> {
	Optional<AiAnalysisOpinion> findTopByOrderByCreatedAtDesc();
}
