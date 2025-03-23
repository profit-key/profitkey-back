package com.profitkey.stock.config;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Comparator;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.ScriptUtils;

// @Component
@RequiredArgsConstructor
public class DataSqlLoader implements CommandLineRunner {

	private final DataSource dataSource;

	@Override
	public void run(String... args) throws Exception {
		Resource[] scripts = new PathMatchingResourcePatternResolver()
			.getResources("classpath:data/*.sql");

		Arrays.sort(scripts, Comparator.comparing(Resource::getFilename));

		for (Resource script : scripts) {
			try (Connection conn = dataSource.getConnection()) {
				ScriptUtils.executeSqlScript(conn, script);
				System.out.println("Executed: " + script.getFilename());
			}
		}
	}
}
