// package com.profitkey.stock;
//
// import static org.assertj.core.api.Assertions.*;
// import javax.sql.DataSource;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.batch.core.Job;
// import org.springframework.batch.core.JobExecution;
// import org.springframework.batch.core.JobParameters;
// import org.springframework.batch.core.JobParametersBuilder;
// import org.springframework.batch.core.JobParametersInvalidException;
// import org.springframework.batch.core.launch.JobLauncher;
// import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
// import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
// import org.springframework.batch.core.repository.JobRestartException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
//
// @ExtendWith(SpringExtension.class)
// @SpringBootTest
// class BatchSchedulerTest {
//
// 	@MockBean
// 	private DataSource dataSource;
//
// 	@Autowired
// 	private JobLauncher jobLauncher;
//
// 	@Autowired
// 	private Job problemDeleteJob;
//
// 	@Test
// 	void testRunJob() throws JobExecutionAlreadyRunningException, JobRestartException,
// 		JobInstanceAlreadyCompleteException, JobParametersInvalidException {
//
// 		JobParameters jobParameters = new JobParametersBuilder()
// 			.addLong("time", System.currentTimeMillis())
// 			.toJobParameters();
//
// 		JobExecution jobExecution = jobLauncher.run(problemDeleteJob, jobParameters);
//
// 		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
// 	}
// }
