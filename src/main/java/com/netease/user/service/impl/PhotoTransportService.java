package com.netease.user.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.netease.user.service.FileService;
import com.netease.utils.IOUtils;

@Service
public class PhotoTransportService implements FileService {

	@Override
	public void fildUpload(String name, byte[] photo) throws IOException {
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(name));
			bos.write(photo);
			bos.flush();
			compressImg(name, getSmallPicturePath(name), 50, 50);
		} finally {
			if (bos != null) {
				bos.close();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> fileDownload(String fileName) throws IOException {
		File file = new File(fileName);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", file.getName());
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

	private void compressImg(String path, String dest, int width, int height) throws IOException {
		OutputStream out = null;
		try {
			Image img = ImageIO.read(new File(path));
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0,
					null);
			out = new FileOutputStream(new File(dest));
			ImageIO.write(image, "jpg", out);
		} finally {
			IOUtils.closeOutputStream(out);
		}
	}

	private String getSmallPicturePath(String path) {
		int idx = path.lastIndexOf('.');
		return path.substring(0, idx) + "_small" + path.substring(idx);
	}
}
