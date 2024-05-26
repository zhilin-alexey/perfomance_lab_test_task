import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    static final String USAGE = "Использование: java Main <файл с данными окружности> <файл с данными точек>";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Должно быть 2 аргумента" + USAGE);
            return;
        }

        String circleFile = args[0];
        String pointsFile = args[1];

        Circle circle;
        Point[] points;

        try {
            circle = readCircleData(circleFile);
            points = readPointsData(pointsFile);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файлы" + USAGE + "\n" + e.getMessage());
            return;
        }

        Arrays.stream(points).forEach((point -> System.out.println(pointPosition(circle, point))));
    }

    private static Circle readCircleData(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] center = br.readLine().trim().split("\\s+");
            double x = Double.parseDouble(center[0]);
            double y = Double.parseDouble(center[1]);
            double radius = Double.parseDouble(br.readLine().trim());
            return new Circle(x, y, radius);
        }
    }

    private static Point[] readPointsData(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            return br.lines().map((line) -> {
                String[] coords = line.trim().split("\\s+");
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                return new Point(x, y);
            }).toArray(Point[]::new);
        }
    }

    private static int pointPosition(Circle circle, Point point) {
        double distance = Math.sqrt(Math.pow(point.x - circle.x, 2.0) + Math.pow(point.y - circle.y, 2.0));
        if (distance < circle.radius) {
            return 1;
        } else {
            return distance == circle.radius ? 0 : 2;
        }
    }
}

class Circle {
    double x, y, radius;

    Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
}

class Point {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}