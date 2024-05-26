import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Main {
    static final String USAGE = "\nИспользование: java Main <файл с результатом> <файл со структурой> <выходной файл>";


    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Должно быть 3 аргумента" + USAGE);
            return;
        }

        Path resultFile, structureFile, outputFile;
        try {
            resultFile = Paths.get(args[0]);
            structureFile = Paths.get(args[1]);
            outputFile = Path.of(args[2]);
        } catch (Exception e) {
            System.out.println("Путь к как минимум одному файлу указан неправильно" + USAGE + "\n\n" + e);
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Tests tests;
        try {
            tests = gson.fromJson(Files.readString(structureFile), Tests.class);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл с результатами теста" + "\n\n" + e);
            return;
        }


        Values values;
        try {
            values = gson.fromJson(Files.readString(resultFile), Values.class);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл со структурой" + "\n\n" + e);
            return;
        }

        HashMap<Integer, String> valuesMap = Arrays.stream(values.values).collect(Collectors.toMap(value -> value.id, value -> value.value, (a, b) -> b, HashMap::new));

        Arrays.stream(tests.tests).forEach((test) -> setValueRecursive(valuesMap, test));

        outputFile = Paths.get(outputFile.toString());


        try {
            Files.writeString(outputFile, gson.toJson(tests));
        } catch (IOException e) {
            System.out.println("Не удалось записать выходной файл" + "\n\n" + e);
        }
    }

    public static void setValueRecursive(HashMap<Integer, String> valuesMap, Test test) {
        test.value = valuesMap.get(test.id);

        if (test.values != null)
            Arrays.stream(test.values).forEach(test1 -> setValueRecursive(valuesMap, test1));
    }

}


class Tests {
    Test[] tests;
}

class Test {
    Integer id;
    String title;
    String value;
    Test[] values;
}

class Values {
    Value[] values;
}

class Value {
    Integer id;
    String value;
}