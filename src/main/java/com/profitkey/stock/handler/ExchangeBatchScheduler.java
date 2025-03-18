package com.profitkey.stock.handler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExchangeBatchScheduler {
    
	private final JobLauncher jobLauncher;
	private final Job job;

	@Scheduled(cron = "0/10 * * * * *") // 10초
	public void runJob() throws Exception {
		JobParameters parameters = new JobParametersBuilder()
			.addString("jobName", "exchangeJob" + System.currentTimeMillis())
			.toJobParameters();

		jobLauncher.run(job, parameters);
	}
}