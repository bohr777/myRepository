package com.oracle.boot.orderMeal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.boot.orderMeal.dataObject.Repertory;

@Repository("repertoryRepository")
public interface RepertoryRepository  extends JpaRepository<Repertory, Integer>{

	List<Repertory> findByPid(Integer pid);
	Repertory findByPidAndSize(Integer pid,String size);
}
