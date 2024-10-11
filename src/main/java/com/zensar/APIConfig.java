package com.zensar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "Stock Management REST API Documentation",
				description = "Stock Management application",
				termsOfService="Stock Management Application Works Based on its Stocks "
						+ "and It manages All the Stocks by adding new stocks and updates if needed aswell deletes if not required"
						+"Stock Management uses stock id , name of the stocks, market name aswell as Price of stock",
				version = "1.1",
				license = @License(
						name = "LGPL",
						url = "http://lgpl.com"
				),
				contact = @Contact(
						name = "Kalyani",
						email = "kalyani.pradhan@zensar.com"
				)
		)		
)
public class APIConfig {
	

}
