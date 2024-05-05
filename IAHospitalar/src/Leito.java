public class Leito {
    private int numero;
    private Paciente paciente;
    private int diasOcupado;

    public Leito(int numero) {
        this.numero = numero;
        this.paciente = null;
        this.diasOcupado = 0;
    }

    public boolean isDisponivel() {
        return paciente == null;
    }

    public void alocarPaciente(Paciente paciente) {
        this.paciente = paciente;
        this.diasOcupado = paciente.getDiasInternacao();
    }

    public int getDiasOcupado() {
        return diasOcupado;
    }

    @Override
    public String toString() {
        if (paciente != null) {
            return "Leito número: " + numero +
                    "\n  Paciente: " + paciente.getNome() +
                    "\n  Idade: " + paciente.getIdade() +
                    "\n  Endereço: " + paciente.getEndereco() +
                    "\n  Motivo da entrada: " + paciente.getMotivoEntrada() +
                    "\n  Dias Internado: " + diasOcupado;
        } else {
            return "Leito número: " + numero + ", Paciente: Nenhum";
        }
    }
}
