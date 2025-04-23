package br.com.marcelo;

import br.com.marcelo.model.Board;
import br.com.marcelo.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);
    private final static int BOARD_LIMITE = 9;
    private static Board board;

    public static void main(String[] args) {
        final var positions = Stream.of(args).collect(toMap(
                k -> k.split(";")[0],
                v -> v.split(";")[1]
        ));
        var option = -1;
        while (true) {
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option) {
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opcao invalida, selecione uma das opcoes do menu");
            }
        }
    }

    private static void startGame(Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("Um jogo ja esta em andamento, finalize-o antes de iniciar um novo");
            return;
        }
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMITE; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMITE; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }
        board = new Board(spaces);
        System.out.println("Jogo iniciado com sucesso");
    }

    private static void inputNumber() {
        if (isNull(board)) {
            System.out.println("Um jogo ainda nao foi iniciado, inicie um novo jogo");
            return;
        }
        System.out.println("Informe a coluna em que o numero sera inserido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o numero sera inserido");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o numero que sera inserido na posicao [%s,%s]\n", col, row);
        var value = runUntilGetValidNumber(1, 9);
        if (!board.changeValue(col, row, value)) {
            System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
        }
    }

    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado iniciado");
            return;
        }
        System.out.println("Informe a coluna que em que o número será inserido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha que em que o número será inserido");
        var row = runUntilGetValidNumber(0, 8);
        if (!board.clearValue(col, row)) {
            System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
        }
    }

    private static void showCurrentGame() {
    }

    private static void showGameStatus() {
    }

    private static void clearGame() {
    }

    private static void finishGame() {
    }

    private static int runUntilGetValidNumber(final int min, final int max) {
        var current = scanner.nextInt();
        while (current < min || current > max) {
            System.out.printf("Valor invalido, digite um numero entre %s e %s\n%n", min, max);
            current = scanner.nextInt();
        }
        return current;
    }
}
