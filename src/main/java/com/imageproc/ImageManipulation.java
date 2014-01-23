package com.imageproc;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class GrayScale extends ImageManipulation {
	public GrayScale(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		int cinzento;
		
		cinzento = (red+green+blue)/3;
		
		return PSI.makeColor(cinzento, cinzento, cinzento);
	}
	public String getName() {
		return "Cinzentos";
	}
}

class Negative extends ImageManipulation {
	public Negative(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		return PSI.makeColor(255-red, 255-green, 255-blue);
	}
	public String getName() {
		return "Negativo";
	}
}

class MirrorX extends ImageManipulation {
	public MirrorX(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		return image.getPixel(image.getSizeMatrix()-x-1);
	}
	public String getName() {
		return "Mirror X";
	}
}

class MirrorY extends ImageManipulation {
	public MirrorY(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		int sizex = image.getSizeX();
		int r;
		
		r=(sizex-x%sizex-1)+((x/sizex)*sizex);
	
		return image.getPixel(r);
	}
	public String getName() {
		return "Mirror Y";
	}
}

class RedChannel extends ImageManipulation {
	public RedChannel(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		return PSI.makeColor(red, 0, 0);
	}
	public String getName() {
		return "Canal Vermelho";
	}
}

class GreenChannel extends ImageManipulation {
	public GreenChannel(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		return PSI.makeColor( 0, green, 0);
	}
	public String getName() {
		return "Canal Verde";
	}
}

class BlueChannel extends ImageManipulation {
	public BlueChannel(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		return PSI.makeColor(0, 0, blue);
	}
	public String getName() {
		return "Canal Azul";
	}
}

class HueChannel extends ImageManipulation {		// Tonalidade
	public HueChannel(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		int tonalidade;
		double theta;
		double R = (double)red/(double)255, G = (double)green/(double)255, B = (double)blue/(double)255;
		theta = Math.acos(
				(0.5 * ((R - G) + (R - B))) /
				Math.sqrt(Math.pow(R - G,2) + (R - B) * (G - B))
				);
		
		tonalidade = (int)(255.0f / (2.0f * Math.PI) * ((blue <= green) ? theta : 2.0f * Math.PI - theta));
		
				
		return PSI.makeColor(tonalidade, tonalidade, tonalidade);
	}
	public String getName() {
		return "Canal Tonalidade";
	}
}

class SaturationChannel extends ImageManipulation {
	public SaturationChannel(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		int saturacao = (int)(255 - ((double)765/(double)(red + green + blue)) *
				(double)Math.min(red, Math.min(green, blue)));
		return PSI.makeColor(saturacao, saturacao, saturacao);
	}
	public String getName() {
		return "Canal Saturação";
	}
}

class IntensityChannel extends ImageManipulation {
	public IntensityChannel(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		int intensidade = (int)((double)(red + green + blue)/(double)3);
		return PSI.makeColor(intensidade, intensidade, intensidade);
	}
	public String getName() {
		return "Canal Intensidade";
	}
}



class Mix extends ImageManipulation {
	private HueChannel hue;
	private SaturationChannel saturation;
	private IntensityChannel intensity;
	
	public Mix(MatImage image, boolean showImage) {
		super(image, showImage);
		
		hue = new HueChannel(image, false);
		saturation = new SaturationChannel(image, false);
		intensity = new IntensityChannel(image, false);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		int h = PSI.getRed(hue.transformPixel(red, green, blue, x));
		int s = PSI.getRed(saturation.transformPixel(red, green, blue, x));
		int i = PSI.getRed(intensity.transformPixel(red, green, blue, x));
		
		return PSI.makeColor(h, s, i);
	}
	public String getName() {
		return "Mix";
	}
	
}

class IntensitySlicing extends ImageManipulation {
	
