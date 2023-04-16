package com.ey.filterfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ey.filterfiles.controller.FilterFilesController;
import com.ey.filterfiles.entities.FailedFiles;
import com.ey.filterfiles.entities.PassedFiles;
import com.ey.filterfiles.exception.ErrorResponse;
import com.ey.filterfiles.exception.FolderExceptions;
import com.ey.filterfiles.exception.PathNotFoundException;
import com.ey.filterfiles.servicesImpl.FilterFilesServicesImpl;

@SpringBootTest
class FilterfilesApplicationTests {

	@Autowired
	private FilterFilesServicesImpl service;

	@Autowired
	private FilterFilesController controller;

	@SuppressWarnings("unchecked")
	@Test
	public void testFilteredFiles() throws Exception {

		String folderPath = "C:/test";

		File file2 = mock(File.class);
		when(file2.getName()).thenReturn("file2.txt");
		when(file2.isFile()).thenReturn(true);
		when(file2.length()).thenReturn(1000L);

		File another = mock(File.class);
		when(another.getName()).thenReturn("another.pdf");
		when(another.isFile()).thenReturn(true);
		when(another.length()).thenReturn(91000L);

		File file1 = mock(File.class);
		when(file1.getName()).thenReturn("file1.txt");
		when(file1.isFile()).thenReturn(true);
		when(file1.length()).thenReturn(8000L);

		List<PassedFiles> actualOutput1 = service.validateFiles(folderPath).get("passedFiles");
		List<FailedFiles> actualOutput2 = service.validateFiles(folderPath).get("failedFiles");

		assertEquals("file2.txt", actualOutput1.get(0).getFileName());
		assertEquals("another.pdf", actualOutput2.get(0).getFileName());
		assertEquals("file1.txt", actualOutput2.get(1).getFileName());
		assertEquals("exceed MAX size", actualOutput2.get(1).getReason());

	}

	@Test
	public void testFolderExceptions() throws Exception {

		String invalidFolderPath = "C:/invalid";
		String nullPath = "C:/test/new";

		assertThrows(FolderExceptions.class, () -> service.validateFiles(invalidFolderPath));
		assertThrows(FolderExceptions.class, () -> service.validateFiles(nullPath));

	}

	@Test
	public void testConstructor() {

		PassedFiles passed = new PassedFiles("test1.txt");
		assertEquals("test1.txt", passed.getFileName());

		FailedFiles failed = new FailedFiles("test2.pdf", "not specified file type");
		assertEquals("test2.pdf", failed.getFileName());
		assertEquals("not specified file type", failed.getReason());
	}

	@Test
	public void testErrorResponse() {

		ErrorResponse errorResponse = new ErrorResponse("Invalid folder path");
		assertEquals("Invalid folder path", errorResponse.getErrorMessage());
		errorResponse.setErrorMessage("No such folder!");
		assertEquals("No such folder!", errorResponse.getErrorMessage());
	}

	@Test
	public void testGetFiles() throws PathNotFoundException {
		String path = "";
		assertThrows(PathNotFoundException.class, () -> controller.getFiles(path));

		String nullPath = null;
		assertThrows(PathNotFoundException.class, () -> controller.getFiles(nullPath));

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void getValidFiles() throws PathNotFoundException {

		String validPath = "C:/notes";

		Map<String, List> actualFiles = controller.getFiles(validPath);
		assertTrue(!actualFiles.isEmpty());

	}

}
