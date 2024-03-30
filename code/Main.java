package newPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        File folderTests = new File("C:/University/2_semester/АиСД/semester_work_first/files");
        File results = new File("C:/University/2_semester/АиСД/semester_work_first/results/results.txt");

        results.createNewFile();

        FileWriter writer = new FileWriter("C:/University/2_semester/АиСД/semester_work_first/results/results.txt");
        String headlines = String.format("%-30s %-30s %-30s %-10s\n", "", "Размер входных данных", "Время работы в наносекундах", "Количество итераций");
        writer.write(headlines);

        File[] testsData = folderTests.listFiles(); //массив всех файлов
        int k = 0; //количество обработанных файлов
        int countIterations = 0; //количество итераций
        if (testsData != null) {
            for (File file : testsData) {
                if (file.isFile()) {
                    try {
                        Scanner scanner = new Scanner(file);

                        String text = scanner.next(); //считываем строку из файла
                        //генерируем рандомный паттерн длины 5
                        String pattern = "";
                        for(int i = 0; i < 5; i++){
                            char randomSymbol = (char) (Math.random() * 26 + 97);
                            pattern += Character.toString(randomSymbol);
                        }
                        BoyerMoore boyerMoore = new BoyerMoore(pattern);

                        long start = System.nanoTime();

                        boyerMoore.run(text);

                        long finish = System.nanoTime();

                        long time = finish - start;

                        String data = String.format("%-30s %-30s %-30s %-30s\n", file.getName(), text.length() ,time, boyerMoore.getCountIterations());
                        writer.write(data);
                        k++;

                        scanner.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Файл не найден.");
                    }
                }
            }
        } else {
            System.out.println("Папка пуста или не существует.");
        }

        System.out.println("Отсортировали файлов всего: "+ k);
        writer.close();
    }
}
