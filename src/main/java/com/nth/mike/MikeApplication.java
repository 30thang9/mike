package com.nth.mike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.nth.mike.utils.ConvertUTF8Utils;

@SpringBootApplication
@EnableConfigurationProperties
public class MikeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MikeApplication.class, args);
		System.out.println("http://localhost:8080/mike");
		String vietnameseText = "Xin chào các bạn!"; // Text with accented characters
		String simplifiedText = ConvertUTF8Utils.convert(vietnameseText);
		System.out.println(simplifiedText);
	}

}