	private int threshold = 130;
	private int color = PSI.makeColor(0, 255, 0);
	private boolean upOrDown = false;
	
	
	public IntensitySlicing(MatImage image, boolean showImage) {
		super(image, showImage);
		
		/* *********************************/
		/* Janela de configuração do slice */
		final JDialog dialog = new JDialog(PSI.getEngine().getMainWindow(), "Intensity Sclicing", true);
		dialog.setLocationRelativeTo(PSI.getEngine().getMainWindow());
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setType(Type.UTILITY);
		dialog.setLayout(new GridLayout(6,1,1,1));
		
		dialog.add(new JLabel("Valor de threshold para a intensidade:"));
		
		final JLabel lblThreshold = new JLabel(String.valueOf(this.threshold)); 
		lblThreshold.setHorizontalAlignment(SwingConstants.CENTER);
		
		final JSlider slider = new JSlider(0,255,this.threshold);
		slider.addChangeListener(new ChangeListener() { public void stateChanged(ChangeEvent ce) {
			JSlider slider = (JSlider)ce.getSource(); lblThreshold
			.setText(String.valueOf(slider.getValue())); } });
		dialog.add(slider);
		
		dialog.add(lblThreshold);
		
		final JButton btnColor = new JButton("Cor");
		btnColor.setBackground(new Color(PSI.getRed(color), PSI.getGreen(color), PSI.getBlue(color)));
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnColor.setBackground(JColorChooser.showDialog(null, "Cor", btnColor.getBackground()));
			}
		});
		dialog.add(btnColor);
		
		final JCheckBox checkBox = new JCheckBox("Aplicar acima do threshold");
		checkBox.setSelected(this.upOrDown);
		dialog.add(checkBox);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = btnColor.getBackground().getRGB();
				threshold = slider.getValue();
				upOrDown = checkBox.isSelected();
				dialog.dispose();
				transformImage();
			}
		});
		dialog.add(btnOk);
		
		dialog.pack();
		dialog.setVisible(true);
	}

	public int transformPixel(int red, int green, int blue, int x) {
		int i = (int)((double)(red + green + blue)/(double)3);	// Intensidade
		if((i>=this.threshold && this.upOrDown) || (i<=this.threshold && !this.upOrDown))
			return this.color;
		
		return PSI.makeColor(red, green, blue);
	}
	public String getName() {
		return "Intensity Slicing";
	}
	
}

/**
 * http://www.equasys.de/colorconversion.html
 */
class YChannel extends ImageManipulation {
	public YChannel(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		int Y = 0 + (int)(0.299 * red + 0.587 * green + 0.114 * blue); //full-range color format
		return PSI.makeColor(Y, Y, Y);
	}
	public String getName() {
		return "Canal Y (luminance)";
	}
}

class CbChannel extends ImageManipulation {
	public CbChannel(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		int Cb = 128 + (int)(-0.1687 * red - 0.3313 * green + 0.500 * blue); //full-range color format
		return PSI.makeColor(255-Cb, 255-Cb, Cb);
	}
	public String getName() {
		return "Canal Cb";
	}
}

class CrChannel extends ImageManipulation {
	public CrChannel(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	public int transformPixel(int red, int green, int blue, int x) {
		int Cr = 128 + (int)(0.500 * red - 0.4187 * green - 0.0813 * blue); //full-range color format
		return PSI.makeColor(Cr, Cr, Cr);
	}
	public String getName() {
		return "Canal Cr";
	}
}


class ContrastStretching extends ImageManipulation {
	private int max=0, min=255;

	public ContrastStretching(MatImage image, boolean showImage) {
		super(image, showImage);
		/*int[] hist = new Histogram(matrix, new HistogramRed()).getHistogram();
		
		for(int i=0; i<256; i++) {
			if(hist[i]>hist[max])
				max=i;
			if(hist[i]<hist[min])
				min=i;
		}*/
		
		int sizex = image.getSizeX();
		int sizey = image.getSizeY();
		
		for(int i=0;i<sizex*sizey;i++) {
			int color = (image.getRed(i)+image.getGreen(i)+image.getBlue(i)) / 3;
			
			if(color>max)
				max=color;
			if(color<min)
				min=color;
		}
		this.transformImage();
	}
	
	public int transformPixel(int red, int green, int blue, int x) {
		int color = (red + green + blue) / 3;
		int resut = 255 * (color - min) / (max - min);
		return PSI.makeColor(resut, resut, resut);
	}
	public String getName() {
		return "Contrast Stretching";
	}
	
}

class ContrastStretching_5_95 extends ImageManipulation {
	private int max=-1, min=-1;

	public ContrastStretching_5_95(MatImage image, boolean showImage) {
		super(image, showImage);
		
		int sizex = image.getSizeX();
		int sizey = image.getSizeY();
		int[] hist = new Histogram(image, new HistogramIntensity()).getHistogram();
		int sum=0;
		
		for(int i=0; i<256; i++) {
			sum+=hist[i];
			if(sum>sizex*sizey*0.05 && min==-1)
				min=i;
			if(sum>sizex*sizey*0.95 && max==-1)
				max=i;
		}
		this.transformImage();
	}
	
	public int transformPixel(int red, int green, int blue, int x) {
		int resultr = 0;
		int resultg = 0;
		int resultb = 0;
		
		try {	// Se divisão for por zero
			resultr = 255 * (red - min) / (max - min);
			resultg = 255 * (green - min) / (max - min);
			resultb = 255 * (blue - min) / (max - min);
		} catch (Exception e) { e.printStackTrace(); }
		
		if(resultr>255) resultr=255;
		if(resultr<0) resultr=0;
		if(resultg>255) resultg=255;
		if(resultg<0) resultg=0;
		if(resultb>255) resultb=255;
		if(resultb<0) resultb=0;
		
		return PSI.makeColor(resultr, resultg, resultb);
	}
	public String getName() {
		return "Contrast Stretching 5% - 95%";
	}
}

/**
 * <b>Reference:</b>
 * {@link http://homepages.inf.ed.ac.uk/rbf/HIPR2/histeq.htm}
 */
class ContrastStretching_HistogramEqualization extends ImageManipulation {
	double[] hist_eq = new double[256];

