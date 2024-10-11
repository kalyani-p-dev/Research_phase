package com.zensar.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.zensar.dto.StockDto;
import com.zensar.exception.InvalidStockException;
import com.zensar.exception.InvalidTokenException;
import com.zensar.service.StockServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/zenarstockapp")
public class StockController {
 
	@Autowired
	StockServiceImpl stockService;
 
//	@GetMapping(value = "/stock", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<StockDto>> getAllStocks() {
//		List<StockDto> allStocks = stockService.getAllStocks();
//		return new ResponseEntity<>(allStocks, HttpStatus.OK);
// 
//	}
	
	@GetMapping(value = "/stock", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StockDto>> getAllStocks(@RequestHeader(value="Authorization") String token) {
	    try {
	        List<StockDto> allStocks = stockService.getAllStocks(token);
	        return new ResponseEntity<>(allStocks, HttpStatus.OK);
	    } catch (InvalidTokenException e) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
	}

 
//	@GetMapping(value = "/stock/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
//	public ResponseEntity<StockDto> getStockbyId(@PathVariable("id") int stockId,
//			@RequestHeader("token") String authorization) {
//		StockDto stockbyId = stockService.getStockbyId(stockId, authorization);
//		if (Objects.nonNull(stockbyId)) {
//			return new ResponseEntity<>(stockbyId, HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}
	
	@GetMapping(value="/stocks/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StockDto> getStockById(@PathVariable("id") int stockId)
      {
	    StockDto stockDto=stockService.getStockById(stockId);
	    return new ResponseEntity<StockDto>(stockDto,HttpStatus.OK);
     }
 
	@PostMapping(value = "/stock", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<StockDto> createNewStock(@Valid @RequestBody StockDto stockDto) {
		StockDto newStock = stockService.createNewStock(stockDto);
		return new ResponseEntity<>(newStock, HttpStatus.CREATED);
 
	}
 
	@PutMapping(value = "/stock/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StockDto> updateStocksById(@PathVariable("id") int stockId,
			@RequestBody StockDto newStockDto) {
		    StockDto updateStocksById = stockService.updateStocksById(stockId, newStockDto);
		    if(Objects.nonNull(updateStocksById)) {
				return new ResponseEntity<>(updateStocksById, HttpStatus.OK);
			}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 
	}
 
	@DeleteMapping(value = "/stock/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteStocksById(@PathVariable("id") int stockId) {
		boolean deleteStocksById = stockService.deleteStocksById(stockId);
		if(deleteStocksById) {
		return new ResponseEntity<>(deleteStocksById ,HttpStatus.OK);
		}
		return new ResponseEntity<>(deleteStocksById ,HttpStatus.NOT_FOUND);
	}
	//Local exception handler
	@ExceptionHandler(value = {InvalidStockException.class})
	public ResponseEntity<Object> handleInvalidStock(Exception ex, WebRequest request) throws Exception {
		String bodyOfResponse = ex.toString();
        return new ResponseEntity<Object>(bodyOfResponse, HttpStatus.CONFLICT);
	}
	
	@GetMapping(value="/stock/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StockDto>> getStocksByName(@PathVariable("name") String name) {
		return new ResponseEntity<List<StockDto>>(stockService.findByName(name), HttpStatus.OK);
	}

	@GetMapping(value="/stock/marketname/{marketname}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockDto> getStocksByMarketName(@PathVariable("marketname") String marketname) {
		return stockService.findByMarket(marketname);
	}

	@GetMapping(value="/stock/marketname/{marketname}/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockDto> getStocksByNameAndMarketName(@PathVariable("marketname") String marketname, @PathVariable("name") String name) {
		return stockService.findByNameAndMarket(name, marketname);
	}

	@GetMapping(value="/stock/name/like/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockDto> getStocksByNameLike(@PathVariable("name") String name) {
		return stockService.findByNameLike(name);
	}

	@GetMapping(value="/stock/name/sort/{sortType}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockDto> getStocksOrderByName(@PathVariable("sortType") String sortType) {
		return stockService.findByOrderByName(sortType);
	}

	@GetMapping(value="/stock/page/{startIndex}/{records}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockDto> getStocksByPage(@PathVariable("startIndex") int startIndex, @PathVariable("records") int records) {
		return stockService.findByPage(startIndex, records);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}

	
 

