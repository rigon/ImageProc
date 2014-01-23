package com.imageproc;
import nu.pattern.OpenCV;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

// ---------------------------------------------------------------
// Classe que cria uma Frame principal, onde se situam os comandos
// de manipulação de imagem. Implementa a interface ActionListener
// para lidar com os eventos produzidos pelos botões.
// ---------------------------------------------------------------
class PSI {
	private static PSIHelper engine = null;
	private MainWindow mainWindow = null;
	
	protected MatImage image;
	
	private boolean aplicarAnterior = false;
	
	public static PSIHelper getEngine() {
		return PSI.engine;
	}
	
	// Construtor
	public PSI(MainWindow mainWindow) {
		PSI.engine = (PSIHelper) this;
		this.mainWindow = mainWindow;
		
		//System.loadLibrary("opencv_java249");
		OpenCV.loadLocally();
	}
	
	// Abrir um ficheiro de Imagem
	public void abrirFicheiro(String filename) {
		// Load Image - Escolher nome da imagem a carregar!
		// Bem mais interessante usar uma interface gráfica para isto...
		Image image;
		File file = new File(filename);
		if(file.isFile()) {
            URL fileURL = null;
            try {
                fileURL = file.toURI().toURL();
				image = LoadImage(fileURL);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
		else
			image = LoadImage(ClassLoader.getSystemResource(filename));
	
		this.image = new MatImage(image);
		
		// Visualizar imagem - Usar um Frame externo
		new ImagePanel(image, file + " [ORIGINAL]");
	}

	// Exemplo de uma função que manipula a imagem
	public void manipularImagem()
	{
		// Exemplo: Conversão de uma imagem a cores, para uma imagem a preto e branco

		// Variáveis de apoio
		int verde, vermelho, azul, cinzento;
		int x;

		// Ciclo que percorre a imagem inteira
		for (x=0; x < image.getSizeMatrix(); x++)
		{
			vermelho = image.getRed(x);
			verde = image.getGreen(x);
			azul = image.getBlue(x);
			
			// Calcular luminosidade
			cinzento = (vermelho+verde+azul)/3;
			
			// Criar valor de cor
			image.makeColor(x, cinzento, cinzento, cinzento);
		}
		
		// Cria e Carregar a imagem no painel externo de visualização
		new ImagePanel(image);
	}

	// Função de apoio que grava a imagem visualizada
	public void guardarImagem()
	{
		Image image = this.image.getImage();
		
		// Criar uma BufferedImage a partir de uma Image
		BufferedImage bi = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
	    Graphics bg = bi.getGraphics();
	    bg.drawImage(image, 0, 0, null);
	    bg.dispose();
	    
	    // Escrever ficheiro de saída
	    // Pq não implementar uma interface de escolha do nome?
		try {
			ImageIO.write(bi, "jpg", new File("resultado.jpg"));
		} catch (IOException e) {
		    System.err.println("Couldn't write output file!");
		    return;
		}
	}

	// Função de apoio que captura a imagem da câmara
	public void capturarImagem() {
		this.image = ImageWebcam.capture();
		// Visualizar imagem - Usar um Frame externo
		new ImagePanel(image, "[CAPTURA WEBCAM]");
	}
	// Função de apoio que captura a imagem da câmara
	public void detectarFaces() {
		MatImage faces = ImageWebcam.detectFaces(this.image, true);
		setImage(faces);
		// Visualizar imagem - Usar um Frame externo
		try { new ImagePanel(faces, "[FACES]"); } catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null,"Apenas válido para imagens capturadas a partir da câmara."); }
	}
	
	public void detectarOlhos() {
		//EyeDetector eye = new EyeDetector(image, true);
		MatImage eyes = ImageWebcam.detectEyes(this.image);
		setImage(eyes);
		// Visualizar imagem - Usar um Frame externo
		try { new ImagePanel(eyes, "[OLHOS]"); } catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null,"Apenas válido para imagens capturadas a partir da câmara."); }
	}
	
	public void detectarBoca() {
		MatImage mouth = ImageWebcam.detectMouth(this.image);
		setImage(mouth);
		// Visualizar imagem - Usar um Frame externo
		try { new ImagePanel(mouth, "[BOCA]"); } catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null,"Apenas válido para imagens capturadas a partir da câmara."); }
	}

	// Função de apoio que carrega uma imagem externa
	public Image LoadImage(URL fileName) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(fileName);
		MediaTracker mediaTracker = new MediaTracker(new Frame());
		mediaTracker.addImage(image, 0);
		try { mediaTracker.waitForID(0); }
		catch (InterruptedException ie) {};

		return image;
	}
	
	public MatImage getImage() {
		return this.image;
	}
	
	public void setImage(MatImage image) {
		if(this.aplicarAnterior)
			this.image = image;
	}
	
	public void setAplicarAnterior(boolean state) {
		this.aplicarAnterior = state;
	}
	
	public void addWindow(JInternalFrame frame) {
		mainWindow.addWindow(frame);
	}
	public Frame getMainWindow() {
		return mainWindow;
	}

	// Funções de apoio para extrair os valores de R, G e B de uma imagem.
	public static int getRed(int color) { return (color >> 16) & 0xff; }
	public static int getGreen(int color) { return (color >> 8) & 0xff; }
	public static int getBlue(int color) { return color & 0xff; }
	public static int makeColor(int red, int green, int blue) { return (255 << 24) | (red << 16) | (green << 8) | blue; }
}

//---------------------------------------------------------------
// Classe Frame de apoio para visualização de uma imagem
//--------------------------------------------------------------- 
class ImagePanel extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	private JLabel lblImage;
	private Image image;
	
	/////////////
	// Construtor
	public ImagePanel() {
		lblImage = new JLabel();
		add(BorderLayout.CENTER, lblImage);
		
		this.setResizable(false);
		this.setClosable(true);
		this.setIconifiable(true);
		this.setVisible(true);
	}
	
	public ImagePanel(String title) {
		this();
		this.setTitle(title);
		
		PSI.getEngine().addWindow(this);
	}
	public ImagePanel(Image image) {
		this();
		this.setImage(image);
		
		PSI.getEngine().addWindow(this);
	}
	public ImagePanel(Image newImage, String title) {
		this(newImage);
		this.setTitle(title);
	}
	public ImagePanel(MatImage image) throws NullPointerException {
		this(image.getImage());
	}
	public ImagePanel(MatImage image, String title) throws NullPointerException {
		this(image.getImage(),title);
	}
	
	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		lblImage.setIcon(new ImageIcon(image));
		this.pack();
		this.repaint();
	}
	
	// Desenhar imagem
	public void paint(Graphics g) {
		g.drawImage(image, 10, 39, null);
		super.paint(g);
	}
}
