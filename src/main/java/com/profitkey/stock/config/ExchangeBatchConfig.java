package com.profitkey.stock.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.profitkey.stock.dto.ExchangeDto;
import com.profitkey.stock.util.ExchangeUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ExchangeBatchConfig {

	private final ExchangeUtils exchangeUtils;

	@Bean
	public Job exchangeJob(JobRepository jobRepository,
		Step step) {
		return new JobBuilder("exchangeJob", jobRepository)
			.start(step)
			.build();
	}

	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager tm, Tasklet tasklet) {
		return new StepBuilder("step", jobRepository)
			.tasklet(tasklet, tm)
			.build();
	}

	@Bean
	public Tasklet tasklet() {
		return ((contribution, chunkContext) -> {
			List<ExchangeDto> exchangeDtoList = exchangeUtils.getExchangeDataAsDtoList();

			for (ExchangeDto exchangeDto : exchangeDtoList) {
				log.info("통화 : " + exchangeDto.getCur_nm());
				log.info("환율 : " + exchangeDto.getDeal_bas_r());
				// 추가적인 필드가 있다면 출력 또는 활용
			}
			return RepeatStatus.FINISHED;
		});
	}
}
