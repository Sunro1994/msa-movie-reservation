package com.sparta.cloud.movie_reservation_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MovieReservationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieReservationServerApplication.class, args);
	}

}
