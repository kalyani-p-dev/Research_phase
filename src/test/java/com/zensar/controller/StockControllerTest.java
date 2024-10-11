package com.zensar.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zensar.controller.StockController;
import com.zensar.dto.StockDto;
import com.zensar.exception.InvalidStockException;
//import com.zensar.exception.InvalidStockIdException;


@WebMvcTest(StockController.class)
public class StockControllerTest {
	@MockBean
	com.zensar.service.StockServiceImpl stockService;
	@Autowired
	MockMvc mockMvc; //used for calling REST API
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void testGetStockById() throws Exception {
		
		StockDto stockDto = new StockDto(1, "Zensar", "BSE", 1200);
		when(stockService.getStockById(1)).thenReturn(stockDto);
	
		MvcResult mvcResult = mockMvc.perform(get("http://localhost:8090/zenarstockapp/stocks/1"))
				.andExpect(status().isOk())
				.andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		assertTrue(response.contains("Zensar"));
	}
	@Test
	public void testGetStockByIdFailure() throws Exception {
		
		when(stockService.getStockById(20)).thenThrow(InvalidStockException.class);
	
		MvcResult mvcResult = mockMvc.perform(get("http://localhost:8090/zenarstockapp/stocks/20"))
				.andExpect(status().isConflict())
				.andReturn();
	}
	
	@Test
	public void testCreateNewStock() throws Exception {
		StockDto stockDto = new StockDto(5, "IT", "BSE", 3000);
		when(stockService.createNewStock(stockDto)).thenReturn(stockDto);
		
		MvcResult mvcResult = mockMvc.perform(
					post("http://localhost:8090/zenarstockapp/stock").contentType("application/json")
								.content(objectMapper.writeValueAsString(stockDto))
				)
				.andExpect(status().isCreated())
				.andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		assertTrue(response.contains("IT"));
	}

	@Test
	public void testCreateNewStockBlankName() throws Exception {
		StockDto stockDto = new StockDto(5, "", "BSE", 3000);
		when(stockService.createNewStock(stockDto)).thenReturn(stockDto);
		
		MvcResult mvcResult = mockMvc.perform(
				post("http://localhost:9092/zenarstockapp/stock").contentType("application/json")
							.content(objectMapper.writeValueAsString(stockDto))
			)
			.andExpect(status().isBadRequest())
			.andReturn();
	}
	
	
	@Test
	public void testDeleteStockById() throws Exception {
		
		
		StockDto stockDto = new StockDto(2, "IT", "NSE", 32000,"Tom");
		when(stockService.deleteStocksById(2)).thenReturn(true);
	
		MvcResult mvcResult = mockMvc.perform(delete("http://localhost:8090/zenarstockapp/stock/2"))
				.andExpect(status().isOk())
				.andReturn();
		assertTrue(true);
	}
	
	@Test
	public void testDeleteStockByIdFailure() throws Exception {
		
		when(stockService.deleteStocksById(25)).thenThrow(InvalidStockException.class);
		
		MvcResult mvcResult = mockMvc.perform(delete("http://localhost:8090/zenarstockapp/stock/25"))
				.andExpect(status().isConflict())
				.andReturn();
		
	}
	
	@Test
	public void testUpdateStockById() throws Exception {
	    StockDto stockDto = new StockDto(2, "IT", "NSE", 32000, "Tom");
	    when(stockService.updateStocksById(2, stockDto)).thenReturn(stockDto);

	    MvcResult mvcResult = mockMvc.perform(put("http://localhost:8090/zenarstockapp/stock/2")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(stockDto)))
	            .andExpect(status().isOk())
	            .andReturn();

	    assertTrue(true);
	}

	@Test
	public void testUpdateStockByIdFailure() throws Exception {
	    StockDto stockDto = new StockDto(25, "Finance", "BSE", 45000, "Jerry");
	    when(stockService.updateStocksById(25, stockDto)).thenThrow(InvalidStockException.class);

	    MvcResult mvcResult = mockMvc.perform(put("http://localhost:8090/zenarstockapp/stock/25")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(stockDto)))
	            .andExpect(status().isConflict())
	            .andReturn();
	}
	
	
	
}
