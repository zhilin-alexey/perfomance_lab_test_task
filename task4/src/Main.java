import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    static final String USAGE = "\nИспользование: java Main <входной файл>";

    public static int minMoves(int[] nums) {
        int target = Arrays.stream(nums).sum() / nums.length;

        int steps = 0;
        for (int num : nums) {
            steps += Math.abs(num - target);
        }
        return steps;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Должен быть один аргумент." + USAGE);
            System.exit(1);
        }

        Path inputFile;
        try {
            inputFile = Paths.get(args[0]);
        } catch (Exception e) {
            System.out.println("Неверный путь к входному файла\n" + USAGE + "\n" + e);
            return;
        }

        String content;
        try {
            content = Files.readString(inputFile);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла\n" + e);
            return;
        }

        int[] nums = content.lines().flatMap(line -> Arrays.stream(line.split("\\s+"))).mapToInt(Integer::parseInt).toArray();

        System.out.println(minMoves(nums));
    }
}