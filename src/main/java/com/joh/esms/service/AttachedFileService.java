package com.joh.esms.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.joh.esms.exception.AttachmentNotFoundException;
import com.joh.esms.model.AttachedFile;

public interface AttachedFileService {

	AttachedFile save(MultipartFile multipartFile) throws IOException;

	void delete(AttachedFile attachedFile) throws IOException;

	byte[] getAttachentFile(int id) throws AttachmentNotFoundException;

	AttachedFile findOne(int id);

	byte[] getAttachedFileSmall(int id) throws AttachmentNotFoundException;

}
