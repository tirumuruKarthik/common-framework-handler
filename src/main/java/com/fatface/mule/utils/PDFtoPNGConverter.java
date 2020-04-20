package com.fatface.mule.utils;
/**
 * 
 */


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.beans.factory.annotation.Value;

/**
 * This is used to convert given pdf format to png format.
 */
public class PDFtoPNGConverter {
	//constructor
	public PDFtoPNGConverter() {}
	
	@Value("${shipment.labelImageFormat}")
	String imageFormat = "PNG";
	public String convertToPNG(String encodedPDF)  throws IOException{
		
			Base64 base64 = new Base64();
			PDDocument document = PDDocument.load(base64.decode(encodedPDF.getBytes()));
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 150, ImageType.RGB);
			ImageIOUtil.writeImage(bim, imageFormat, bos);
			// Encode back the PNG file
			String encodedPNG = new String(base64.encode(bos.toByteArray()));
			document.close();
			return encodedPNG;
	}
}
