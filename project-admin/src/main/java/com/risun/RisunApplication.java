package com.risun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RisunApplication {
	public static void main(String[] args) {
		SpringApplication.run(RisunApplication.class, args);
		System.out.println("XX项目启动成功");
	}
}
