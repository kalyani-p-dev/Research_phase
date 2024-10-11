package com.zensar.service;

import java.util.List;

import com.zensar.dto.StockDto;

public interface StockService {

	List<StockDto> getAllStocks();

	//StockDto getStockbyId(int stockId, String authorization);
	public StockDto getStockById(int stockId);

	StockDto createNewStock(StockDto stockDto);

	StockDto updateStocksById(int stockId, StockDto newStockDto);

	boolean deleteStocksById(int stockId);
	
	List<StockDto> findByMarket(String market);
	List<StockDto> findByName(String name);
	List<StockDto> findByNameAndMarket(String name, String market);
	List<StockDto> findByNameLike(String name);	
	List<StockDto> findByOrderByName(String sortType);
	List<StockDto> findByPage(int startIndex, int records);
	
	




}
