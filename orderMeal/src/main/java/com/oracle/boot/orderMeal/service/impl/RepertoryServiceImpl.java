package com.oracle.boot.orderMeal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.boot.orderMeal.dao.RepertoryRepository;
import com.oracle.boot.orderMeal.dataObject.Repertory;
import com.oracle.boot.orderMeal.service.RepertoryService;
@Service("repertoryService")
public class RepertoryServiceImpl implements RepertoryService {

	@Autowired
	private RepertoryRepository repertoryRepository;
	
	
	public List<Repertory> findByPid(Integer pid) {
		return repertoryRepository.findByPid(pid);
	}

	
	public void update(Repertory repertory) {
		repertoryRepository.save(repertory);
	}

	
	public Repertory findById(Integer rid) {
		return repertoryRepository.findById(rid).get();
	}


	@Override
	public Repertory findByPidAndSize(Integer pid, String size) {
		// TODO Auto-generated method stub
		return repertoryRepository.findByPidAndSize(pid, size);
	}

}
