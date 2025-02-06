package com.sparta.cloud.movie_reservation_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MovieReservationGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieReservationGatewayApplication.class, args);
	}

}
