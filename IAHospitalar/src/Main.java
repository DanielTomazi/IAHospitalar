import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

class Paciente {
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public int getIdade() {
        return idade;
    }

    public String getCep() {
        return cep;
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

    @Override
    public String toString() {
        return "Paciente{" +
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", idade=" + idade +
                ", cep='" + cep + '\'' +
                ", endereco='" + endereco + '\'' +
                ", motivoEntrada='" + motivoEntrada + '\'' +
                ", diasInternacao=" + diasInternacao +
                '}';
    }
}

class Leito {
    private int numero;
    private Paciente paciente;

    public Leito(int numero) {
        this.numero = numero;
        this.paciente = null;
    }

    public boolean isDisponivel() {
        return paciente == null;
    }

    public void alocarPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    @Override
    public String toString() {
        return "Leito{" +
                "numero=" + numero +
                ", paciente=" + paciente +
                '}';
    }
}

