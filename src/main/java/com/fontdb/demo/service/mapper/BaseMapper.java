package com.fontdb.demo.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.fontdb.demo.web.dto.JqGridDto;

public abstract class BaseMapper<T, T1> {
	public abstract T1 modelToDto(T model);

	public List<T1> modelsToDtos(List<T> models) {
		return models.stream().map(this::modelToDto).collect(Collectors.toList());
	}
	
	public JqGridDto<T1> pageToJqGrid(Page<T> page) {
		JqGridDto<T1>  result = new JqGridDto<>();
		result.setRecords(page.getTotalElements());
		result.setRows(modelsToDtos(page.getContent()));
		result.setTotal(page.getTotalPages());
		return result;
		
	}
}
