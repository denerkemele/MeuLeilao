package br.com.senac.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import br.com.senac.model.Item;
import br.com.senac.model.Lance;

public abstract class TelaLeilao extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblCronometro;
	private JTextArea taHistorico;
	private JLabel lblVencedor;
	private JLabel lblLanceVencedor;
	private NumberFormat formatter = new DecimalFormat("#0.00");
	
	private JLabel createLabelInfo(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		//label.setBorder(BorderFactory.createBevelBorder(1));
		label.setBorder(BorderFactory.createLoweredBevelBorder());
		return label;
	}
	
	public TelaLeilao(Item item) {
		setLayout(new BorderLayout());
		setSize(500, 375);
		setTitle("Leilao");

		JPanel panelMain = new JPanel();
		panelMain.setLayout(new GridLayout(0,2));
		panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(panelMain, BorderLayout.NORTH);
		
		Font font = new Font(Font.SERIF, Font.BOLD, 15);
		
		panelMain.add(new JLabel("Item do Leilao:"));
		panelMain.add(createLabelInfo(item.getDescricao()));
		
		panelMain.add(new JLabel("Lance Minimo:"));
		panelMain.add(createLabelInfo(formatter.format(item.getValorMinimo())));
		
		panelMain.add(new JLabel("Vencedor:"));
		lblVencedor = createLabelInfo("");

		panelMain.add(lblVencedor);
		panelMain.add(new JLabel("Lance Vencedor:"));
		lblLanceVencedor = createLabelInfo("");
		panelMain.add(lblLanceVencedor);
		
		lblCronometro = new JLabel("00:00");
		lblCronometro.setBorder(BorderFactory.createBevelBorder(1));
		lblCronometro.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		lblCronometro.setForeground(Color.BLACK);
		lblCronometro.setHorizontalAlignment(SwingConstants.CENTER);
		panelMain.add(lblCronometro);
		
		JButton buttonLance = new JButton("Iniciar Leilão");
		buttonLance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//darLance();
				darLanceButtonClick();
			}

		});
                panelMain.add(buttonLance);
		
		
		JPanel panelHistorico = new JPanel();		
		this.getContentPane().add(panelHistorico, BorderLayout.CENTER);
		panelHistorico.setSize(500, 200);
		panelHistorico.setLayout(new BorderLayout());
		panelHistorico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelHistorico.add(new JLabel("Historico de lances"), BorderLayout.NORTH);
		taHistorico = new JTextArea();
		taHistorico.setSize(500, 300);
		panelHistorico.add(taHistorico, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public abstract void darLanceButtonClick();
	
	public void setDuracao(int segundos) {
		lblCronometro.setForeground(segundos <= 20? Color.RED : Color.BLACK);
		int minutos = segundos / 60;
		segundos = segundos % 60;
		lblCronometro.setText(String.format("%02d:%02d", minutos, segundos));
	}
	
	public void setVencedor(Lance lance) {
		lblVencedor.setText(lance.getCompetidor());
		lblLanceVencedor.setText(formatter.format(lance.getValor()));
	}
	
	public void addLanceHistorico(Lance lance) {
		taHistorico.setText(lance.toString() + "\n" + taHistorico.getText());
	}
	
}
