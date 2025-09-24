package controller;

import model.habilidades.Habilidade;
import model.personagens.Heroi;
import model.Torre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BatalhaSwing extends JFrame {
    private final Torre torre;
    private Batalha batalha;
    private final Heroi heroi;
    private final GerenciadorDistribuicaoPontosSwing gerenciador = new GerenciadorDistribuicaoPontosSwing();

    private final JLabel lblTituloAndar = new JLabel("", SwingConstants.CENTER);
    private final JTextArea txtStatus = new JTextArea(3, 40);
    private final JTextArea txtLog = new JTextArea(12, 40);

    // Barras de vida e labels
    private final JLabel lblHeroiNome = new JLabel();
    private final JLabel lblInimigoNome = new JLabel();
    private final JLabel lblHeroiValores = new JLabel();
    private final JLabel lblInimigoValores = new JLabel();
    private final JProgressBar barraHeroi = new JProgressBar();
    private final JProgressBar barraInimigo = new JProgressBar();

    private final JButton btnAtacar = new JButton("Atacar");
    private final JButton btnDefender = new JButton("Defender");
    private final JButton btnFugir = new JButton("Fugir");
    private final JComboBox<Habilidade> comboHabilidades = new JComboBox<>();
    private final JButton btnUsarHabilidade = new JButton("Usar Habilidade");

    public BatalhaSwing(Torre torre) {
        this.torre = torre;
        this.heroi = torre.getJogo().getJogador();
        setTitle("Tower of Trials - Batalha");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);

        montarLayout();
        conectarAcoes();

        carregarAndar();
    }

    private void montarLayout() {
        setLayout(new BorderLayout(8, 8));
        lblTituloAndar.setFont(lblTituloAndar.getFont().deriveFont(Font.BOLD, 18f));
        add(lblTituloAndar, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(8, 8));

        // Painel de status com barras de vida (vertical)
        JPanel painelStatus = new JPanel(new GridBagLayout());
        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(4, 6, 4, 6);
        s.gridx = 0; s.gridy = 0; s.anchor = GridBagConstraints.LINE_START;
        lblHeroiNome.setFont(lblHeroiNome.getFont().deriveFont(Font.BOLD));
        painelStatus.add(lblHeroiNome, s);
        s.gridx = 1; s.anchor = GridBagConstraints.LINE_END;
        painelStatus.add(lblHeroiValores, s);
        s.gridx = 0; s.gridy = 1; s.gridwidth = 2; s.fill = GridBagConstraints.HORIZONTAL; s.weightx = 1.0;
        barraHeroi.setStringPainted(true);
        painelStatus.add(barraHeroi, s);

        s.gridwidth = 1; s.fill = GridBagConstraints.NONE; s.weightx = 0;
        s.gridx = 0; s.gridy = 2; s.anchor = GridBagConstraints.LINE_START;
        lblInimigoNome.setFont(lblInimigoNome.getFont().deriveFont(Font.BOLD));
        painelStatus.add(lblInimigoNome, s);
        s.gridx = 1; s.anchor = GridBagConstraints.LINE_END;
        painelStatus.add(lblInimigoValores, s);
        s.gridx = 0; s.gridy = 3; s.gridwidth = 2; s.fill = GridBagConstraints.HORIZONTAL; s.weightx = 1.0;
        barraInimigo.setStringPainted(true);
        painelStatus.add(barraInimigo, s);

        centro.add(painelStatus, BorderLayout.NORTH);

        // Área de log
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);
        txtLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        centro.add(new JScrollPane(txtLog), BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        // Painel de ações
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridx = 0; c.gridy = 0;
        painelAcoes.add(btnAtacar, c);
        c.gridx = 1;
        painelAcoes.add(btnDefender, c);
        c.gridx = 2;
        painelAcoes.add(btnFugir, c);

        c.gridx = 0; c.gridy = 1; c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        painelAcoes.add(comboHabilidades, c);
        c.gridx = 2; c.gridy = 1; c.gridwidth = 1; c.fill = GridBagConstraints.NONE; c.weightx = 0;
        painelAcoes.add(btnUsarHabilidade, c);

        add(painelAcoes, BorderLayout.SOUTH);

        // Popular habilidades
        atualizarListaHabilidades();
    }

    private void conectarAcoes() {
        btnAtacar.addActionListener(this::onAtacar);
        btnDefender.addActionListener(this::onDefender);
        btnFugir.addActionListener(this::onFugir);
        btnUsarHabilidade.addActionListener(this::onUsarHabilidade);
    }

    private void carregarAndar() {
        boolean ok = torre.iniciarAndar();
        if (!ok) {
            finalizarJogo();
            return;
        }
        batalha = torre.getBatalhaAtual();

        // Evolui inimigos a partir do segundo andar
        if (torre.getAndarAtual() > 1) {
            gerenciador.evoluirInimigos(torre.getJogo().getInimigos());
        }

        lblTituloAndar.setText("Andar " + torre.getAndarAtual() + " de " + torre.getTotalAndares());

        // Configurar nomes e máximos das barras
        lblHeroiNome.setText(batalha.getJogador().getNome());
        lblInimigoNome.setText(batalha.getInimigo().getNome());
        barraHeroi.setMaximum(batalha.getJogador().getVidaMaxima());
        barraInimigo.setMaximum(batalha.getInimigo().getVidaMaxima());

        atualizarStatusELog(true);
        atualizarHabilitadoPorTurno();
        if (!batalha.isJogadorNaVez()) executarTurnoInimigoAposAtraso();
    }

    private void onAtacar(ActionEvent e) {
        executarAcaoJogador(Batalha.AcaoBatalha.ATACAR, null);
    }

    private void onDefender(ActionEvent e) {
        executarAcaoJogador(Batalha.AcaoBatalha.DEFENDER, null);
    }

    private void onFugir(ActionEvent e) {
        executarAcaoJogador(Batalha.AcaoBatalha.FUGIR, null);
    }

    private void onUsarHabilidade(ActionEvent e) {
        Object sel = comboHabilidades.getSelectedItem();
        if (!(sel instanceof Habilidade)) {
            JOptionPane.showMessageDialog(this, "Selecione uma habilidade.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Habilidade h = (Habilidade) sel;
        executarAcaoJogador(Batalha.AcaoBatalha.USAR_HABILIDADE, h);
    }

    private void executarAcaoJogador(Batalha.AcaoBatalha acao, Habilidade habilidade) {
        if (batalha.isBatalhaFinalizada()) return;
        if (!batalha.isJogadorNaVez()) return;

        setControlesAtivos(false);
        batalha.executarTurno(acao, habilidade);
        atualizarStatusELog(false);

        if (batalha.isBatalhaFinalizada()) {
            tratarFimDaBatalha();
        } else {
            executarTurnoInimigoAposAtraso();
        }
    }

    private void executarTurnoInimigoAposAtraso() {
        setControlesAtivos(false);
        new Timer(700, ev -> {
            ((Timer) ev.getSource()).stop();
            if (!batalha.isBatalhaFinalizada() && !batalha.isJogadorNaVez()) {
                batalha.executarTurno(null, null);
                atualizarStatusELog(false);
                if (batalha.isBatalhaFinalizada()) {
                    tratarFimDaBatalha();
                } else {
                    setControlesAtivos(true);
                }
            }
        }).start();
    }

    private void tratarFimDaBatalha() {
        setControlesAtivos(false);
        atualizarStatusELog(false);
        boolean vitoria = batalha.isJogadorVencedor();
        mostrarOverlayResultado(vitoria);
    }

    private void mostrarOverlayResultado(boolean vitoria) {
        String msg = vitoria ? "Vitória! Seguindo para o próximo nível..." : "Derrota...";
        Color cor = vitoria ? new Color(0x2ECC71) : new Color(0xE74C3C);
        JPanel overlay = criarOverlay(msg, cor);
        setGlassPane(overlay);
        overlay.setVisible(true);

        if (!vitoria) {
            tremerJanela();
        }

        int duracao = vitoria ? 1800 : 1800;
        new Timer(duracao, e -> {
            ((Timer) e.getSource()).stop();
            overlay.setVisible(false);
            if (vitoria) {
                if (heroi.getPontosHabilidade() > 0) {
                    gerenciador.evoluirHeroi(heroi, this);
                    atualizarListaHabilidades();
                }
                if (torre.jogoFinalizado()) {
                    finalizarJogo();
                } else {
                    carregarAndar();
                    setControlesAtivos(true);
                }
            } else {
                finalizarJogo();
            }
        }).start();
    }

    private JPanel criarOverlay(String mensagem, Color corFundo) {
        JPanel gp = new JPanel(new GridBagLayout());
        gp.setOpaque(true);
        gp.setBackground(corFundo);
        JLabel lbl = new JLabel(mensagem, SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 28f));
        gp.add(lbl, new GridBagConstraints());
        return gp;
    }

    private void tremerJanela() {
        Point origem = getLocation();
        int[] offsets = {0, -10, 10, -8, 8, -6, 6, -4, 4, -2, 2, 0};
        final int[] i = {0};
        Timer t = new Timer(20, e -> {
            setLocation(origem.x + offsets[i[0]], origem.y);
            i[0]++;
            if (i[0] >= offsets.length) {
                ((Timer) e.getSource()).stop();
                setLocation(origem);
            }
        });
        t.start();
    }

    private void finalizarJogo() {
        String msg = batalha != null && batalha.isJogadorVencedor() ?
                "Parabéns! Você concluiu a torre." :
                "Jogo finalizado.";
        int opt = JOptionPane.showOptionDialog(this, msg + "\nDeseja jogar novamente?", "Tower of Trials",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"Novo Jogo", "Sair"}, "Novo Jogo");
        if (opt == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> new MenuPrincipalSwing().setVisible(true));
        } else {
            dispose();
        }
    }

    private void atualizarStatusELog(boolean limparLog) {
        // Atualizar barras de vida
        atualizarBarraVida(barraHeroi, lblHeroiValores,
                batalha.getJogador().getVidaAtual(), batalha.getJogador().getVidaMaxima());
        atualizarBarraVida(barraInimigo, lblInimigoValores,
                batalha.getInimigo().getVidaAtual(), batalha.getInimigo().getVidaMaxima());

        // Atualizar log
        if (limparLog) txtLog.setText("");
        for (String l : batalha.getLogBatalha()) {
            txtLog.append(limparAnsi(l));
            txtLog.append("\n");
        }
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    private void atualizarBarraVida(JProgressBar barra, JLabel lblValores, int atual, int max) {
        if (max <= 0) max = 1;
        atual = Math.max(0, Math.min(atual, max));
        barra.setMaximum(max);
        barra.setValue(atual);
        int pct = (int) Math.round((atual * 100.0) / max);
        barra.setString(atual + "/" + max + " (" + pct + "%)");
        lblValores.setText(barra.getString());
        barra.setForeground(corPorPercentual(pct));
    }

    private Color corPorPercentual(int pct) {
        // 75-100: verde | 50-74: amarelo | 25-49: laranja | 0-24: vermelho
        if (pct >= 75) return new Color(0x2ECC71);         // Verde
        if (pct >= 50) return new Color(0xF1C40F);         // Amarelo
        if (pct >= 25) return new Color(0xE67E22);         // Laranja
        return new Color(0xE74C3C);                        // Vermelho
    }

    private void atualizarHabilitadoPorTurno() {
        setControlesAtivos(batalha.isJogadorNaVez() && !batalha.isBatalhaFinalizada());
    }

    private void setControlesAtivos(boolean ativo) {
        btnAtacar.setEnabled(ativo);
        btnDefender.setEnabled(ativo);
        btnFugir.setEnabled(ativo);
        btnUsarHabilidade.setEnabled(ativo && comboHabilidades.getItemCount() > 0);
        comboHabilidades.setEnabled(ativo && comboHabilidades.getItemCount() > 0);
    }

    private void atualizarListaHabilidades() {
        comboHabilidades.removeAllItems();
        List<Habilidade> habilidades = heroi.getHabilidades();
        if (habilidades != null) {
            for (Habilidade h : habilidades) comboHabilidades.addItem(h);
        }
        comboHabilidades.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Habilidade h) setText(h.getNome() + " (Custo: " + h.getCustoMana() + ")");
                return comp;
            }
        });
    }

    private static String limparAnsi(String s) {
        if (s == null) return "";
        return s.replaceAll("\\u001B\\[[;\\d]*m", "");
    }
}
