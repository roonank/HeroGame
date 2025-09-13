package Controller;

public class Torre {
    private final Jogo jogo;
    private Batalha batalhaAtual;

    public Torre(Jogo jogo) {
        this.jogo = jogo;
    }

    public boolean iniciarAndar() {
        batalhaAtual = jogo.proximaBatalha();
        return batalhaAtual != null;
    }

    public Batalha getBatalhaAtual() {
        return batalhaAtual;
    }

    public boolean jogoFinalizado() {
        return jogo.jogoFinalizado();
    }

    public int getAndarAtual() {
        return jogo.getAndarAtual();
    }

    public int getTotalAndares() {
        return jogo.getTotalAndares();
    }

    public Jogo getJogo() {
        return jogo;
    }
}
