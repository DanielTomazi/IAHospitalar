import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class HospitalSystem {

    private List<Leito> leitos;
    private List<Paciente> pacientes;

    public HospitalSystem() {
        this.leitos = new ArrayList<>();
        this.pacientes = new ArrayList<>();
    }

    public void iniciarSistema() {
        Scanner scanner = new Scanner(System.in);

        // Gerar leitos hospitalares
        gerarLeitos(1);

        // Cadastrar pacientes
        cadastrarPacientes(scanner);

        // Determinar alocação de pacientes
        determinarAlocacaoPacientes();

        // Exibir informações dos leitos e pacientes
        exibirInformacoes();
    }

    private void gerarLeitos(int quantidade) {
        for (int i = 1; i <= quantidade; i++) {
            leitos.add(new Leito(i));
        }
    }

    private void cadastrarPacientes(Scanner scanner) {
        boolean cadastrarNovoPaciente = true;
        while (cadastrarNovoPaciente) {
            Paciente novoPaciente = cadastrarPaciente(scanner);
            if (novoPaciente != null) {
                pacientes.add(novoPaciente);
            }

            System.out.print("Deseja cadastrar outro paciente? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (!resposta.equals("s")) {
                cadastrarNovoPaciente = false;
            }
        }
    }

    private Paciente cadastrarPaciente(Scanner scanner) {
        System.out.println("Cadastro de Paciente");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        LocalDate dataNascimento = null;
        boolean dataValida = false;
        while (!dataValida) {
            try {
                System.out.print("Data de Nascimento (no formato DD-MM-AAAA): ");
                String dataNascimentoStr = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
                dataValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Por favor, insira a data no formato DD-MM-AAAA.");
            }
        }
        // Calculando a idade com base na data de nascimento
        LocalDate hoje = LocalDate.now();
        int idade = Period.between(dataNascimento, hoje).getYears();

        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        // Obtendo o endereço com base no CEP
        String endereco = obterEnderecoPorCEP(cep);

        System.out.println("Motivo da entrada no hospital:");
        System.out.println("1 - Internação cirúrgica");
        System.out.println("2 - Exames médicos com necessidade de internação");
        System.out.println("3 - Exames sem necessidade de internação");
        System.out.println("4 - Procedimento cirúrgico com alta no mesmo dia");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer de entrada

        int diasInternacao = determinarDiasInternacao(opcao);

        String motivoEntrada = "";
        switch (opcao) {
            case 1:
                motivoEntrada = "Internação cirúrgica";
                break;
            case 2:
                motivoEntrada = "Exames médicos com necessidade de internação";
                break;
            case 3:
                motivoEntrada = "Exames sem necessidade de internação";
                diasInternacao = 0; // Sem necessidade de leito
                break;
            case 4:
                motivoEntrada = "Procedimento cirúrgico com alta no mesmo dia";
                break;
        }

        // Verificar se há leitos disponíveis
        boolean leitosDisponiveis = leitos.stream().anyMatch(Leito::isDisponivel);

        if (!leitosDisponiveis) {
            System.out.println("Não há leitos disponíveis, o sistema irá verificar a locação e a indicação é que o paciente seja alocado ao obtermos liberação de leitos no sistema. O paciente " + nome + " não pode ser cadastrado.");
            return null;
        }

        return new Paciente(nome, dataNascimento, idade, cep, endereco, motivoEntrada, diasInternacao);
    }

    private int determinarDiasInternacao(int opcao) {
        switch (opcao) {
            case 1:
                return 5; // Internação cirúrgica
            case 2:
                return 3; // Exames médicos com necessidade de internação
            case 3:
                return 1; // Exames sem necessidade de internação
            case 4:
                return 1; // Procedimento cirúrgico com alta no mesmo dia
            default:
                return 0;
        }
    }

    private void determinarAlocacaoPacientes() {
        for (Paciente paciente : pacientes) {
            determinarAlocacaoPaciente(paciente);
        }
    }

    private void determinarAlocacaoPaciente(Paciente paciente) {
        // Lógica para determinar a alocação do paciente com base no motivo de entrada
        if (paciente.getDiasInternacao() > 0) {
            boolean leitosDisponiveis = leitos.stream().anyMatch(Leito::isDisponivel);
            if (leitosDisponiveis) {
                Leito leitoDisponivel = leitos.stream().filter(Leito::isDisponivel).findFirst().get();
                leitoDisponivel.alocarPaciente(paciente);
                System.out.println("Paciente " + paciente.getNome() + " alocado no leito " + leitoDisponivel.getNumero());
            } else {
                System.out.println("Não há leitos disponíveis para o paciente " + paciente.getNome());
            }
        } else {
            System.out.println("Paciente " + paciente.getNome() + " não precisa de leito.");
        }
    }

    private void exibirInformacoes() {
        // Exibir informações dos leitos e pacientes
        System.out.println("\n--- Informações dos Leitos ---");
        for (Leito leito : leitos) {
            System.out.println(leito.toString());
        }
    }

    private String obterEnderecoPorCEP(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        StringBuilder endereco = new StringBuilder();
        try {
            URL urlObj = new URL(url);
            Scanner scanner = new Scanner(urlObj.openStream());
            while (scanner.hasNextLine()) {
                endereco.append(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return endereco.toString();
    }

    public static void main(String[] args) {
        HospitalSystem hospitalSystem = new HospitalSystem();
        hospitalSystem.iniciarSistema();
    }
}
