package br.com.senac.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaLance extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField fieldCompetidor;
	private JFormattedTextField fieldValor;
	private boolean modalResult = false;

	public TelaLance() {
		this.setModal(true);
		this.setSize(500, 180);
		this.setTitle("Efetuar Lance");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout());

		JPanel panelMain = new JPanel();
		this.getContentPane().add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel panelLabels = new JPanel();
		panelMain.add(panelLabels, BorderLayout.LINE_START);
		panelLabels.setLayout(new GridLayout(0,1));
		panelLabels.add(new JLabel("Competidor  "));
		panelLabels.add(new JLabel("Valor Lance  "));
		
		JPanel panelFields = new JPanel();
		panelMain.add(panelFields, BorderLayout.CENTER);
		panelFields.setLayout(new GridLayout(0,1));
		fieldCompetidor = new JTextField();
		panelFields.add(fieldCompetidor);
		NumberFormat valorFormat = NumberFormat.getNumberInstance();
		valorFormat.setMinimumFractionDigits(2);
		fieldValor = new JFormattedTextField(valorFormat);
		fieldValor.setValue(new Double(0));
		panelFields.add(fieldValor);
		
		JPanel panelButtons = new JPanel();
		this.add(panelButtons, BorderLayout.PAGE_END);
		panelButtons.setLayout(new FlowLayout());
		panelButtons.setBorder(BorderFactory.createLineBorder(Color.black));
		JButton buttonOk = new JButton("OK");
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setModalResult(true);
			}
		});
		panelButtons.add(buttonOk);
		JButton buttonCancel = new JButton("Cancelar");
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setModalResult(false);
			}
		});
		panelButtons.add(buttonCancel);
	}
	
	private void setModalResult(boolean value) {
		this.modalResult = value;
		this.setVisible(false);
	}
	
	public boolean efetuarLance() {
		this.setVisible(true);
		this.dispose();
		return modalResult;
	}
	
	public String getCompetidor() {
		return fieldCompetidor.getText();
	}
	
	public double getLance() {
		return ((Number)fieldValor.getValue()).doubleValue();
	}
	
}
