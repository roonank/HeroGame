package view;

import Controller.*;
import model.habilidades.Habilidade;
import model.personagens.Heroi;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private Torre torre;
    private Batalha batalhaAtual;
    private JTextArea logArea;
    private JLabel statusLabel;

    public MainFrame() {
        setTitle("Torre dos Deuses");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        statusLabel = new JLabel("Bem-vindo à Torre!");
        add(statusLabel, BorderLayout.NORTH);

        JPanel botoes = new JPanel();
        JButton btnAtacar = new JButton("Atacar");
        JButton btnDefender = new JButton("Defender");
        JButton btnHabilidade = new JButton("Habilidade");
        JButton btnFugir = new JButton("Fugir");
        botoes.add(btnAtacar);
        botoes.add(btnDefender);
        botoes.add(btnHabilidade);
        botoes.add(btnFugir);
        add(botoes, BorderLayout.SOUTH);

        iniciarJogo();

        // Listeners para os botões de ação
        btnAtacar.addActionListener(e -> executarTurno(Batalha.AcaoBatalha.ATACAR, null));
        btnDefender.addActionListener(e -> executarTurno(Batalha.AcaoBatalha.DEFENDER, null));
        btnFugir.addActionListener(e -> executarTurno(Batalha.AcaoBatalha.FUGIR, null));

        btnHabilidade.addActionListener(e -> {
            // Acessa o herói atual da batalha
            Heroi heroi = (Heroi) torre.getJogo().getJogador();

            // Obtém a lista de habilidades do herói
            List<Habilidade> habilidades = heroi.getHabilidades();

            if (habilidades.isEmpty()) {
                logArea.append("  Você não possui habilidades para usar!\n");
            } else {
                // Converte a lista de habilidades para um array de nomes para o JComboBox
                Object[] opcoesHabilidades = habilidades.stream()
                        .map(Habilidade::getNome)
                        .toArray();

                // Abre uma janela de diálogo para o jogador escolher a habilidade
                Object escolha = JOptionPane.showInputDialog(
                        MainFrame.this,
                        "Escolha uma habilidade:",
                        "Usar Habilidade",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opcoesHabilidades,
                        opcoesHabilidades[0]
                );

                // Se o jogador selecionou uma habilidade e não cancelou
                if (escolha != null) {
                    String nomeHabilidadeSelecionada = (String) escolha;

                    // Encontra a habilidade correspondente na lista
                    Habilidade habilidadeSelecionada = habilidades.stream()
                            .filter(h -> h.getNome().equals(nomeHabilidadeSelecionada))
                            .findFirst()
                            .orElse(null);

                    if (habilidadeSelecionada != null) {
                        // Chama o método executarTurno com os dois argumentos
                        executarTurno(Batalha.AcaoBatalha.USAR_HABILIDADE, habilidadeSelecionada);
                    }
                }
            }
        });
    }

    private void iniciarJogo() {
        Jogo jogo = new Jogo();
        String[] herois = {"Aquiles", "Hércules", "Perseu", "Ragnar", "Gilgamesh"};
        int escolha = JOptionPane.showOptionDialog(this, "Escolha seu herói:", "Heróis",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, herois, herois[0]);
        jogo.escolherHeroi(escolha + 1);

        jogo.carregarInimigos(1);
        torre = new Torre(jogo);
        iniciarNovoAndar();
    }

    private void iniciarNovoAndar() {
        if (torre.iniciarAndar()) {
            batalhaAtual = torre.getBatalhaAtual();
            atualizarTela();
        } else {
            JOptionPane.showMessageDialog(this, "Você conquistou a Torre!");
        }
    }

    private void executarTurno(Batalha.AcaoBatalha acao, Habilidade habilidade) {
        if (batalhaAtual == null) return;

        batalhaAtual.executarTurno(acao, habilidade);

        atualizarTela();

        if (batalhaAtual.isBatalhaFinalizada()) {
            if (batalhaAtual.isJogadorVencedor()) {
                JOptionPane.showMessageDialog(this, "Vitória! Subindo...");
                iniciarNovoAndar();
            } else {
                JOptionPane.showMessageDialog(this, "Você foi derrotado!");
                // Lógica para encerrar o jogo após a derrota
            }
        }
    }

    private void atualizarTela() {
        if (torre != null && batalhaAtual != null) {
            String nomeCombatenteAtual = batalhaAtual.isJogadorNaVez() ? batalhaAtual.getJogador().getNome() : batalhaAtual.getInimigo().getNome();

            statusLabel.setText(
                    "Andar " + torre.getAndarAtual() + "/" + torre.getTotalAndares() +
                            " | Vez de: " + nomeCombatenteAtual +
                            " | " + batalhaAtual.getStatusVida()
            );
            logArea.setText(String.join("\n", batalhaAtual.getLogBatalha()));
        }
    }
}