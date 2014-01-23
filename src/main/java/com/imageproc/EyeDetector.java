package com.imageproc;

import java.util.Stack;

public class EyeDetector {
	
	private MatImage image;
	private MatImage processedImage;

	public EyeDetector(MatImage image, boolean showImage) {
		this.image = image;
		
		EyeMapChr chr = new EyeMapChr(this.image, false);
		ContrastStretching_5_95 c = new ContrastStretching_5_95(chr.getNewImage(), false);
		processedImage = c.getNewImage();
		
		expand(processedImage.getRed(0), 0, 30);
		
		for(int n=0;n<2;n++) {
			int max=0;
			int pmax=0;
			for(int i=processedImage.getSizeMatrix()/5;i<processedImage.getSizeMatrix()/2;i++) {
				if(processedImage.getRed(i) > max) {
					max = processedImage.getRed(i);
					pmax=i;
				}
			}
			expand(max, pmax, 30);
		}
		
		PSI.getEngine().setImage(processedImage);
			
		if(showImage)
			new ImagePanel(processedImage, "Detecção Olhos");
	}
	
	private void expand(int max, int pos, int threshold) {
		int p = 0;
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(pos);
		
		while(!stack.isEmpty()) {
			pos = stack.pop();
			try {
				p = processedImage.getRed(pos);
			} catch(Exception e) { continue; }	// A posição não existe, está fora da imagem
		
			if(p < max - threshold)
				continue;
			
			processedImage.makeColor(pos, 0, 0, 0);
			
			stack.push(pos-1);
			stack.push(pos+1);
			stack.push(pos+processedImage.getSizeX());
			stack.push(pos-processedImage.getSizeX());
		}
	}
}
