package com.zensar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Entity class follow JPA specification

@Entity
@Table(name = "STOCKS")
public class StockEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	@Column(name = "market_name", nullable = false, length = 30)
	private String market;	
	@Column(name = "price", nullable = false)
	private double price;
	private String owner ;
	
	public StockEntity() {}
	public StockEntity(int id, String name, String market, double price,String owner) {
		super();
		this.id = id;
		this.name = name;
		this.market = market;
		this.price = price;
		this.owner = owner;
		
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
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	@Override
	public String toString() {
		return "StockEntity [id=" + id + ", name=" + name + ", market=" + market + ", price=" + price + ", owner="
				+ owner + "]";
	}
	
	
}

