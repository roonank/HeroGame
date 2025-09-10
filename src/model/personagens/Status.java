package model.personagens;

public class Status {
    private int vidaMaxima;
    private int vidaAtual;
    private int defesa;
    private int pontosAcao;
    private double chanceCritico;
    private int prioridadeTurno;

    public Status(Atributos atributos) {
        calcularStatus(atributos);
    }

    public void calcularStatus(Atributos atributos) {
        this.vidaMaxima = 50 + (atributos.getForca() * 2) + (atributos.getConstituicao()*5);
        this.defesa = 10 + (atributos.getConstituicao() / 2);
        this.pontosAcao = 6;
        this.chanceCritico = 0.05 + (atributos.getAgilidade() * 0.01);
        this.prioridadeTurno = atributos.getAgilidade();

        if (vidaAtual == 0) {
            this.vidaAtual = vidaMaxima;
        } else if (vidaAtual > vidaMaxima) {
            this.vidaAtual = vidaMaxima;
        }
    }

    public void reduzirVida(int dano) { this.vidaAtual = Math.max(0, this.vidaAtual - dano); }

    public void recuperarVida(int cura){ this.vidaAtual = Math.min(this.vidaMaxima, this.vidaAtual + cura); }

    public int getVidaMaxima() { return vidaMaxima; }
    public int getVidaAtual() { return vidaAtual; }
    public int getDefesa() { return defesa; }
    public int getPontosAcao() { return pontosAcao; }
    public double getChanceCritico() { return chanceCritico; }
    public int getPrioridadeTurno() { return prioridadeTurno; }
    public void setVidaAtual(int vidaAtual) {this.vidaAtual = vidaAtual;}

    public void setPontosAcao(int pontosAcao) { this.pontosAcao = pontosAcao; }
}
