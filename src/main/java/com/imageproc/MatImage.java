package com.imageproc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import javax.swing.JLabel;

import org.opencv.core.Mat;

public class MatImage {
	
	private int matrix[];
	private int sizex;
	private int sizey;
	
	private Image image;
	private Mat opencvMatrix;
	
	public MatImage() {
		this.matrix = null;
		this.sizex = 0;
		this.sizey = 0;
		
		this.image = null;
		this.opencvMatrix = null;
	}
	
	public MatImage(int sizex, int sizey) {
		this.sizex = sizex;
		this.sizey = sizey;
		this.matrix = new int[sizex*sizey];
		
		this.image = null;
		this.opencvMatrix = null;
	}
	
	public MatImage(int[] matrix, int sizex, int sizey) {
		this.setImage(matrix, sizex, sizey);
	}
	
	public MatImage(Image image) {
		this.setImage(image);
	}
	
	public MatImage(Mat matrix) {
		this.setImage(matrix);
	}
	
	
	/**
	 * Retorna a imagem num objecto <code>Image</code>. Se a imagem estiver apenas no
	 * formato da mitriz, será criado um objecto <code>Image</code> a partir da matriz.
	 * @return
	 */
	public Image getImage() {
		if(this.image == null) {
			JLabel l = new JLabel();
			this.image = l.createImage(new MemoryImageSource(sizex, sizey, matrix, 0, sizex));
		}
		
		return image;
	}
	
	public int[] getMatrix() {
		return this.matrix;
	}
	public int getSizeX() {
		return this.sizex;
	}
	public int getSizeY() {
		return this.sizey;
	}
	public int getSizeMatrix() {
		return this.sizex * this.sizey;
	}
	
	/**
	 * Define a imagem a partir de uma matriz
	 * @param matrix
	 * @param sizex
	 * @param sizey
	 */
	public void setImage(int[] matrix, int sizex, int sizey) {
		this.matrix = matrix;
		this.sizex = sizex;
		this.sizey = sizey;
		
		this.image = null;
		this.opencvMatrix = null;
	}
	
	/**
	 * Define a imagem a partir de uma <code>Image</code>.
	 * A imagem é convertida para a matriz.
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
		this.opencvMatrix = null;
		
		// Obter matriz da imagem
		// A variável "matrix" fica com os valores de cada pixel da imagem
		// A dimensão desta é determinada por "sizex" e "sizey"
		// Cada valor têm 4 bytes. Estes correspondem invidividualmente a:
		// Transparência, Vermelho, Verde, Azul
		// Para aceder aos valores individuais:
		// red = (color >> 16) & 0xff;
		// green = (color >> 8) & 0xff;
		// blue = color & 0xff;
		this.sizex = image.getWidth(null);
		this.sizey = image.getHeight(null);
		this.matrix = new int[sizex * sizey];
		
		PixelGrabber pg = new PixelGrabber(image, 0, 0, sizex, sizey, matrix,0, sizex);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			System.err.println("interrupted waiting for pixels!");
			return;
		}
		if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
			System.err.println("image fetch aborted or errored");
			return;
		}
	}
	
	/**
	 * Converts/writes a Mat into a BufferedImage.
	 * 
	 * @param matrix
	 *            Mat of type CV_8UC3 or CV_8UC1
	 * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
	 */
	public void setImage(Mat matrix) {
		int cols = matrix.cols();
		int rows = matrix.rows();

		int elemSize = (int) matrix.elemSize();
		byte[] data = new byte[cols * rows * elemSize];
		int type;

		matrix.get(0, 0, data);
		switch (matrix.channels()) {
		case 1:
			type = BufferedImage.TYPE_BYTE_GRAY;
			break;
		case 3:
			type = BufferedImage.TYPE_3BYTE_BGR;
			// bgr to rgb
			byte b;
			for (int i = 0; i < data.length; i = i + 3) {
				b = data[i];
				data[i] = data[i + 2];
				data[i + 2] = b;
			}
			break;
		default:
			this.opencvMatrix = null;
			return;
		}
		BufferedImage image = new BufferedImage(cols, rows, type);
		image.getRaster().setDataElements(0, 0, cols, rows, data);
		
		this.setImage(image);
		this.opencvMatrix = matrix;
	}
	
	/**
	 * Retorna a matriz para ser usada no OpenCV.
	 * Se a imagem for definida usando outro método
	 * que não a atribuíção da Matriz do OpenCV, não
	 * será retornado nada.
	 * @return Matrix original do OpenCV
	 */
	public Mat getOpenCVMatrix() {
		return this.opencvMatrix;
	}
	
	
	public int getRed(int pos) {
		return (this.matrix[pos] >> 16) & 0xff;
	}
	public int getGreen(int pos) {
		return (this.matrix[pos] >> 8) & 0xff;
	}
	public int getBlue(int pos) {
		return this.matrix[pos] & 0xff;
	}
	
	public int getY(int i) {
		return 0 + (int)(0.299 * this.getRed(i) + 0.587 * this.getGreen(i) + 0.114 * this.getBlue(i));
	}
	public int getCb(int i) {
		return 128 + (int)(-0.1687 * this.getRed(i) - 0.3313 * this.getGreen(i) + 0.500 * this.getBlue(i));
	}
	public int getCr(int i) {
		return 128 + (int)(0.500 * this.getRed(i) - 0.4187 * this.getGreen(i) - 0.0813 * this.getBlue(i));
	}
	
	public int getPixel(int pos) {
		return this.matrix[pos];
	}
	public void setPixel(int pos, int pixel) {
		// Invalida a imagem que esteja definina previamente
		this.image = null;
		this.opencvMatrix = null;
		
		this.matrix[pos] = pixel;
	}
	
	/**
	 * Atribui a nova cor especificada no pixel indicado.
	 * Esta acção invalida a imagem que foi definida previamente.
	 * @param pos
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void makeColor(int pos, int red, int green, int blue) {
		// Invalida a imagem que esteja definina previamente
		this.image = null;
		this.opencvMatrix = null;
		
		matrix[pos] = (255 << 24) | (red << 16) | (green << 8) | blue;
	}
}
