import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
    static final String USAGE = "\nИспользование: java Main <n> <m>";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Должно быть 2 аргумента" + USAGE);
        } else {
            int n, m;
            try {
                n = Integer.parseInt(args[0]);
                m = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Оба аргумента должны быть целыми числами" + USAGE + "\n" + e);
                return;
            }

            int[] baseArray = IntStream.range(0, n).map((ix) -> ix + 1).toArray();
            int[] path = new int[n];
            int currentIndex = 0;
            int pathIndex = 0;

            do {
                path[pathIndex] = baseArray[currentIndex];
                ++pathIndex;

                for(int i = 0; i < m - 1; ++i) {
                    currentIndex = (currentIndex + 1) % n;
                }
            } while(currentIndex != 0);

            Arrays.stream(path).filter((number) -> number != 0).forEach(System.out::print);
        }
    }
}