	public ContrastStretching_HistogramEqualization(MatImage image, boolean showImage) {
		super(image, showImage);
		
		int sizex = image.getSizeX();
		int sizey = image.getSizeY();
		int[] hist = new Histogram(image, new HistogramIntensity()).getHistogram();
		double sum=0;
		
		for(int i=0; i<256; i++) {
			sum+=(double)(hist[i])/((double)(sizex*sizey));
			hist_eq[i]=sum;
		}
		this.transformImage();
	}
	
	public int transformPixel(int red, int green, int blue, int x) {
		int i = (red + green + blue) / 3;
		
		int resultr = (int) (red * hist_eq[i]);
		int resultg = (int) (green * hist_eq[i]);
		int resultb = (int) (blue * hist_eq[i]);
		
		return PSI.makeColor(resultr, resultg, resultb);
	}
	public String getName() {
		return "Contrast Stretching - Histogram Equalization";
	}
}


class EyeMapChr extends ImageManipulation {
	
	public EyeMapChr(MatImage image, boolean showImage) {
		super(image, showImage);
		this.transformImage();
	}
	
	public int transformPixel(int red, int green, int blue, int x) {
		int cr = image.getCr(x);
		int cb = image.getCb(x);
		
		int emc = ( cb*cb + (255-cr)*(255-cr) + (cb/cr) ) / 3;
		emc/=255;
		if(emc<0) emc=0;
		if(emc>255) emc=255;
		
		return PSI.makeColor(emc, emc, emc);
	}
	public String getName() {
		return "Eye Map Chrominance";
	}
}
class EyeMapLum extends ImageManipulation {
	private MatImage erosion;
	private MatImage dilation;

	public EyeMapLum(MatImage image, boolean showImage) {
		super(image, showImage);
		erosion = new GrayscaleErosionFilter(image).applyMask(FilterBorder.BLACK);
		dilation = new GrayscaleDilationFilter(image).applyMask(FilterBorder.BLACK);
		this.transformImage();
	}
	
	public int transformPixel(int red, int green, int blue, int x) {
		int d = dilation.getRed(x);
		int e = erosion.getRed(x);
		int r = d/(e+1);
		//int eml = (r==0 ? 0 : 255);
		return PSI.makeColor(r, r, r);
	}
	public String getName() {
		return "Eye Map Luminance";
	}
}
class EyeMap extends ImageManipulation {
	private EyeMapChr chr;
	private EyeMapLum lum;

	public EyeMap(MatImage image, boolean showImage) {
		super(image, showImage);
		chr = new EyeMapChr(image, false);
		lum = new EyeMapLum(image, false);
		this.transformImage();
	}
	
	public int transformPixel(int red, int green, int blue, int x) {	
		int c = chr.getNewImage().getRed(x);
		int l = lum.getNewImage().getRed(x);
		int r = c * l;
		if(r>255) r = 255;
		return PSI.makeColor(r, r, r);
	}
	public String getName() {
		return "Eye Map";
	}
}

public abstract class ImageManipulation {
	protected MatImage image;
	protected MatImage newImage;
	protected PSIHelper engine;
	private boolean showImage;
	
	public ImageManipulation(MatImage image, boolean showImage) {
		this.image = image;
		this.newImage = new MatImage(image.getSizeX(), image.getSizeY());
		this.engine = PSI.getEngine();
		this.showImage = showImage;
	}
	
	public abstract int transformPixel(int red, int green, int blue, int x);
	public abstract String getName();
	
	public void transformImage() {
		// Variáveis de apoio
		int verde, vermelho, azul;
		int i;
	
		// Ciclo que percorre a imagem inteira
		for (i=0; i < image.getSizeMatrix(); i++)
		{
			vermelho = image.getRed(i);
			verde = image.getGreen(i);
			azul = image.getBlue(i);
			
			// Criar valor de cor
			newImage.setPixel(i, this.transformPixel(vermelho, verde, azul, i));
		}
		engine.setImage(newImage);
		
		if(showImage)
			this.showImage();
	}
	
	public MatImage getImage() {
		return this.image;
	}
	
	public MatImage getNewImage() {
		return this.newImage;
	}
	
	public void showImage() {
		// Carregar a imagem no painel externo de visualização
		new ImagePanel(this.newImage, this.getName());
	}
}
