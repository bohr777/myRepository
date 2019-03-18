package com.oracle.color;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.mapper.ColorMapper;
import com.oracle.pojo.Color;
@Service("colorService")
@Transactional
public class ColorServiceImpl implements ColorService {

	@Autowired
	private ColorMapper colorMapper;
	
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int insert(Color record) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int insertSelective(Color record) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public Color selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int updateByPrimaryKeySelective(Color record) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int updateByPrimaryKey(Color record) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public List<Color> selectAllColor() {
		
		return colorMapper.selectAllColor();
	}

}
