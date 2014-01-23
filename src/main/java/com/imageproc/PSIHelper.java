package com.imageproc;

public class PSIHelper extends PSI {
	
	public PSIHelper(MainWindow mainWindow) {
		super(mainWindow);
	}
	
	public void mostrarHistogramasHSI() {
		new Histogram(image,new HistogramHue()).showWindow();
		new Histogram(image,new HistogramSaturation()).showWindow();
		new Histogram(image,new HistogramIntensity()).showWindow();
	}
	
	public void mostrarHistogramasRGB() {
		new Histogram(image, new HistogramRed()).showWindow();
		new Histogram(image, new HistogramGreen()).showWindow();
		new Histogram(image, new HistogramBlue()).showWindow();
	}
	
	public void mostrarHistogramasYCrCb() {
		new Histogram(image, new HistogramY()).showWindow();
		new Histogram(image, new HistogramCr()).showWindow();
		new Histogram(image, new HistogramCb()).showWindow();
	}

	public void filtroDerivativo() {
		Filter filter = new DerivativeFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroSobel() {
		Filter filter = new SobelFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroSobelHorizontal() {
		Filter filter = new SobelHorizontalFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroSobelVertical() {
		Filter filter = new SobelVerticalFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroGaussian() {
		Filter filter = new GaussianFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroMedia() {
		Filter filter = new MeanFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroMediana() {
		Filter filter = new MedianFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroRoberts() {
		Filter filter = new RobertsFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroErosion() {
		Filter filter = new ErosionFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroDilation() {
		Filter filter = new DilationFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroErosionGrayscale() {
		Filter filter = new GrayscaleErosionFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}
	public void filtroDilationGrayscale() {
		Filter filter = new GrayscaleDilationFilter(image);
		setImage(filter.applyMask(FilterBorder.NONE));
		filter.showWindow();
	}

	
}
