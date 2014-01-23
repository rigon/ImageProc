package com.imageproc;
import java.util.Arrays;

enum FilterBorder {
	NONE, BLACK, NEAREST_PIXEL, IMAGE_REFLECTION
}


class DerivativeFilter extends Filter {
	public DerivativeFilter(MatImage image) {
		super(image);
	}
	public int[] getMask() {
		int[] mask = { 	0, -1, 0,
						-1, 4, -1,
						0, -1, 0};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		return applySpatialFilters(px, py, 1);
	}
	public String getName() {
		return "Filtro Derivativo";
	}
}


class SobelHorizontalFilter extends Filter {
	public SobelHorizontalFilter(MatImage image) {
		super(image);
	}
	public int[] getMask() {
		int[] mask = { 	 1,  2,  1,
				 		 0,  0,  0,
				 		-1, -2, -1};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		return applySpatialFilters(px, py, 1);
	}
	public String getName() {
		return "Filtro Sobel Horizontal";
	}
}


class SobelVerticalFilter extends Filter {
	public SobelVerticalFilter(MatImage image) {
		super(image);
	}
	public int[] getMask() {
		int[] mask = { 	-1, 0, 1,
				 		-2, 0, 2,
				 		-1, 0, 1};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		return applySpatialFilters(px, py, 1);
	}
	public String getName() {
		return "Filtro Sobel Vertical";
	}
}

/**
 * <b>Referência:</b>
 * {@link http://www.roborealm.com/help/Sobel.php}
 */
class SobelFilter extends Filter {
	int[] mask2 = {  1,  2,  1,
	 		 0,  0,  0,
	 		-1, -2, -1};
	
	public SobelFilter(MatImage image) {
		super(image);
	}
	public int[] getMask() {
		int[] mask = { 	-1, 0, 1,
				 		-2, 0, 2,
				 		-1, 0, 1};

		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		int sizex = this.image.getSizeX();
		
		int sumr1=0;
		int sumg1=0;
		int sumb1=0;
		
		int sumr2=0;
		int sumg2=0;
		int sumb2=0;
		
		for(int i=0; i<size_mask; i++) {
			for(int j=0; j<size_mask; j++) {
				int color = this.getPixel((py+i-size_mask/2)*sizex + px+j-size_mask/2);
				sumr1 += PSI.getRed(color) * mask[i*size_mask+j];
				sumg1 += PSI.getGreen(color) * mask[i*size_mask+j];
				sumb1 += PSI.getBlue(color) * mask[i*size_mask+j];
				
				sumr2 += PSI.getRed(color) * mask2[i*size_mask+j];
				sumg2 += PSI.getGreen(color) * mask2[i*size_mask+j];
				sumb2 += PSI.getBlue(color) * mask2[i*size_mask+j];
			}
			
		}
		
		int resultr = (int)Math.sqrt(sumr1*sumr1 + sumr2*sumr2);
		int resultg = (int)Math.sqrt(sumg1*sumg1 + sumg2*sumg2);
		int resultb = (int)Math.sqrt(sumb1*sumb1 + sumb2*sumb2);
		
		if(resultr>255) resultr=255;
		if(resultr<0) resultr=0;
		if(resultg>255) resultg=255;
		if(resultg<0) resultg=0;
		if(resultb>255) resultb=255;
		if(resultb<0) resultb=0;
			
		return PSI.makeColor(resultr, resultg, resultb);
	}
	public String getName() {
		return "Filtro Sobel";
	}
}


class GaussianFilter extends Filter {
	public GaussianFilter(MatImage image) {
		super(image);
	}
	public int[] getMask() {
		int[] mask = { 	1, 2, 1,
				 		2, 4, 2,
				 		1, 2, 1};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		return applySpatialFilters(px, py, 16);
	}
	public String getName() {
		return "Filtro Gaussian";
	}
}


class MeanFilter extends Filter {
	public MeanFilter(MatImage image) {
		super(image);
	}
	public int[] getMask() {
		int[] mask = { 	1, 1, 1,
				 		1, 1, 1,
				 		1, 1, 1};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		return applySpatialFilters(px, py, 9);
	}
	public String getName() {
		return "Filtro Média";
	}
}

class MedianFilter extends Filter {
	public MedianFilter(MatImage image) {
		super(image);
		this.size_mask=3;
	}
	public int[] getMask() {
		int[] mask = {};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		int sizex = this.image.getSizeX();
		
		int[] r = new int[9];
		int[] g = new int[9];
		int[] b = new int[9];
		
		for(int i=0; i<size_mask; i++) {
			for(int j=0; j<size_mask; j++) {
				int color = this.getPixel((py+i-size_mask/2)*sizex + px+j-size_mask/2);
				r[i*3+j] = PSI.getRed(color);
				g[i*3+j] = PSI.getGreen(color);
				b[i*3+j] = PSI.getBlue(color);
			}
			
		}
		Arrays.sort(r);
		Arrays.sort(g);
		Arrays.sort(b);
		
		return PSI.makeColor(r[5], g[5], b[5]);
	}
	public String getName() {
		return "Filtro Mediana";
	}
}

