package com.ey.filterfiles.servicesImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ey.filterfiles.entities.FailedFiles;
import com.ey.filterfiles.entities.PassedFiles;
import com.ey.filterfiles.exception.FolderExceptions;
import com.ey.filterfiles.services.FilterFilesServices;

@Service
public class FilterFilesServicesImpl implements FilterFilesServices {

	@Value("${fileType}")
	private String type;

	@Value("${fileSize}")
	private Long sizemb;

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, List> validateFiles(String pathName) {

		final Logger LOGGER = LoggerFactory.getLogger(FilterFilesServicesImpl.class);

		File files = new File(pathName);

		if (!files.exists()) {
			throw new FolderExceptions("Invalid folder!");
		}

		File[] fileNames = files.listFiles();

		if (files != null && files.length() == 0) {
			throw new FolderExceptions("File not found!");
		}
		List<PassedFiles> passed = new ArrayList<>();
		List<FailedFiles> failed = new ArrayList<>();

		Map<String, List> output = new HashMap<>();

		for (File file : fileNames) {

			long fileSize = file.length();
			long fileKb = fileSize / (1024);
			long fileMb = fileKb / (1024);
			fileMb++;

			LOGGER.info("Checking file:" + file.getName());

			if ((sizemb > fileMb) && (file.getName().endsWith(type))) {

				PassedFiles passedFile = new PassedFiles();
				passedFile.setFileName(file.getName());
				passed.add(passedFile);
				LOGGER.info(file.getName() + " passed!");

			} else {
				FailedFiles failedFile = new FailedFiles();
				failedFile.setFileName(file.getName());
				if (!file.getName().endsWith(type)) {
					failedFile.setReason("not specified file type");
				} else {
					failedFile.setReason("exceed MAX size");

				}
				failed.add(failedFile);
				LOGGER.info(file.getName() + " failed!");
			}

		}

		output.put("passedFiles", passed);
		output.put("failedFiles", failed);

		return output;

	}

}
