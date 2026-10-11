package com.hospital.atenciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AtencionesServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtencionesServiceApplication.class, args);
    }
}
