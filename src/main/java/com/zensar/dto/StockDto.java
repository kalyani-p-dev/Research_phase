

package com.zensar.dto;

import jakarta.validation.constraints.NotEmpty;

public class StockDto {
	private int id;
	@NotEmpty(message = "Name cannot be blank")
	private String name;
	@NotEmpty(message = "Market should be specified")
	private String market;
	private double price;
	private String createdBy; 
	
	public StockDto() {}
	public StockDto(int id,String name, String market, double price) {
		super();
		this.id=id;
		this.name = name;
		this.market = market;
		this.price = price;
	}
	
//	public StockDto(String name, String market, double price,String createdBy) {
//		super();
//		this.name = name;
//		this.market = market;
//		this.price = price;
//		this.createdBy = createdBy;
//	}
//	
 
	public StockDto(int id, String name, String market, double price,String createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.market = market;
		this.price = price;
		this.createdBy = createdBy;
	}
	
 
	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getMarket() {
		return market;
	}
 
	public void setMarket(String market) {
		this.market = market;
	}
 
	public double getPrice() {
		return price;
	}
 
	public void setPrice(double price) {
		this.price = price;
	}
	
 
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public boolean equals(Object object)
	{
		StockDto stockDto=(StockDto)object;
		if(this.id==stockDto.id)
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return "StockDto [id=" + id + ", name=" + name + ", market=" + market + ", price=" + price + ", createdBy="
				+ createdBy + "]";
	}

	
	
	
	
	
 
}