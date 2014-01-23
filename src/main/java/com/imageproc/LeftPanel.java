package com.imageproc;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class LeftPanel extends Panel implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;

	private final String[] resourceImages = new String[]{
			"Barns grand tetons YCbCr separation.jpg",
			"Dots.jpg",
			"Face detection.png",
			"lena.jpg",
			"Roberts_Edge_src.jpg",
			"Sobel_src.jpg"
	};
	
	private PSIHelper engine;

	private TextField text;
	private Checkbox checkbox;
	private List list;
	
	public LeftPanel(final PSIHelper engine) {
		this.engine = engine;
		
		Button button;
		
		// Criar botões 
		this.setLayout(new GridLayout(10,1,1,1));

		list = new List();
		add(list);
		
		text = new TextField("face.jpg");
		text.setVisible(true);
		add(text);
		
		
		checkbox = new Checkbox("Aplicar ao anterior");
		checkbox.addItemListener(this);
		add(checkbox);
		
		button = new Button("Abrir Ficheiro");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
	
		button = new Button("Manipular Imagem");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
		
		button = new Button("Guardar Imagem");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
		
		button = new Button("Capturar Câmara");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
		
		button = new Button("Eye Map Chrominance");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
		
		button = new Button("Eye Map Luminance");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
		
		button = new Button("Eye Map");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);


		//////////////////////////////////
		// Lista de ficheiros em resources
		for(String resourceImage : resourceImages)
			if(ClassLoader.getSystemResource(resourceImage)!=null)
				list.add(resourceImage);

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					List list = (List) mouseEvent.getSource();
					engine.abrirFicheiro(list.getSelectedItem());
				}
			}
		});
		
		/////////////////
		// Setup panel
		setBackground(SystemColor.menu);
		setVisible(true);
		
	}
	
	// O utilizador carregou num botão
	public void actionPerformed (ActionEvent myEvent) {
		// Qual o botão premido?
		String nomeBotao = "";
		
		if(myEvent.getSource().getClass() == Button.class)
			nomeBotao = ((Button) myEvent.getSource()).getActionCommand();
		
		MatImage image = engine.getImage();
		
		// Realizar acção adequada
        switch (nomeBotao) {
            case "Abrir Ficheiro":
                engine.abrirFicheiro(text.getText());
                break;
            case "Manipular Imagem":
                engine.manipularImagem();
                break;
            case "Guardar Imagem":
                engine.guardarImagem();
                break;
            case "Capturar Câmara":
                engine.capturarImagem();
                break;
            case "Eye Map Chrominance":
                new EyeMapChr(image, true);
                break;
            case "Eye Map Luminance":
                new EyeMapLum(image, true);
                break;
            case "Eye Map":
                new EyeMap(image, true);
                break;
        }
	}
	
	public void itemStateChanged(ItemEvent myEvent) {
		// Qual o botão premido?
		String nomeBotao = "";
		
		if(myEvent.getSource().getClass() == Checkbox.class)
			nomeBotao = ((Checkbox) myEvent.getSource()).getLabel();
		
		if (nomeBotao.equals("Aplicar ao anterior")) engine.setAplicarAnterior(((Checkbox) myEvent.getSource()).getState());
	}
}
