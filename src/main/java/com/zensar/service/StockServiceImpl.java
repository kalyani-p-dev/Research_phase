
package com.zensar.service;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.zensar.dto.StockDto;
import com.zensar.entity.StockEntity;
import com.zensar.exception.InvalidStockException;
import com.zensar.exception.InvalidTokenException;
import com.zensar.repository.StockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
 
@Service
public class StockServiceImpl implements StockService {
 
	@Autowired
	StockRepository stockRepository;
	@Autowired
    ModelMapper modelMapper;
	@Autowired
	UserServiceDelegate userServiceDelegate;

//	@Override
//	public List<StockDto> getAllStocks(String token) {
//		if(!userServiceDelegate.isTokenValid(token));{
//		throw new InvalidTokenException(token);
//	}
//        List<StockEntity> stockEntityList = stockRepository.findAll();
//        List<StockDto> stockDtoList = convertEntityListIntoDtoList(stockEntityList);
//
//        return stockDtoList;
//	}
	
	public List<StockDto> getAllStocks(String token) {
		if(!userServiceDelegate.isTokenValid(token))
		{
			throw new InvalidTokenException(token);
		}
		List<StockEntity> stockEntityList=stockRepository.findAll();
		List<StockDto> stockDtoList=convertEntityListIntoDtoList((stockEntityList));		
		return stockDtoList;
	}
	
	private List<StockDto> convertEntityListIntoDtoList(List<StockEntity> stockEntityList) {
		List<StockDto> stockDtoList = new ArrayList<StockDto>();
		
		//ModelMapper customization
		TypeMap<StockEntity, StockDto> typeMap =
				modelMapper.typeMap(StockEntity.class, StockDto.class);
		typeMap.addMappings((mapper)-> {
			mapper.map(source->source.getOwner(), StockDto::setCreatedBy);
		});
		
		for(StockEntity stockEntity: stockEntityList) {
			StockDto stockDto = modelMapper.map(stockEntity, StockDto.class);
			//StockEntity stockEntity = modelMapper.map(stockDto, StockEntity.class);
			stockDtoList.add(stockDto);
		}
		return stockDtoList;
	}
	
//	@Override
//	public StockDto getStockbyId(int stockId, String authorization) {
//	    if (authorization.equals("ABCD")) {
//	        Optional<StockEntity> opStockEntity = stockRepository.findById(stockId);
//	        if (opStockEntity.isPresent()) {
//	            StockEntity stockEntity = opStockEntity.get();
//	            return modelMapper.map(stockEntity, StockDto.class);
//	        }
//	    }
//	    throw new InvalidStockException("" + stockId);
//	}
	
	@Override
	public StockDto getStockById(int stockId) {
		Optional<StockEntity> opStockEntity = stockRepository.findById(stockId);
		if(opStockEntity.isPresent()) { //stockId is correct
			StockEntity stockEntity = opStockEntity.get();
			StockDto stockDto = modelMapper.map(stockEntity, StockDto.class);
			return stockDto;
		}
		return new StockDto();
	}


	@Override
	public StockDto createNewStock(StockDto stockDto) {
		StockEntity stockEntity = modelMapper.map(stockDto, StockEntity.class);
		stockEntity = stockRepository.save(stockEntity); //Saves into table
		stockDto = modelMapper.map(stockEntity, StockDto.class);
		return stockDto;
	}
 
	
	 @Override
	    public StockDto updateStocksById(int stockId, StockDto newStockDto) {
	        Optional<StockEntity> opStockEntity = stockRepository.findById(stockId);

	        if (opStockEntity.isPresent()) {
	            StockEntity stockEntity = opStockEntity.get();
	            int originalId = stockEntity.getId();
	            modelMapper.map(newStockDto, stockEntity);
	            stockEntity.setId(originalId);
	            stockRepository.save(stockEntity);
	            return modelMapper.map(stockEntity, StockDto.class);
	        } else {
	            throw new InvalidStockException("" + stockId);
	        }
	 }
	        

	@Override
	public boolean deleteStocksById(int stockId) {
		if(stockRepository.findById(stockId).isPresent()) {
			stockRepository.deleteById(stockId);
			return true;
		}
		throw new InvalidStockException(""+stockId);
	}
	@Override
	public List<StockDto> findByMarket(String market) {
		
	        List<StockEntity> entities = stockRepository.findByMarket(market);
	        List<StockDto> dtos = new ArrayList<>();

	        for (StockEntity entity : entities) {
	            StockDto dto = modelMapper.map(entity, StockDto.class);
	            dtos.add(dto);
	        }

	        return dtos;
	    }
	
	@Override
	public List<StockDto> findByName(String name) {
		
	        List<StockEntity> entities = stockRepository.findByName(name);
	        List<StockDto> dtos = new ArrayList<>();

	        for (StockEntity entity : entities) {
	            StockDto dto = modelMapper.map(entity, StockDto.class);
	            dtos.add(dto);
	        }

	        return dtos;
	    }
	
	@Override
	public List<StockDto> findByNameAndMarket(String name, String market) {
		
	        List<StockEntity> entities = stockRepository.findByNameAndMarket(name, market);
	        List<StockDto> dtos = new ArrayList<>();

	        for (StockEntity entity : entities) {
	            StockDto dto = modelMapper.map(entity, StockDto.class);
	            dtos.add(dto);
	        }

	        return dtos;
	    }
	
	@Override
	public List<StockDto> findByNameLike(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<StockDto> findByOrderByName(String sortType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<StockDto> findByPage(int startIndex, int records) {
	    Pageable pageable = PageRequest.of(startIndex, records);
	    Page<StockEntity> page = stockRepository.findAll(pageable);
	    List<StockEntity> stockEntityList = page.getContent();
	    return convertEntityListIntoDtoList(stockEntityList);
	}

	@Override
	public List<StockDto> getAllStocks() {
		// TODO Auto-generated method stub
		return null;
	}
}
	
 

