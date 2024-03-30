package createFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class createFiles {
    public static void main(String[] args) throws IOException {
        //создаем список из имен 100 файлов
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if(i < 10){
                fileNames.add("test_" + 0 + i + ".txt");
            }else{
                fileNames.add("test_" + i + ".txt");
            }
        }
        //количество символов в файле
        int countSymbolInFile = 0;
        //заполняем файлы рандомными строками
        for(int i = 0; i < 100; i++){
            countSymbolInFile += 100; //каждый раз количество символов будет на 100 больше прежнего
            //создаем файл под конкретным именем
            File newFile = new File("C:/University/2_semester/АиСД/semestrovka_1/files/" + fileNames.get(i));
            //создаем поток вывода
            OutputStream curFile = new FileOutputStream(newFile);

            //вводим в файл рандомную строку, состоящую из прописных букв английского алфавита
            for(int j = 0; j < countSymbolInFile; j++){
                char randomSymbol = (char) (Math.random() * 26 + 97);
                curFile.write(randomSymbol);
            }
            curFile.close();
        }
    }
}
