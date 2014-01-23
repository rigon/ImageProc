package com.imageproc;

import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class GameWindow extends Dialog {
	private static final long serialVersionUID = 1L;
	
	private MatImage image;
	private Mat image_mat;
	private VideoCapture capture;
	private Timer timer;
	
	public GameWindow() {
		super(PSI.getEngine().getMainWindow());
		// Captura da imagem
		image_mat = new Mat();
		capture = new VideoCapture(0);
		capture.read(image_mat);
		if (capture.isOpened()) {
			this.image = new MatImage(image_mat);
		}
		
		// Task para actualizar a imagem
		timer = new Timer();
		timer.schedule(new Task(), 0, 100);
		
		// Definições da Janela
		this.setModal(true);
		this.setResizable(false);
		this.setSize(image.getSizeX()+8, image.getSizeY()+31);
		this.setLocationRelativeTo(PSI.getEngine().getMainWindow());
		this.setTitle("Jogo do Sério");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				capture.release();
				dispose();
			}
		});
		this.setVisible(true);
	}
	
	// Desenhar imagem
	public void paint(Graphics g) {
		if(image!=null) g.drawImage(image.getImage(), 4, 27, null);
		super.paint(g);
	}
	
	class Task extends TimerTask {
		public void run() {
			capture.read(image_mat);
			if (capture.isOpened() && image_mat != null && image_mat.rows() > 0 && image_mat.cols() > 0) {
				image = ImageWebcam.detectAll(new MatImage(image_mat), false);
				repaint();
			}
		}
	}
}
