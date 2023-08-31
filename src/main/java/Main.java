import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        int[] key = new int[]{5, 2, 1, 3, 0, 4};
        String encodedInput = TranspositionCipher.encode(input, key);
        System.out.println("encoded: " + encodedInput);
        String decodedString = TranspositionCipher.decode(encodedInput, key);
        System.out.println("decoded: " + decodedString);
    }

    public static void printTable(char[][] table, int[] key) {
        int rows = table.length;
        int cols = table[0].length;

        // создадим строку-разделитель для таблицы
        String divider = "+";
        for (int j = 0; j < cols; j++) {
            divider += "-";
        }
        divider += "+";

        // отображаем заголовок таблицы
        System.out.println(divider);
        System.out.print("|");
        for (int j: key) {
            System.out.print(" " + j + " |");
        }
        System.out.println();
        System.out.println(divider);

        // отображаем значения таблицы
        for (int i = 0; i < rows; i++) {
            System.out.print("|");
            for (int j = 0; j < cols; j++) {
                System.out.print(" " + table[i][j] + " |");
            }
            System.out.println();
            System.out.println(divider);
        }
    }

}
