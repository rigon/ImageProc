package com.imageproc;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;


interface HistogramType {
	public int calcPoint(int i);
	public Color scaleColor(int i);
	public String getName();
}

class HistogramHue implements HistogramType {
	public int calcPoint(int i) {
		int tonalidade;
		double theta;
		double R = (double)PSI.getRed(i)/(double)255, G = (double)PSI.getGreen(i)/(double)255, B = (double)PSI.getBlue(i)/(double)255;
		theta = Math.acos(
				(0.5 * ((R - G) + (R - B))) /
				Math.sqrt(Math.pow(R - G,2) + (R - B) * (G - B))
				);
		
		tonalidade = (int)(255.0f / (2.0f * Math.PI) * ((B <= G) ? theta : 2.0f * Math.PI - theta));
		
		return tonalidade;
	}
	public Color scaleColor(int i) {
		return new Color(i, i, i);
	}
	public String getName() {
		return "HSI - Tonalidade";
	}
}
class HistogramSaturation implements HistogramType {
	public int calcPoint(int i) {
		int red = PSI.getRed(i);
		int green = PSI.getGreen(i);
		int blue = PSI.getBlue(i);
		
		int saturacao = (int)(255 - ((double)765/(double)(red + green + blue)) *
				(double)Math.min(red, Math.min(green, blue)));
		
		return saturacao;
	}
	public Color scaleColor(int i) {
		return new Color(i, i, i);
	}
	public String getName() {
		return "HSI - Saturação";
	}
}
class HistogramIntensity implements HistogramType {
	public int calcPoint(int i) {
		return (
				PSI.getRed(i) +
				PSI.getGreen(i) +
				PSI.getBlue(i)
			) / 3;
	}
	public Color scaleColor(int i) {
		return new Color(i, i, i);
	}
	public String getName() {
		return "HSI - Intensidade";
	}
}

class HistogramRed implements HistogramType {
	public int calcPoint(int i) {
		return PSI.getRed(i);
	}
	public Color scaleColor(int i) {
		return new Color(i, 0, 0);
	}
	public String getName() {
		return "RGB - Vermelho";
	}
}
class HistogramGreen implements HistogramType {
	public int calcPoint(int i) {
		return PSI.getGreen(i);
	}
	public Color scaleColor(int i) {
		return new Color(0, i, 0);
	}
	public String getName() {
		return "RGB - Verde";
	}
}
class HistogramBlue implements HistogramType {
	public int calcPoint(int i) {
		return PSI.getBlue(i);
	}
	public Color scaleColor(int i) {
		return new Color(0, 0, i);
	}
	public String getName() {
		return "RGB - Azul";
	}
}


class HistogramY implements HistogramType {
	public int calcPoint(int i) {
		return 16 + (int)(0.257 * PSI.getRed(i) + 0.504 * PSI.getGreen(i) + 0.098 * PSI.getBlue(i));
	}
	public Color scaleColor(int i) {
		return new Color(i, i, i);
	}
	public String getName() {
		return "YCrCb - Y";
	}
}
class HistogramCb implements HistogramType {
	public int calcPoint(int i) {
		return 128 + (int)(-0.148 * PSI.getRed(i) - 0.291 * PSI.getGreen(i) + 0.439 * PSI.getBlue(i));
	}
	public Color scaleColor(int i) {
		return new Color(i, i, i);
	}
	public String getName() {
		return "YCrCb - Cb";
	}
}
class HistogramCr implements HistogramType {
	public int calcPoint(int i) {
		return 128 + (int)(0.439 * PSI.getRed(i) - 0.368 * PSI.getGreen(i) - 0.071 * PSI.getBlue(i));
	}
	public Color scaleColor(int i) {
		return new Color(i, i, i);
	}
	public String getName() {
		return "YCrCb - Cr";
	}
}



public class Histogram  extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private static int pos_x=0;
	
	private int[] matrix;
	private int[] hist = new int[256];
	private int max_hist=0;
	private HistogramType histogramType;
	
	public Histogram(MatImage image, HistogramType histogramType) {
		this.matrix = image.getMatrix();
		this.histogramType = histogramType;
		
		for(int i=0; i<matrix.length; i++) {
			int intensity = histogramType.calcPoint(this.matrix[i]);
			
			hist[intensity]++;
			
			if(hist[intensity]>max_hist)
				max_hist=hist[intensity];
		}
		
		setContentPane(new DrawPane());
	}
	
	public int[] getHistogram() {
		return this.hist;
	}
	
	public void showWindow() {
		this.setTitle(this.histogramType.getName());
		this.setLocation(pos_x%(350*5)+30*(int)(pos_x/(350*5)), 550+30*(int)(pos_x/(350*5)));
		this.setSize(356, 376);
		this.setClosable(true);
		this.setVisible(true);
		pos_x+=355;
		PSI.getEngine().addWindow(this);
	}
	
	// create a component that you can actually draw on.
	class DrawPane extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			for (int i = 0; i < hist.length; i++) {
				g.drawLine(i + 50, 300, i + 50, (int) (300 - (((double) hist[i] / (double) max_hist) * (double) 256)));
			}

			for (int i = 0; i < hist.length; i++) {
				g.setColor(histogramType.scaleColor(i));
				g.drawLine(i + 50, 305, i + 50, 320);
			}
		}
	}
	
}
