package com.ludogorieSoft.villagelifefrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootTest
@EnableDiscoveryClient
@EnableFeignClients
class VillagelifeFrontEndApplicationTests {

	public static void main(String[] args) {
		SpringApplication.run(VillagelifeFrontEndApplication.class, args);
	}
}