/**
 * <b>Referência:</b>
 * {@link http://www.roborealm.com/help/Roberts_Edge.php}
 */
class RobertsFilter extends Filter {
	public RobertsFilter(MatImage image) {
		super(image);
	}
	
	public int[] getMask() {
		int[] mask = { 	1, 0,	// Horizontal
						0, -1};
		
		@SuppressWarnings("unused")
		int[] mask2 = { 0, 1,	// Vertical
		 				-1, 0};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		int sizex = this.image.getSizeX();
		
		int p1 = this.getPixel(py*sizex + px);
		int p2 = this.getPixel(py*sizex + px + 1);
		int p3 = this.getPixel((py+1)*sizex + px);
		int p4 = this.getPixel((py+1)*sizex + px + 1);

		int p1r = PSI.getRed(p1);
		int p1g = PSI.getGreen(p1);
		int p1b = PSI.getBlue(p1);
		
		int p2r = PSI.getRed(p2);
		int p2g = PSI.getGreen(p2);
		int p2b = PSI.getBlue(p2);
		
		int p3r = PSI.getRed(p3);
		int p3g = PSI.getGreen(p3);
		int p3b = PSI.getBlue(p3);
		
		int p4r = PSI.getRed(p4);
		int p4g = PSI.getGreen(p4);
		int p4b = PSI.getBlue(p4);
		
		int resultr = (int)Math.sqrt( Math.abs(p1r-p4r)*Math.abs(p1r-p4r) + Math.abs(p2r-p3r)*Math.abs(p2r-p3r));
		int resultg = (int)Math.sqrt( Math.abs(p1g-p4g)*Math.abs(p1g-p4g) + Math.abs(p2g-p3g)*Math.abs(p2g-p3g));
		int resultb = (int)Math.sqrt( Math.abs(p1b-p4b)*Math.abs(p1b-p4b) + Math.abs(p2b-p3b)*Math.abs(p2b-p3b));
		
		if(resultr>255) resultr=255;
		if(resultr<0) resultr=0;
		if(resultg>255) resultg=255;
		if(resultg<0) resultg=0;
		if(resultb>255) resultb=255;
		if(resultb<0) resultb=0;
		
		return PSI.makeColor(resultr, resultg, resultb);
	}
	public String getName() {
		return "Filtro Roberts";
	}
}

class ErosionFilter extends Filter {
	public ErosionFilter(MatImage image) {
		super(image);
	}
	public int[] getMask() {
		int[] mask = { 	73, 125, 73,
				125, 150, 125,
		 		73, 125, 73};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		int sizex = this.image.getSizeX();
		
		int sumY=0;
		
		for(int i=0; i<size_mask; i++) {
			for(int j=0; j<size_mask; j++) {
				int pos = (py+i-size_mask/2)*sizex + px+j-size_mask/2;
				sumY += this.getY(pos) >= mask[i*size_mask+j] ? 1 : 0;
			}
			
		}
		
		sumY = sumY==mask.length ? 255 : 0;
		
		return PSI.makeColor(sumY, sumY, sumY);
	}
	public String getName() {
		return "Filtro Erosão";
	}
}

class DilationFilter extends Filter {
	public DilationFilter(MatImage image) {
		super(image);
	}
	public int[] getMask() {
		int[] mask = { 	73, 125, 73,
						125, 150, 125,
				 		73, 125, 73};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		int sizex = this.image.getSizeX();
		
		int sumY=0;
		
		for(int i=0; i<size_mask; i++) {
			for(int j=0; j<size_mask; j++) {
				int pos = (py+i-size_mask/2)*sizex + px+j-size_mask/2;
				sumY += this.getY(pos) >= mask[i*size_mask+j] ? 1 : 0;
			}
			
		}
		
		sumY = sumY>0 ? 255 : 0;
		
		return PSI.makeColor(sumY, sumY, sumY);
	}
	public String getName() {
		return "Filtro Dilação";
	}
}
class GrayscaleErosionFilter extends Filter {
	public GrayscaleErosionFilter(MatImage image) {
		super(image);
	}
	public int[] getMask() {
		int[] mask = { 	0, 1, 0,
						1, 1, 1,
						0, 1, 0};
//		int[] mask = { 	0, 0, 0, 1, 0, 0, 0,
//						0, 1, 1, 1, 1, 1, 0,
//						0, 1, 1, 1, 1, 1, 0,
//						1, 1, 1, 1, 1, 1, 1,
//						0, 1, 1, 1, 1, 1, 0,
//						0, 1, 1, 1, 1, 1, 0,
//						0, 0, 0, 1, 0, 0, 0};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		int sizex = this.image.getSizeX();
		
		int min=256;
		
		for(int i=0; i<size_mask; i++) {
			for(int j=0; j<size_mask; j++) {
				int pos = (py+i-size_mask/2)*sizex + px+j-size_mask/2;
				if(mask[i*size_mask+j] == 1 && this.getY(pos) < min)
					min = this.getY(pos);
			}
		}
		
		//Y = sumY==mask.length ? 255 : 0;
		if(min==256) min = 0;
		
		return PSI.makeColor(min, min, min);
	}
	public String getName() {
		return "Filtro Erosão Escala de Cinzentos";
	}
}
class GrayscaleDilationFilter extends Filter {
	public GrayscaleDilationFilter(MatImage image) {
		super(image);
	}
	public int[] getMask() {
//		int[] mask = { 	73, 125, 73,
//		125, 150, 125,
// 		73, 125, 73};
		int[] mask = { 	0, 1, 0,
						1, 1, 1,
						0, 1, 0};
//		int[] mask = { 	0, 0, 1, 0, 0,
//						0, 1, 1, 1, 0,
//						1, 1, 1, 1, 1,
//						0, 1, 1, 1, 0,
//						0, 0, 1, 0, 0};
//		int[] mask = { 	0, 0, 0, 1, 0, 0, 0,
//						0, 1, 1, 1, 1, 1, 0,
//						0, 1, 1, 1, 1, 1, 0,
//						1, 1, 1, 1, 1, 1, 1,
//						0, 1, 1, 1, 1, 1, 0,
//						0, 1, 1, 1, 1, 1, 0,
//						0, 0, 0, 1, 0, 0, 0};
		return mask;
	}
	public int applyMaskAtPoint(int px, int py) {
		int sizex = this.image.getSizeX();
		
		int max=0;
		
		for(int i=0; i<size_mask; i++) {
			for(int j=0; j<size_mask; j++) {
				int pos = (py+i-size_mask/2)*sizex + px+j-size_mask/2;
				if(mask[i*size_mask+j] == 1 && this.getY(pos) > max)
					max = this.getY(pos);
			}
		}
		
		return PSI.makeColor(max, max, max);
	}
	public String getName() {
		return "Filtro Dilação Escala de Cinzentos";
	}
}

