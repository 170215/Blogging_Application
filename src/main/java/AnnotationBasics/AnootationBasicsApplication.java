package AnnotationBasics;

import AnnotationBasics.Config.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AnootationBasicsApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext=SpringApplication.run(AnootationBasicsApplication.class, args);
		ProductService productService=applicationContext.getBean(ProductService.class);
		productService.createProduct();

	}
   @Bean
	public ModelMapper modelMapper(){
		return  new ModelMapper();
   }
}
