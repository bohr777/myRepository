package com.oracle.boot.orderMeal.service;

import java.util.List;

import com.oracle.boot.orderMeal.dataObject.Repertory;

public interface RepertoryService {

	List<Repertory> findByPid(Integer pid);
	
	void update(Repertory repertory);
	
	Repertory findById(Integer rid);
	
	Repertory findByPidAndSize(Integer pid,String size);
}
