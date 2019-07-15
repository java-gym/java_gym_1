package javagym;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

/**
 * This class deals with the input of the solution; you should not change it!
 */
public final class Input {

    static final class Point implements Cloneable {

        final double x;
        final double y;
        final double z;

        Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        double dist(@Nonnull Point other) {
            return Math.sqrt(
                    Math.pow(this.x - other.x, 2) +
                            Math.pow(this.y - other.y, 2) +
                            Math.pow(this.z - other.z, 2)

            );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return Double.compare(point.x, x) == 0 &&
                    Double.compare(point.y, y) == 0 &&
                    Double.compare(point.z, z) == 0;
        }
    }

    public static final void saveToCsv(@Nonnull String pth, @Nonnull double[][] matrix) throws IOException {

        try (BufferedWriter fw = new BufferedWriter(new FileWriter(pth))) {
            for (int i = 0; i < matrix.length; i++) {
                boolean isFirst = true;
                for (int j = 0; j < matrix[i].length; j++) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        fw.write(";");
                    }
                    double cell = matrix[i][j];
                    fw.write(String.format("%.12f", cell));
                }
                fw.write("\n");
            }
        }
    }

    @Nonnull
    public static final double[][] loadFromCsv(@Nonnull String pth) throws NumberFormatException {
        // https://stackoverflow.com/questions/35791418/java-read-csv-file-as-matrix

        List<String[]> rowList = new ArrayList<String[]>();
        try (BufferedReader fr = new BufferedReader(new FileReader(pth))) {
            String line;
            while ((line = fr.readLine()) != null) {
                String[] lineItems = line.split(";");
                rowList.add(lineItems);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        double[][] matrix = new double[rowList.size()][];
        for (int i = 0; i < rowList.size(); i++) {
            String[] row = rowList.get(i);
//            String[] cells = row[i].split(";");
            matrix[i] = new double[row.length];
            for (int j = 0; j < row.length; j++) {
//                String cell = cells[j];
                matrix[i][j] = Double.parseDouble(row[j].replace(",", "."));
            }
        }
        return matrix;
    }

    @Nonnull
    public static final Point[] loadPointsFromCsv() throws NumberFormatException {
        double[][] matrix = loadFromCsv("test_data.csv");
        Point[] points = new Point[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != 3) {
                throw new IllegalStateException("Excepted three cells at row " + (i + 1) + " but found " + matrix[i].length);
            }
            points[i] = new Point(matrix[i][0], matrix[i][1], matrix[i][2]);
        }
        return points;
    }

    @Nonnull
    public static final void generatePoints(int pointCount) throws IOException {
        Random rand = new Random(884045000L);
        Point[] points = new Point[pointCount];
        for (int i = 0; i < pointCount; i++) {
            points[i] = new Point(
                    2e6 * rand.nextDouble() - 1e6,
                    2e6 * rand.nextDouble() - 1e6,
                    2e6 * rand.nextDouble() - 1e6
            );
        }
        double[][] matrix = Arrays.stream(points)
                .map(point -> new double[]{point.x, point.y, point.z})
                .collect(Collectors.toList()).toArray(new double[0][0]);
        saveToCsv("test_data.csv", matrix);
    }
}
