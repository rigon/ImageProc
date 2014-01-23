package com.imageproc;

import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class ImageWebcam {
	/**
	 * Reference:
	 * http://cell0907.blogspot.pt/2013/06/creating-windows-and-capturing
	 * -webcam.html
	 */
	public static MatImage capture() {
		Mat image_mat = new Mat();
		MatImage matImage = null;

		VideoCapture capture = new VideoCapture(0);

		if (capture.isOpened()) {
			try {
				capture.read(image_mat);
				Thread.sleep(700);
				capture.read(image_mat);
				capture.read(image_mat);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (!image_mat.empty())
				matImage = new MatImage(image_mat);
			else
				JOptionPane.showMessageDialog(null, "--(!) No captured frame -- Break!");
			
			capture.release();
		} else
			JOptionPane.showMessageDialog(null, "Erro na câmara.");
		
		return matImage;
	}
	
	public static MatImage detectFaces(MatImage matImage, boolean crop) {
		Mat image = matImage.getOpenCVMatrix();
		if(image==null) return null;
		
		//String resource = FaceDetector.class.getResource("C:\\opencv\\data\\haarcascades\\haarcascade_frontalface_alt.xml").getPath();
		String resource = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml";
		CascadeClassifier faceDetector = new CascadeClassifier(resource);

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		
		Rect[] faces = faceDetections.toArray();
		System.out.println(String.format("Detected %s faces", faces.length));

		for (Rect rect : faces) {
			Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
				new Scalar(0, 0, 255));
		}
		
		return new MatImage(image);
//		String filename = "ouput.png";
//		System.out.println(String.format("Writing %s", filename));
//		// Highgui.imwrite(filename, image);
//
//		MatOfByte bytemat = new MatOfByte();
//
//		Highgui.imencode(".jpg", image, bytemat);
//
//		byte[] bytes = bytemat.toArray();
//
//		InputStream in = new ByteArrayInputStream(bytes);
//		return in;
	}
	
	public static MatImage detectEyes(MatImage matImage) {
		Mat image = matImage.getOpenCVMatrix();
		if(image==null) return null;
		
		//String resource = FaceDetector.class.getResource("C:\\opencv\\data\\haarcascades\\haarcascade_frontalface_alt.xml").getPath();
		String resource = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_eye.xml";
		CascadeClassifier faceDetector = new CascadeClassifier(resource);

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		
		Rect[] faces = faceDetections.toArray();
		System.out.println(String.format("Detected %s faces", faces.length));
		
		for (Rect rect : faces) {
			Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
				new Scalar(0, 255, 0));
		}
		
		return new MatImage(image);
	}
	
	public static MatImage detectMouth(MatImage matImage) {
		Mat image = matImage.getOpenCVMatrix();
		if(image==null) return null;
		
		//String resource = FaceDetector.class.getResource("C:\\opencv\\data\\haarcascades\\haarcascade_frontalface_alt.xml").getPath();
		String resource = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_mcs_mouth.xml";
		CascadeClassifier faceDetector = new CascadeClassifier(resource);

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		
		Rect[] faces = faceDetections.toArray();
		System.out.println(String.format("Detected %s faces", faces.length));
		
		for (Rect rect : faces) {
			Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
				new Scalar(255, 0, 0));
		}
		
		return new MatImage(image);
	}

	public static MatImage detectAll(MatImage matImage, boolean showImage) {
		Mat image = matImage.getOpenCVMatrix();
		return detectAll(image,showImage);
	}
	
	public static MatImage detectAll(Mat image, boolean showImage) {
		if(image==null) {
			JOptionPane.showMessageDialog(null,"Apenas válido para imagens capturadas a partir da câmara.");
			return null;
		}
		
		String resourceFace = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml";
		String resourceEye = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_eye.xml";
		String resourceMouth = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_mcs_mouth.xml";
		
		Rect posFace;
		Rect posEye1;
		Rect posEye2;
		Rect posMouth;
		
		// Detecção da Cara
		CascadeClassifier faceDetector = new CascadeClassifier(resourceFace);

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		
		Rect[] faces = faceDetections.toArray();

		if(faces.length != 1) {
			if(showImage) JOptionPane.showMessageDialog(null, "Erro na detecção da cara ("+faces.length+" caras).");
			return new MatImage(image);
		}
		posFace = faces[0];
		Mat face = image.submat(posFace);
		
		// Detecção dos Olhos
		CascadeClassifier eyeDetector = new CascadeClassifier(resourceEye);
		
		MatOfRect eyeDetections = new MatOfRect();
		eyeDetector.detectMultiScale(face, eyeDetections);
		
		Rect[] eyes = eyeDetections.toArray();
		
		if(eyes.length < 2) {
			if(showImage) JOptionPane.showMessageDialog(null, "Erro na detecção dos olhos ("+eyes.length+" olhos).");
			return new MatImage(image);
		}

		posEye1 = eyes[0];
		posEye2 = eyes[0];
		for(Rect rect : eyes) {
			if(rect.area() > posEye1.area())
				posEye1 = rect;
			else if(rect.area() > posEye2.area())
				posEye2 = rect;
		}
//		for (Rect rect : eyes) {
//			if(rect.y < image.rows()/2)
//				Core.rectangle(face, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
//					new Scalar(0, 255, 0));
//		}
		
		// Detecção da Boca
		CascadeClassifier mouthDetector = new CascadeClassifier(resourceMouth);

		MatOfRect mouthDetections = new MatOfRect();
		mouthDetector.detectMultiScale(face, mouthDetections);
		
		Rect[] mouths = mouthDetections.toArray();

		if(mouths.length <= 0) {
			if(showImage) JOptionPane.showMessageDialog(null, "Erro na detecção de bocas ("+eyes.length+" bocas).");
			return new MatImage(image);
		}
		
		Rect lower = mouths[0];
		for (Rect rect : mouths) {
			if(rect.y > lower.y)
				lower = rect;
		}
		posMouth = mouths[0];
		
		// Detecção de sorriso
		MatImage m = new MatImage(face.submat(posMouth));
		IntensityChannel intensity = new IntensityChannel(m,false);
		m = intensity.getNewImage();
//		CrChannel cr = new CrChannel(m, false);
//		ContrastStretching_5_95 cs = new ContrastStretching_5_95(cr.getNewImage(), false);
		SobelFilter sf = new SobelFilter(m);
		
		Core.rectangle(image, new Point(posFace.x+posEye1.x, posFace.y+posEye1.y), new Point(posFace.x+posEye1.x + posEye1.width, posFace.y+posEye1.y + posEye1.height),
				new Scalar(0, 255, 0));
		Core.rectangle(image, new Point(posFace.x+posEye2.x, posFace.y+posEye2.y), new Point(posFace.x+posEye2.x + posEye2.width, posFace.y+posEye2.y + posEye2.height),
				new Scalar(0, 255, 0));
		Core.rectangle(image, new Point(posFace.x+posMouth.x, posFace.y+posMouth.y), new Point(posFace.x+posMouth.x + posMouth.width, posFace.y+posMouth.y + posMouth.height),
				new Scalar(0, 0, 255));
		
		if(showImage) new ImagePanel(new MatImage(face), "("+eyes.length+" eyes, "+mouths.length+" mouths)");
		if(showImage) new ImagePanel(sf.applyMask(FilterBorder.BLACK));
		
		return new MatImage(image);
	}
}
