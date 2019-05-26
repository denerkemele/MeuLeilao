package br.com.senac.cliente;

import static com.oracle.jrockit.jfr.DataType.DOUBLE;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TCPClienteSocket {

    public static void main(String[] args) {
        Socket socketCliente = null;
        try {
            socketCliente = new Socket("localhost", 6789);
            System.out.println("Conectado ao Servidor!");

            //recebe dados de entrada
            DataInputStream entrada = new DataInputStream(
                    socketCliente.getInputStream());

            //recebe dados de saida
            DataOutputStream saida = new DataOutputStream(
                    socketCliente.getOutputStream());

            //grava a string no Stream
            String nome = JOptionPane.showInputDialog("Nome: ");
            saida.writeUTF(nome);
            System.out.println("Enviando " + nome);

            //grava o decimal no Stream
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do Lance"));
            saida.writeDouble(valor);
            System.out.println("Enviando " + valor);

            String mensagem = entrada.readUTF();
            System.out.println("Mensagem recebida: " + mensagem);
            JOptionPane.showMessageDialog(null, mensagem);

        } catch (IOException ex) {
            Logger.getLogger(TCPClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (socketCliente != null) {
                    socketCliente.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(TCPClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
