import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TranspositionCipher {

    public static String encode(String plainText, int[] key) throws Exception {
        // проверка валидности ключа
        if (!isKeyValid(key)) {
            throw new Exception("key is invalid. Try again");
        }

        // Заменяем пробелы на пустые символы
        plainText = plainText.replace(" ", "");

        int keyLen = key.length;
        int len = plainText.length();

        // находим число стобцов и строк шифротаблицы
        int rows = len / keyLen;
        int cols = keyLen;

        // если при делении есть остаток, то нужна еще одна строка
        if (len % keyLen != 0) {
            rows++;
        }

        // создаем таблицу, по которой будем шифровать
        char[][] grid = new char[rows][cols];

        /*
         Заносим данные в таблицу так, чтобы это соответствовало ключу
         Метод заполнения таблицы можно представить, будто
         сначала мы вписываем открытый текст, начиная слева сверху
         и строка за строкой идя слева направо
         если остаются пустые места, то мы вставляем на их место случайный латинский символ.
         Потом мы сортируем столбцы соответственно ключу. Например, если
         key[2] = 5, то мы переносим второй столбец grid на 5 позицию.
         */

        int colPos = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i * keyLen + j >= len) {
                    int randomNum = ThreadLocalRandom.current().nextInt(65, 122 + 1);
                    grid[i][j] = Character.toString(randomNum).charAt(0);
                } else {
                    grid[i][j] = plainText.charAt(i * keyLen + j);
                }
            }
        }

        // выписываем результат по столбцам слева сверху, сверху вниз.
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < cols; j++) {
            // Получаем номер столбца, который заносим в result.
            // Если j = 0, то находим номер позиции, на которой 0 находится в ключе
            int pos = getPosition(j ,key);
            for (int i = 0; i < rows; i++) {
                result.append(grid[i][pos]);
            }
        }
        return result.toString();
    }

    public static String decode(String encodedString, int[] key) {
        int keyLen = key.length;
        int len = encodedString.length();

        // находим число столбцов и строк шифротаблицы
        int rows = len / keyLen;
        int cols = keyLen;

        // создаем таблицу, по которой будем шифровать
        char[][] grid = new char[rows][cols];

        // заполняем сетку символами из зашифрованной строки
        int index = 0;
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                grid[i][j] = encodedString.charAt(index);
                index++;
            }
        }

        // создаем строку с открытым текстом
        // заполняя ее соответственно ключу
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int k : key) {
                result.append(grid[i][k]);
            }
        }
        return result.toString();
    }

    // ключ должен представлять собой набор чисел, отображающий
    // порядок номеров столбцов. Соотв. все элементы массива должны быть
    // уникальными и быть элементом множества {0, ..., key.length - 1}
    private static boolean isKeyValid(int[] key) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i : key) {
            set.add(i);
            if (i < 0 || i >= key.length) {
                return false;
            }
        }
        return set.size() == key.length;
    }

    private static int getPosition (int num, int[] key) {
        for (int i = 0; i < key.length; i++) {
            if (num == key[i]) {
                return i;
            }
        }
        return -1;
    }

}
