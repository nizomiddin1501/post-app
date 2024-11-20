package uz.developers.postapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PostAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostAppApplication.class, args);

		// for swagger documentation
		// http://localhost:8080/swagger-ui/index.html

        //		Post CRUD application yaratish:
        //		- user registration
        //				- user authentication
        //				- post create
        //				- post edit
        //				- post view
        //				- post list
        //				- post delete
        //				- faqat post muallifi uz postini delete va update qila oladi
        //				- faqat comment muallifi uz commentini delete va update qila oladi.

		// my portfolio
		// https://nizomiddin-portfolio.netlify.app/

	}
}
