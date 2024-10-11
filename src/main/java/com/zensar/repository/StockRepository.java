//package com.zensar.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.zensar.entity.StockEntity;
//
//public interface StockRepository extends JpaRepository<StockEntity, Integer> {
	
	
package com.zensar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zensar.entity.StockEntity;

//Developer
public interface StockRepository extends JpaRepository<StockEntity, Integer>{
	List<StockEntity> findByName(String name);
	List<StockEntity> findByMarket(String market);
	List<StockEntity> findByNameAndMarket(String name, String market);
	
	List<StockEntity> findByNameContaining(String namePattern); //namePattern='lia'
	List<StockEntity> findByNameIsContaining(String namePattern);
	List<StockEntity> findByNameContains(String namePattern);
	
	@Query(value = "SELECT se FROM StockEntity se WHERE se.name LIKE %:namePattern%") //JPQL
	List<StockEntity> getStocksByTheirNameLike(String namePattern);
	
	List<StockEntity> findByOrderByNameAsc();
	List<StockEntity> findByOrderByNameDesc();
}



