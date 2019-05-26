package br.com.senac.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public abstract class TelaResumo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea taResumo;

	public TelaResumo(List<String> resumo) {
		setLayout(new BorderLayout());
		setSize(500, 375);
		setTitle("Leilao");

		taResumo = new JTextArea();
		taResumo.setSize(500, 300);
		this.getContentPane().add(taResumo, BorderLayout.CENTER);
		//API: Lambda
		resumo.forEach(l -> taResumo.append(l.toString()+"\n"));
		
		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout());
		panelButtons.setBorder(BorderFactory.createLineBorder(Color.black));
		this.getContentPane().add(panelButtons, BorderLayout.SOUTH);
			
		JButton buttonSalvar = new JButton("Salvar Resumo");
		buttonSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//darLance();
				salvarResumoButtonClick();
			}

		});
		panelButtons.add(buttonSalvar);
		
		JButton buttonFechar = new JButton("Fechar");
		buttonFechar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//darLance();
				fecharButtonClick();
			}

		});
		panelButtons.add(buttonFechar);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public abstract void salvarResumoButtonClick();
	public abstract void fecharButtonClick();
	
}
