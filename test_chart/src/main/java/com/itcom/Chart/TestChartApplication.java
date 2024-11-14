package com.itcom.Chart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itcom.Chart.mapper") 
public class TestChartApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestChartApplication.class, args);
	}

}
