package com.spirit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import cn.hutool.core.lang.Console;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpiritApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpiritApplication.class, args);
		Console.log("XX项目启动成功");
	}
}
