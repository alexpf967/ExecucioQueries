package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	public static int cost;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	public static void sum_cost(int n) {
		cost = cost+n;
	}

}
