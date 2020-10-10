
package com.joh.esms.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.joh.esms.dao.AttachedFileDAO;
import com.joh.esms.exception.AttachmentNotFoundException;
import com.joh.esms.model.AttachedFile;

@Service
public class AttachedFileServiceImpl implements AttachedFileService {

    private static final Logger logger = Logger.getLogger(AttachedFileServiceImpl.class);

    @Autowired
    private ResourceLoader resourceLoader;


    private String folderName = "attachedFiles";

    @Autowired
    private AttachedFileDAO attachedFileDAO;

    @Transactional
    @Override
    public AttachedFile save(MultipartFile multipartFile) throws IOException {
        String url = resourceLoader.getResource("resources").getFile().getPath();

        File folder = new File(url + "/" + folderName);

        logger.info("folder=" + folder);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        AttachedFile attachedFile = new AttachedFile();

        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toUpperCase();
        logger.info("extension=" + extension);
        attachedFile.setExtension(extension);

        attachedFile = attachedFileDAO.save(attachedFile);

        byte[] bytes = multipartFile.getBytes();

        Path path = Paths.get(folder + "/" + attachedFile.getId() + "." + extension);
        Files.write(path, bytes);

        return attachedFile;
    }

    @Override
    public byte[] getAttachentFile(int id) throws AttachmentNotFoundException {
        try {
            String url = resourceLoader.getResource("resources").getFile().getPath();

            AttachedFile attachedFile = attachedFileDAO.findOne(id);

            logger.debug("attachedFile=" + attachedFile);

            File file = new File(url + "/" + folderName + "/" + attachedFile.getId() + "." + attachedFile.getExtension());

            logger.debug("file=" + file);

            return Files.readAllBytes(file.toPath());

        } catch (Exception e) {
            logger.info("getAttachentFile exception occurred");
            throw new AttachmentNotFoundException("id" + id);
        }

    }

    @Override
    public byte[] getAttachedFileSmall(int id) throws AttachmentNotFoundException {
        try {

            String url = resourceLoader.getResource("resources").getFile().getPath();


            AttachedFile attachedFile = attachedFileDAO.findOne(id);

            logger.debug("attachedFile=" + attachedFile);

            File file = new File(
                    url + "/" + folderName + "/" + attachedFile.getId() + "." + attachedFile.getExtension());

            logger.debug("file=" + file);

            BufferedImage img = ImageIO.read(file);

            double scale = determineImageScale(img.getWidth(), img.getHeight(), 100, 150);

            Image tmp = img.getScaledInstance((int) (img.getWidth() * scale), (int) (img.getHeight() * scale),
                    Image.SCALE_FAST);
            BufferedImage dimg = new BufferedImage((int) (img.getWidth() * scale), (int) (img.getHeight() * scale),
                    img.getType());

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(dimg, attachedFile.getExtension(), bos);
            return bos.toByteArray();
        } catch (Exception e) {
            logger.info("getAttachedFileSmall exception occurred");
            throw new AttachmentNotFoundException("id" + id);
        }

    }

    @Override
    public AttachedFile findOne(int id) {
        return attachedFileDAO.findOne(id);
    }

    @Transactional
    @Override
    public void delete(AttachedFile attachedFile) throws IOException {
        logger.info("delete->fired");
        logger.info("attachedFile=" + attachedFile);
        String url = resourceLoader.getResource("resources").getFile().getPath();

        File folder = new File(url + "/attachedFiles");

        logger.info("folder=" + folder);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder + "/" + attachedFile.getId() + "." + attachedFile.getExtension());
        if (file.exists()) {
            file.delete();
        }
        attachedFileDAO.delete(attachedFile.getId());

    }

    // Helper

    private double determineImageScale(int sW, int sH, int tW, int tH) {
        double scalex = (double) tW / sW;
        double scaley = (double) tH / sH;
        return Math.min(scalex, scaley);
    }

}
