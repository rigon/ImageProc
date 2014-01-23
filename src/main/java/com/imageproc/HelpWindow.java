package com.imageproc;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class HelpWindow extends JDialog {
	private static final long serialVersionUID = 1L;
	private HelpWindow window;

	public HelpWindow(MainWindow mainWindow) {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setSize(new Dimension(450, 240));
		setModal(true);
		setLocationRelativeTo(mainWindow);
		window = this;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ImageProc");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setBounds(10, 33, 424, 37);
		getContentPane().add(lblNewLabel);
		
		JLabel lblUmProgramaRealizado = new JLabel("Um programa realizado por:");
		lblUmProgramaRealizado.setHorizontalAlignment(SwingConstants.CENTER);
		lblUmProgramaRealizado.setBounds(10, 100, 424, 14);
		getContentPane().add(lblUmProgramaRealizado);
		
		JLabel lblNelsonMorais = new JLabel("Nelson Morais");
		lblNelsonMorais.setHorizontalAlignment(SwingConstants.CENTER);
		lblNelsonMorais.setBounds(10, 132, 424, 14);
		getContentPane().add(lblNelsonMorais);
		
		JLabel lblRicardoGonalves = new JLabel("Ricardo Gon\u00E7alves");
		lblRicardoGonalves.setHorizontalAlignment(SwingConstants.CENTER);
		lblRicardoGonalves.setBounds(10, 149, 424, 14);
		getContentPane().add(lblRicardoGonalves);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window.dispose();
			}
		});
		btnOk.setBounds(345, 177, 89, 23);
		getContentPane().add(btnOk);
		
		this.setVisible(true);
	}
}
