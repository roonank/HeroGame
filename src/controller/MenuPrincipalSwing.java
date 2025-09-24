package controller;

import model.personagens.Emun.HeroiEnum;
import model.personagens.Emun.MitologiaEnum;
import model.Torre;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalSwing extends JFrame {
    private JComboBox<HeroiEnum> comboHeroi;
    private JComboBox<MitologiaEnum> comboMitologia;
    private JButton btnIniciar;

    public MenuPrincipalSwing() {
        setTitle("Tower of Trials - Menu Principal");
        setSize(420, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2; c.anchor = GridBagConstraints.CENTER;
        JLabel lblTitulo = new JLabel("=== TOWER OF TRIALS ===", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 16f));
        add(lblTitulo, c);

        c.gridwidth = 1; c.gridy = 1; c.gridx = 0; c.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Escolha seu herói:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START;
        comboHeroi = new JComboBox<>(HeroiEnum.values());
        comboHeroi.setPreferredSize(new Dimension(200, 26));
        comboHeroi.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof HeroiEnum h) setText(h.getNome());
                return comp;
            }
        });
        add(comboHeroi, c);

        c.gridy = 2; c.gridx = 0; c.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Escolha uma mitologia:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START;
        comboMitologia = new JComboBox<>(MitologiaEnum.values());
        comboMitologia.setPreferredSize(new Dimension(200, 26));
        comboMitologia.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof MitologiaEnum m) setText(m.getNome());
                return comp;
            }
        });
        add(comboMitologia, c);

        c.gridy = 3; c.gridx = 0; c.gridwidth = 2; c.anchor = GridBagConstraints.CENTER;
        btnIniciar = new JButton("Iniciar Jogo");
        add(btnIniciar, c);

        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeroiEnum heroi = (HeroiEnum) comboHeroi.getSelectedItem();
                MitologiaEnum mitologia = (MitologiaEnum) comboMitologia.getSelectedItem();
                if (heroi == null || mitologia == null) return;
                // Cria o jogo e inicia a torre
                Jogo jogo = new Jogo();
                jogo.escolherHeroi(heroi);
                jogo.carregarInimigos(mitologia);
                Torre torre = new Torre(jogo);
                // Abre a tela de batalha
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    new BatalhaSwing(torre).setVisible(true);
                });
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipalSwing().setVisible(true);
        });
    }
}
