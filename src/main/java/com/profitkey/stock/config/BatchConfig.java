package com.profitkey.stock.config;

import com.profitkey.stock.repository.StockRepository;
import com.profitkey.stock.service.stock.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class BatchConfig {

	private final StockRepository stockRepository;
	private final StockService stockService;
	private final org.springframework.batch.core.repository.JobRepository jobRepository;
	private final org.springframework.transaction.PlatformTransactionManager transactionManager;

	public BatchConfig(StockRepository stockRepository,
		StockService stockService,
		org.springframework.batch.core.repository.JobRepository jobRepository,
		org.springframework.transaction.PlatformTransactionManager transactionManager) {
		this.stockRepository = stockRepository;
		this.stockService = stockService;
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@Bean
	public Job createStockInfoJob() {
		return new JobBuilder("createStockInfoJob", jobRepository)
			.start(createStockInfoStep())
			.build();
	}

	@Bean
	public Step createStockInfoStep() {
		log.info("createStockInfoStep batch-------------------------*********");

		// stockRepository.deleteAll();

		return new StepBuilder("createStockInfoStep", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				stockService.createStockInfo();
				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}
}
