package com.ey.filterfiles.services;

import java.util.List;
import java.util.Map;

public interface FilterFilesServices {

	@SuppressWarnings("rawtypes")
	Map<String,List> validateFiles(String pathName);
}
