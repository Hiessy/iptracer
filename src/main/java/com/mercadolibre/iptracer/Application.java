package com.mercadolibre.iptracer;

import com.mercadolibre.iptracer.repository.IpTracerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.mercadolibre.iptracer"})
@EnableJpaRepositories(basePackageClasses = IpTracerRepository.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
