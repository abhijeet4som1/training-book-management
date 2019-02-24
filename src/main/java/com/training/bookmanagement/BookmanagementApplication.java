package com.training.bookmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackageClasses = {BookmanagementApplication.class, Jsr310JpaConverters.class })
@EnableJpaRepositories(basePackageClasses = { BookmanagementApplication.class }, considerNestedRepositories = true)
@ComponentScan(basePackageClasses = { BookmanagementApplication.class })
@EnableTransactionManagement
public class BookmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmanagementApplication.class, args);
	}

}
