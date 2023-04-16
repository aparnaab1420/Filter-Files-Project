package com.ey.filterfiles.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ey.filterfiles.exception.PathNotFoundException;
import com.ey.filterfiles.services.FilterFilesServices;

@RestController
public class FilterFilesController {

	@Autowired
	private FilterFilesServices service;

	final Logger LOGGER = LoggerFactory.getLogger(FilterFilesController.class);

	@SuppressWarnings("rawtypes")
	@GetMapping("/getFiles")
	public Map<String, List> getFiles(@RequestParam(name = "path") String pathName) throws PathNotFoundException {

		if (pathName == null || pathName.isEmpty()) {
			throw new PathNotFoundException("Invalid path!");
		}
		return service.validateFiles(pathName);

	}

}
