package Gerencia;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Biblioteca {
    private static final ArrayList<Livros> listaLivros = new ArrayList<>();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean executando = true;

            while (executando) {
                exibirMenu();
                int opcao = lerInteiro(scanner, "Escolha uma opção: ", 0, 4);

                switch (opcao) {
                    case 1:
                        adicionarLivro(scanner);
                        break;
                    case 2:
                        excluirLivro(scanner);
                        break;
                    case 3:
                        listarLivros();
                        break;
                    case 4:
                        procurarLivro(scanner);
                        break;
                    case 0:
                        executando = false;
                        System.out.println("Programa encerrado.");
                        break;
                    default:
                        // A validação de lerInteiro impede que esta opção seja alcançada.
                        break;
                }
                System.out.println();
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("=== Biblioteca ===");
        System.out.println("1 - Adicionar livro");
        System.out.println("2 - Excluir livro");
        System.out.println("3 - Listar livros");
        System.out.println("4 - Procurar livro");
        System.out.println("0 - Sair");
    }

    private static void adicionarLivro(Scanner scanner) {
        Livros novoLivro = new Livros();

        System.out.println("\n=== Cadastro de livro ===");
        novoLivro.nome = lerTextoObrigatorio(scanner, "Título: ");
        novoLivro.autor = lerTextoObrigatorio(scanner, "Autor: ");

        System.out.print("Descrição (opcional): ");
        novoLivro.descricao = scanner.nextLine().trim();
        novoLivro.quantidade = lerInteiro(scanner, "Quantidade em estoque: ", 0, Integer.MAX_VALUE);
        novoLivro.valor = lerValor(scanner, "Preço (use vírgula ou ponto): R$ ");

        listaLivros.add(novoLivro);
        System.out.println("Livro cadastrado com sucesso.");
    }

    private static void excluirLivro(Scanner scanner) {
        if (listaLivros.isEmpty()) {
            System.out.println("Não há livros cadastrados para excluir.");
            return;
        }

        System.out.println("\n=== Excluir livro ===");
        for (int i = 0; i < listaLivros.size(); i++) {
            Livros livro = listaLivros.get(i);
            System.out.printf("%d - %s (autor: %s)%n", i + 1, livro.nome, livro.autor);
        }

        int numero = lerInteiro(scanner, "Digite o número do livro que deseja excluir: ", 1, listaLivros.size());
        Livros removido = listaLivros.remove(numero - 1);
        System.out.println("Livro \"" + removido.nome + "\" excluído com sucesso.");
    }

    private static void listarLivros() {
        if (listaLivros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        System.out.println("\n=== Livros cadastrados ===");
        for (int i = 0; i < listaLivros.size(); i++) {
            imprimirLivro(listaLivros.get(i), i + 1);
        }
    }

    private static void procurarLivro(Scanner scanner) {
        if (listaLivros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado para pesquisar.");
            return;
        }

        System.out.print("\nDigite o título, autor ou descrição para pesquisar: ");
        String consulta = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
        if (consulta.isEmpty()) {
            System.out.println("Digite algum texto para fazer a pesquisa.");
            return;
        }

        int encontrados = 0;
        for (int i = 0; i < listaLivros.size(); i++) {
            Livros livro = listaLivros.get(i);
            if (contemTexto(livro.nome, consulta)
                    || contemTexto(livro.autor, consulta)
                    || contemTexto(livro.descricao, consulta)) {
                imprimirLivro(livro, i + 1);
                encontrados++;
            }
        }

        if (encontrados == 0) {
            System.out.println("Nenhum livro encontrado para \"" + consulta + "\".");
        } else {
            System.out.println(encontrados + (encontrados == 1 ? " livro encontrado." : " livros encontrados."));
        }
    }

    private static void imprimirLivro(Livros livro, int numero) {
        System.out.println("\nLivro " + numero);
        System.out.println("Título: " + livro.nome);
        System.out.println("Autor: " + livro.autor);
        System.out.println("Descrição: " + (livro.descricao.isEmpty() ? "Não informada" : livro.descricao));
        System.out.println("Quantidade: " + livro.quantidade);
        System.out.printf(Locale.forLanguageTag("pt-BR"), "Preço: R$ %.2f%n", livro.valor);
    }

    private static String lerTextoObrigatorio(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Este campo é obrigatório.");
        }
    }

    private static int lerInteiro(Scanner scanner, String mensagem, int minimo, int maximo) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // Mostra a mesma orientação para entradas que não são números inteiros.
            }
            System.out.printf("Digite um número inteiro entre %d e %d.%n", minimo, maximo);
        }
    }

    private static double lerValor(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(entrada);
                if (Double.isFinite(valor) && valor >= 0) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // Mostra a mesma orientação para valores inválidos.
            }
            System.out.println("Digite um preço válido, igual ou maior que zero.");
        }
    }

    private static boolean contemTexto(String texto, String consulta) {
        return texto != null && texto.toLowerCase(Locale.ROOT).contains(consulta);
    }
}
