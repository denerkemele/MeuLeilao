package br.com.senac.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import br.com.senac.enumerador.TipoFormatoData;
import br.com.senac.model.Item;
import br.com.senac.model.Lance;
import br.com.senac.view.TelaItem;
import br.com.senac.view.TelaLance;
import br.com.senac.view.TelaLeilao;
import br.com.senac.view.TelaResumo;

public class Leilao {

    private Item item;
    public List<Lance> lances;
    private int duracao;
    private TelaLeilao tela;
    private TelaResumo telaResumo;
    private LocalDateTime inicioLeilao;
    private LocalDateTime fimLeilao;
    private List<String> resumo;
    // Camada enumerador
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern(TipoFormatoData.DIA_MES_ANO_HORA24_MINUTO_SEGUNDO.val());

    public Leilao() {

    }

    public void iniciarLeilao() {
        //Obter Item da View
        TelaItem telaItem = new TelaItem();

        if (telaItem.obterItem()) {
            item = new Item(telaItem.getItem(), telaItem.getValorMinimo());
            //converte para minutos
            duracao = telaItem.getDuracao() * 60;

            //API: Collections 
            //Os dados são armazendos em lista em array
            lances = new ArrayList<Lance>();

            //API: LocalDateTime
            //compara com data atual
            inicioLeilao = LocalDateTime.now();

            // APIs: Threads e Lambda
            //A execução e feita direto na declaração
            Runnable cronometro = () -> {
                while (duracao > 0) {
                    //API: Exceptions
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    duracao--;
                    tela.setDuracao(duracao);
                }
                finalizarLeilao();
            };

            // Inicial Leilao
            tela = new TelaLeilao(item) {

                @Override

                //inicia o cronometro
                //liga o servidor 
                public void darLanceButtonClick() {
                    //new MeuThread(duracao,item).start();

                    TCPServerSocket();
                    Thread thread = new Thread(cronometro, "Cronometro Leilao");
                    thread.start();

                }

            };

            tela.setDuracao(duracao);
            tela.setVisible(true);
        }
    }

    String nome;
    Double valor;

    public String TCPServerSocket() {
        
        //executa uma thread no servidor 
        //concorrendo com Thread do cronometro
        new Thread() {

            @Override
            public void run() {

                ServerSocket serverSocket = null;
                Socket listenSocket = null;

                try {
                    //metodo construtor 
                    serverSocket = new ServerSocket(6789);

                    //mantem o servidor rodando sempre
                    while (true) {

                        System.out.println("Aguardando conexao...");

                        listenSocket = serverSocket.accept();
                        System.out.println("Cliente conectado!!");

                        DataInputStream entrada = new DataInputStream(
                                listenSocket.getInputStream());
                        DataOutputStream saida = new DataOutputStream(
                                listenSocket.getOutputStream());

                        //recebe dados do cliente tipo String
                        String nome = entrada.readUTF();
                        System.out.println("Recebido " + nome);

                        //recebe dados do cliente tipo Double
                        Double valor = entrada.readDouble();
                        System.out.println("Recebido " + valor);

                        //dados são enviados para gerenciar o leilão
                        darLance(nome, valor);

                        // Leilao prog = new Leilao();  
                        // prog.darLance(nome,valor); 
                        // }
                        // return var;
                    }

                } catch (IOException ex) {
                }

            }
        }.start();
        return null;
    }

    ////////////
    private void finalizarLeilao() {
        System.out.println("Leilao acabou!");
        tela.setVisible(false);
        tela.dispose();
        //API: LocalDateTime
        fimLeilao = LocalDateTime.now();

        resumoLeilao();
    }

    private void resumoLeilao() {
        resumo = new ArrayList<>();

        resumo.add("Leilao de " + item.getDescricao());
        //API: LocalDateTime
        resumo.add("Iniciado em " + inicioLeilao.format(formatador));
        resumo.add("Finalizado em " + fimLeilao.format(formatador));

        long duracaoEfetiva = ChronoUnit.SECONDS.between(inicioLeilao, fimLeilao);
        long duracaoEfetivaSegundos = duracaoEfetiva % 60;
        duracaoEfetiva = duracaoEfetiva / 60;
        long duracaoEfetivaMinutos = duracaoEfetiva % 60;
        long duracaoEfetivaHoras = duracaoEfetiva / 60;
        resumo.add("Duracao: " + String.format("%02d:%02d:%02d",
                duracaoEfetivaHoras,
                duracaoEfetivaMinutos,
                duracaoEfetivaSegundos));

        Lance vencedor = getVencedor();

        //retorna se houve ou não um cliente que deu lance
        if (vencedor == null) {
            resumo.add("Nao houve lance vencedor!");
        } else {
            resumo.add("Lance vencedor:");
            resumo.add(vencedor.toString());
        }

        //API: Lambda
        resumo.forEach(System.out::println);
        telaResumo = new TelaResumo(resumo) {

            @Override
            public void salvarResumoButtonClick() {
                salvarResumo();
            }

            @Override
            public void fecharButtonClick() {
                telaResumo.setVisible(false);
                telaResumo.dispose();
            }
        };
        telaResumo.setVisible(true);
    }

    public void darLance(String nome, double valor) {

        //toda vez que executa a função ele cria uma Thread
        //para cada usuario que executa
        new Thread() {

            @Override
            public void run() {

                Lance lance = new Lance(nome, valor);
                addLance(lance);
                System.out.println(lance.toString());

                // }
            }
        }.start();

    }

    public void addLance(Lance lance) {
        lances.add(lance);
        tela.addLanceHistorico(lance);

        //Ordena lista em ordem decescente
        Collections.sort(lances, Collections.reverseOrder());

        Lance vencedor = getVencedor();
        if (vencedor != null) {
            tela.setVencedor(vencedor);
        }
    }

    private Lance getVencedor() {
        //API: Stream
        Optional<Lance> vencedor = lances.stream().filter(l -> l.getValor() >= item.getValorMinimo()).findFirst();
        return (vencedor.isPresent() ? vencedor.get() : null);
    }

    private void salvarResumo() {
        System.out.println("Salvar Resumo");
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            //APIs: IO e Exceptions
            try {
                File file = fileChooser.getSelectedFile();
                FileWriter fw = new FileWriter(file);
                PrintWriter out = new PrintWriter(fw);
                resumo.forEach(l -> out.write(l + "\n"));
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
