package com.imageproc;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private PSIHelper engine;

	private MenuBar menuBar;
	private JDesktopPane theDesktop;
	private LeftPanel rightPanel;
	
	private Point posAddWindow = new Point(0, 0);
	
	public MainWindow() {
		engine = new PSIHelper(this);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{140, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		Menu menu;
		MenuItem menuItem;
		menuBar = new MenuBar();
		///////////////////////
		// Ficheiro
		menu = new Menu("Ficheiro");
		menuBar.add(menu);

		menuItem = new MenuItem("Abrir Jogo");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new MenuItem("Abrir Imagem...");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();
		
		menuItem = new MenuItem("Sair");
		menuItem.addActionListener(this);
		menu.add(menuItem);					
		
		///////////////////////
		// Canais
		menu = new Menu("Canais");
		menuBar.add(menu);

		menuItem = new MenuItem("Vermelho");
		menuItem.addActionListener(this);
		menu.add(menuItem);	

		menuItem = new MenuItem("Verde");
		menuItem.addActionListener(this);
		menu.add(menuItem);					

		menuItem = new MenuItem("Azul");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new MenuItem("Tonalidade");
		menuItem.addActionListener(this);
		menu.add(menuItem);					

		menuItem = new MenuItem("Saturação");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new MenuItem("Intensidade");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator();

		menuItem = new MenuItem("Y (luminance)");
		menuItem.addActionListener(this);
		menu.add(menuItem);					

		menuItem = new MenuItem("Cb");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new MenuItem("Cr");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		///////////////////////
		// Histogramas
		menu = new Menu("Histogramas");
		menuBar.add(menu);
		
		menuItem = new MenuItem("Histogramas HSI");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Histograma Tonalidade");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Histograma Saturação");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Histograma Intensidade");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new MenuItem("Histogramas RGB");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Histograma Vermelho");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Histograma Verde");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Histograma Azul");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new MenuItem("Histogramas YCrCb");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Histograma Y");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Histograma Cr");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Histograma Cb");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		///////////////////////
		// Manipulações
		menu = new Menu("Manipulações");
		menuBar.add(menu);
	
		menuItem = new MenuItem("Cinzentos");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	
		menuItem = new MenuItem("Negativo");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	
		menuItem = new MenuItem("Mirror X");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	
		menuItem = new MenuItem("Mirror Y");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	
		menuItem = new MenuItem("Mix HSI");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	
		menuItem = new MenuItem("Intensity Slicing");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new MenuItem("Contrast Stretching");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Contrast Stretching 5% - 95%");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Contrast Stretching - Histogram Equalization");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		///////////////////////
		// Filtros
		menu = new Menu("Filtros");
		menuBar.add(menu);

		menuItem = new MenuItem("Filtro Derivativo");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new MenuItem("Filtro Sobel");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Filtro Sobel Horizontal");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Filtro Sobel Vertical");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Filtro Roberts");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();
		
		menuItem = new MenuItem("Filtro Gaussiano");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Filtro Média");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Filtro Mediana");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();
		
		menuItem = new MenuItem("Filtro Erosão");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Filtro Dilação");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Filtro Erosão Cinzentos");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new MenuItem("Filtro Dilação Cinzentos");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		///////////////////////
		// OpenCV
		menu = new Menu("OpenCV");
		menuBar.add(menu);

		menuItem = new MenuItem("Detectar Faces");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new MenuItem("Detectar Olhos");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new MenuItem("Detectar Boca");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new MenuItem("Detectar Tudo");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		///////////////////
		// Ajuda
		menu = new Menu("Ajuda");
		menuBar.add(menu);
	
		menuItem = new MenuItem("Acerca");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		setMenuBar(menuBar);
		
		///////////////////
		// Painel lateral
		rightPanel = new LeftPanel(engine);
		GridBagConstraints gbc_app = new GridBagConstraints();
		gbc_app.fill = GridBagConstraints.VERTICAL;
		add(rightPanel, gbc_app);
		
		theDesktop = new JDesktopPane();
		GridBagConstraints gbc_theDesktop = new GridBagConstraints();
		gbc_theDesktop.fill = GridBagConstraints.BOTH;
		getContentPane().add(theDesktop, gbc_theDesktop); // add desktop pane to frame
		
		
		// Setup window
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ImageProc");
		setVisible(true);
		
		
		try {
			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void actionPerformed (ActionEvent myEvent) {
		// Qual o botão premido?
		String nomeBotao = new String();

		if(myEvent.getSource().getClass() == MenuItem.class)
			nomeBotao = ((MenuItem) myEvent.getSource()).getActionCommand();
		
		MatImage image = engine.getImage();
		
		// Realizar acção adequada
		if (nomeBotao.equals("Sair")) System.exit(0);
		else if (nomeBotao.equals("Abrir Jogo")) {
			GameWindow game = new GameWindow();
		}
		else if (nomeBotao.equals("Abrir Imagem...")) {
			JFileChooser fileOpen = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("Imagens","bmp","png","jpg","gif");
			fileOpen.addChoosableFileFilter(filter);
			fileOpen.setFileFilter(filter);
			fileOpen.setCurrentDirectory(new File(System.getProperty("user.dir")+File.separatorChar+"images"));
			if(fileOpen.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				engine.abrirFicheiro(fileOpen.getSelectedFile().getAbsolutePath());
		}
		else if (nomeBotao.equals("Vermelho")) new RedChannel(image, true);
		else if (nomeBotao.equals("Verde")) new GreenChannel(image, true);
		else if (nomeBotao.equals("Azul")) new BlueChannel(image, true);
		
		else if (nomeBotao.equals("Tonalidade")) new HueChannel(image, true);
		else if (nomeBotao.equals("Saturação")) new SaturationChannel(image, true);
		else if (nomeBotao.equals("Intensidade")) new IntensityChannel(image, true);
		
		else if (nomeBotao.equals("Y (luminance)")) new YChannel(image, true);
		else if (nomeBotao.equals("Cb")) new CbChannel(image, true);
		else if (nomeBotao.equals("Cr")) new CrChannel(image, true);
		
		else if (nomeBotao.equals("Histogramas HSI")) engine.mostrarHistogramasHSI();
		else if (nomeBotao.equals("Histograma Tonalidade")) new Histogram(image, new HistogramHue()).showWindow();
		else if (nomeBotao.equals("Histograma Saturação")) new Histogram(image, new HistogramSaturation()).showWindow();
		else if (nomeBotao.equals("Histograma Intensidade")) new Histogram(image, new HistogramIntensity()).showWindow();
		
		else if (nomeBotao.equals("Histogramas RGB")) engine.mostrarHistogramasRGB();
		else if (nomeBotao.equals("Histograma Vermelho")) new Histogram(image, new HistogramRed()).showWindow();
		else if (nomeBotao.equals("Histograma Verde")) new Histogram(image, new HistogramGreen()).showWindow();
		else if (nomeBotao.equals("Histograma Azul")) new Histogram(image, new HistogramBlue()).showWindow();
		
		else if (nomeBotao.equals("Histogramas YCrCb")) engine.mostrarHistogramasYCrCb();
		else if (nomeBotao.equals("Histograma Y")) new Histogram(image, new HistogramY()).showWindow();
		else if (nomeBotao.equals("Histograma Cb")) new Histogram(image, new HistogramCb()).showWindow();
		else if (nomeBotao.equals("Histograma Cr")) new Histogram(image, new HistogramCr()).showWindow();

		else if (nomeBotao.equals("Cinzentos")) new GrayScale(image, true);
		else if (nomeBotao.equals("Negativo")) new Negative(image, true);
		else if (nomeBotao.equals("Mirror X")) new MirrorX(image, true);
		else if (nomeBotao.equals("Mirror Y")) new MirrorY(image, true);
		else if (nomeBotao.equals("Mix HSI")) new Mix(image, true);
		else if (nomeBotao.equals("Intensity Slicing")) new IntensitySlicing(image, true);
		
		else if (nomeBotao.equals("Contrast Stretching")) new ContrastStretching(image, true);
		else if (nomeBotao.equals("Contrast Stretching 5% - 95%")) new ContrastStretching_5_95(image, true);
		else if (nomeBotao.equals("Contrast Stretching - Histogram Equalization")) new ContrastStretching_HistogramEqualization(image, true);
		
		else if (nomeBotao.equals("Filtro Derivativo")) engine.filtroDerivativo();
		else if (nomeBotao.equals("Filtro Sobel")) engine.filtroSobel();
		else if (nomeBotao.equals("Filtro Sobel Horizontal")) engine.filtroSobelHorizontal();
		else if (nomeBotao.equals("Filtro Sobel Vertical")) engine.filtroSobelVertical();
		else if (nomeBotao.equals("Filtro Gaussiano")) engine.filtroGaussian();
		else if (nomeBotao.equals("Filtro Média")) engine.filtroMedia();
		else if (nomeBotao.equals("Filtro Mediana")) engine.filtroMediana();
		else if (nomeBotao.equals("Filtro Roberts")) engine.filtroRoberts();
		else if (nomeBotao.equals("Filtro Erosão")) engine.filtroErosion();
		else if (nomeBotao.equals("Filtro Dilação")) engine.filtroDilation();
		else if (nomeBotao.equals("Filtro Erosão Cinzentos")) engine.filtroErosionGrayscale();
		else if (nomeBotao.equals("Filtro Dilação Cinzentos")) engine.filtroDilationGrayscale();
		
		else if (nomeBotao.equals("Detectar Faces")) engine.detectarFaces();
		else if (nomeBotao.equals("Detectar Olhos")) engine.detectarOlhos();
		else if (nomeBotao.equals("Detectar Boca")) engine.detectarBoca();
		else if (nomeBotao.equals("Detectar Tudo")) ImageWebcam.detectAll(image, true);
		
		else if (nomeBotao.equals("Acerca")) new HelpWindow(this);
	}
	
	public void addWindow(JInternalFrame frame) {
		if(posAddWindow.x + frame.getWidth() > theDesktop.getWidth()) {
			posAddWindow.y += 40;
			posAddWindow.x = posAddWindow.y / 5;
		}
		
		if(posAddWindow.y + frame.getHeight() > theDesktop.getHeight()) {
			posAddWindow.x = 0;
			posAddWindow.y = 0;
		}
		
		frame.setLocation(posAddWindow);
		theDesktop.add(frame);
		frame.toFront();
		
		posAddWindow.x += frame.getWidth();
	}
	
	public static void main(String[] args) {
		JFrame mainWindow = new MainWindow();
		// Iniciar janela maximizada
		mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
}
