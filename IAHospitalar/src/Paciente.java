import java.time.LocalDate;

public class Paciente {
    private String nome;
    private LocalDate dataNascimento;
    private int idade;
    private String cep;
    private String endereco;
    private String motivoEntrada;
    private int diasInternacao;

    public Paciente(String nome, LocalDate dataNascimento, int idade, String cep, String endereco, String motivoEntrada, int diasInternacao) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.cep = cep;
        this.endereco = endereco;
        this.motivoEntrada = motivoEntrada;
        this.diasInternacao = diasInternacao;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getMotivoEntrada() {
        return motivoEntrada;
    }

    public int getDiasInternacao() {
        return diasInternacao;
    }
}
