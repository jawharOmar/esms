package com.joh.esms.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joh.esms.exception.AttachmentNotFoundException;
import com.joh.esms.model.AttachedFile;
import com.joh.esms.service.AttachedFileService;

@Controller
@RequestMapping(path = "/attachedFiles")
public class AttachedFileControlller {

	private static final Logger logger = Logger.getLogger(AttachedFileControlller.class);

	@Autowired
	private AttachedFileService attachedFileService;

	@GetMapping(path = "/download/{id}")
	public void downloadAttachedFile(@PathVariable int id, HttpServletResponse response)
			throws IOException, AttachmentNotFoundException {

		response.setContentType("application/octet-stream");

		AttachedFile attachedFile = attachedFileService.findOne(id);

		String fileName = attachedFile.getId() + "." + attachedFile.getExtension();
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		ServletOutputStream out = response.getOutputStream();

		byte[] outputByte = attachedFileService.getAttachentFile(id);

		out.write(outputByte, 0, outputByte.length);
	}

	@GetMapping(path = "/{size}/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable int size, @PathVariable int id) {
		logger.info("getImage->fired");
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		try {
			byte[] media = null;
			if (size == 0) {
				media = attachedFileService.getAttachentFile(id);
			} else {
				media = attachedFileService.getAttachedFileSmall(id);
			}

			AttachedFile attachedFile = attachedFileService.findOne(id);

			switch (attachedFile.getExtension()) {
			case "JPG":
			case "JPEG":
				headers.setContentType(MediaType.IMAGE_JPEG);
				break;
			case "PNG":
				headers.setContentType(MediaType.IMAGE_PNG);
				break;

			case "GIF":
				headers.setContentType(MediaType.IMAGE_GIF);
				break;
			}

			ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
			return responseEntity;

		} catch (AttachmentNotFoundException e) {
			logger.info("exception occurred->AttachmentNotFoundException");

			ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
			return responseEntity;
		}

	}
}