public abstract class Filter {

	protected int[] mask;
	protected int size_mask;
	protected MatImage image;
	protected MatImage newImage;
	
	protected Filter(MatImage image) {
		mask = getMask();
		size_mask = (int) Math.sqrt(mask.length);

		this.image = image;
		this.newImage = new MatImage(image.getSizeX(), image.getSizeY());
	}
	
	public abstract int[] getMask();
	public abstract int applyMaskAtPoint(int px, int py);
	public abstract String getName();
	
	public void showWindow() {
		new ImagePanel(this.newImage,this.getName());
	}
	
	public int getPixel(int x) {
		try {
			return this.image.getMatrix()[x];
		} catch(Exception e) {
			return PSI.makeColor(0, 0, 0);
		}
	}
	
	public int getY(int x) {
		try {
			@SuppressWarnings("unused")
			int tmp = this.image.getMatrix()[x];
			return this.image.getY(x);
		} catch(Exception e) {
			return PSI.makeColor(0, 0, 0);
		}
	}
	
	public int applySpatialFilters(int px, int py, int normalizeFactor) {
		int sizex = this.image.getSizeX();
		
		int sumr=0;
		int sumg=0;
		int sumb=0;
		
		for(int i=0; i<size_mask; i++) {
			for(int j=0; j<size_mask; j++) {
				int color = this.getPixel((py+i-size_mask/2)*sizex + px+j-size_mask/2);
				sumr += PSI.getRed(color) * mask[i*size_mask+j];
				sumg += PSI.getGreen(color) * mask[i*size_mask+j];
				sumb += PSI.getBlue(color) * mask[i*size_mask+j];
			}
			
		}
		
		int resultr = (int)((double)sumr/(double)normalizeFactor);
		int resultg = (int)((double)sumg/(double)normalizeFactor);
		int resultb = (int)((double)sumb/(double)normalizeFactor);
		
		if(resultr>255) resultr=255;
		if(resultr<0) resultr=0;
		if(resultg>255) resultg=255;
		if(resultg<0) resultg=0;
		if(resultb>255) resultb=255;
		if(resultb<0) resultb=0;
		
		return PSI.makeColor(resultr, resultg, resultb);
	}
	
	public MatImage applyMask(FilterBorder border) {
		int sizex = this.image.getSizeX();
		int sizey = this.image.getSizeY();
		
		for(int i=0; i<sizey; i++) {
			for(int j=0; j<sizex; j++) {
				switch(border) {
				case NONE:
 					if(i>=size_mask/2 && i<sizey-size_mask/2 && j>=size_mask/2 && j<sizex-size_mask/2)
						newImage.setPixel(
								(i-size_mask/2)*(sizex-size_mask+1)+(j-size_mask/2),
								applyMaskAtPoint(j, i));
					break;
				case BLACK:
					newImage.setPixel(i*sizex+j,applyMaskAtPoint(j, i));	
					break;
				case IMAGE_REFLECTION:
					break;
				case NEAREST_PIXEL:
					break;
				default:
					break;
				}
			}
		}
		
		if(border==FilterBorder.NONE) {
			this.newImage.setImage(newImage.getMatrix(), newImage.getSizeX()-size_mask+1, image.getSizeY()-size_mask+1);
		}
		return this.newImage;
	}
}
