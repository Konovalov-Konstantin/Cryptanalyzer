import java.io.*;
import java.util.*;

public class Crypto {

    static final String FILE = "C:\\Users\\Konstantin\\Desktop\\Новая папка (2)\\file1.txt";
    static final String FILE2 = "C:\\Users\\Konstantin\\Desktop\\Новая папка (2)\\file2.txt";
    static final String FILE3 = "C:\\Users\\Konstantin\\Desktop\\Новая папка (2)\\file3.txt";

    public static void main(String[] args) {

        List<Character> alfavit = alphabet();

        encryption(FILE, alfavit);

        decryption(FILE2);
    }

    // создание перечня с символами русского алфавита и знаками препинания
    static List<Character> alphabet() {
        List<Character> alphabet = new ArrayList<>();
        for (char i = 'А'; i <= 'я'; i++) {
            alphabet.add(i);
        }
        alphabet.add('ё');
        alphabet.add('Ё');
        alphabet.add(' ');
        alphabet.add('.');
        alphabet.add(',');
        alphabet.add('”');
        alphabet.add(':');
        alphabet.add('-');
        alphabet.add('!');
        alphabet.add('?');
        return alphabet;
    }

    // шифрование исходого файла
    static void encryption(String filename, List<Character> alfavit) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ключ для шифрования (целое число)");
        int key = scanner.nextInt();
        scanner.close();
        try (FileReader reader = new FileReader(filename);
             FileWriter writer = new FileWriter(FILE2)) {

            while (reader.ready()) {
                char c = (char) reader.read();
                if (alfavit.contains(c)) {
                    writer.write(c + key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // поиск ключа (ищем последовательность из трех символов - точка, пробел и заглавная буква)
    static void decryption(String filename) {

        Map<Integer, Integer> mapa = new HashMap<>();

        try (FileReader reader = new FileReader(filename)) {

            char a1 = (char) reader.read();
            char a2 = (char) reader.read();
            char a3 = (char) reader.read();
            while (reader.ready()) {
                if (a1 - a2 == 14 && a3 - a2 >= 994 && a3 - a2 <= 1025) {
                    if (mapa.containsKey((int) a1)) {
                        mapa.put((int) a1, mapa.get((int) a1) + 1);
                    } else {
                        mapa.put((int) a1, 1);
                    }
                }
                a1 = a2;
                a2 = a3;
                a3 = (char) reader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int x = 0;
        int key = 0;
        for (Map.Entry<Integer, Integer> entry : mapa.entrySet()) { //если несколько вариантов ключей - выбираем key с наибольшим value
            if (entry.getValue() > x) {
                x = entry.getValue();
                key = entry.getKey();
            }
        }

        key = key - (int) '.';
        System.out.println("Значение ключа: " + key);

        try (FileReader reader = new FileReader(filename);  // расшифровка файла найденным ключом и запись в новый файл
             FileWriter writer = new FileWriter(FILE3)) {
            while (reader.ready()) {
                char c = (char) reader.read();
                writer.write(c - key);
            }
            System.out.println("Файл расшифрован");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



