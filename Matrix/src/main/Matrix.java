package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;

public class Matrix {
    private int height;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix)) return false;
        Matrix matrix = (Matrix) o;
        return height == matrix.height && width == matrix.width && values.equals(matrix.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width, values);
    }

    private int width;
    private final List<List<Long>> values = new ArrayList<>(new ArrayList<>());

    public Matrix(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                List<Long> lineValues = new ArrayList<>();
                for (String i : line.split(" "))
                    lineValues.add(Long.parseLong(i));
                values.add(lineValues);
            }
            width = values.get(0).size();
            height = values.size();
        } catch (IOException ex) {
            System.out.println("Illegal argument");
        }
    }

    public Matrix(int height, int width) {
        this.height = height;
        this.width = width;
        for (int i = 0; i < height; i++) {
            List<Long> newValues = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                newValues.add(0L);
            }
            values.add(newValues);
        }
    }

    public void generateValues() {
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                values.get(i).set(j, random.nextLong());
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Long getValues(int x, int y) {
        return values.get(y).get(x);
    }

    public void setValues(int x, int y, Long val) {
        values.get(x).set(y, val);
    }

    public List<Long> getColumn(int column) {
        List<Long> result = new ArrayList<>();
        values.forEach(row -> result.add(row.get(column)));
        return result;
    }

    public List<Long> getRow(int row) {
        return new ArrayList<>(values.get(row));
    }

    public Matrix simpleMultiply(Matrix matrix) {
        if (this.width != matrix.height) {
            System.err.println("Illegal matrix parameters");
            return null;
        }
        Matrix newMatrix = new Matrix(this.height, matrix.width);

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < matrix.width; j++) {
                newMatrix.setValues(i, j, VectorMultiplication.multiplyVectors(getRow(i), matrix.getColumn(j)));
            }
        }
        return newMatrix;
    }

    public Matrix multiplyThreads(Matrix matrix, int p) {
        if (this.width != matrix.height) {
            System.err.println("Illegal matrix parameters");
            return null;
        }

        Matrix newMatrix = new Matrix(this.height, matrix.width);
        ExecutorService executorService = Executors.newFixedThreadPool(p);

        List<VectorMultiplication> task = new ArrayList<>();
        for (int j = 0; j < matrix.width; j += p) {
            task.add(new VectorMultiplication(this, matrix, j, j + p, newMatrix));
        }
        try {
            executorService.invokeAll(task);
            executorService.shutdown();
            executorService.awaitTermination(10L, TimeUnit.SECONDS);


        } catch (InterruptedException exception) {
            System.err.println("Exception with multiply with treads");
        }
        return newMatrix;
    }


    public void writeMatrixInFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++)
                    writer.write(this.getValues(c, r).toString() + " ");
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class VectorMultiplication implements Callable<Void> {
        Matrix a;
        Matrix b;
        int start;
        int end;
        final Matrix ans;

        public VectorMultiplication(Matrix a, Matrix b, int start, int end, Matrix ans) {
            this.a = a;
            this.b = b;
            this.start = start;
            this.end = end;
            this.ans = ans;
        }

        public static long multiplyVectors(List<Long> first, List<Long> second) {
            long result = 0;
            for (int k = 0; k < first.size(); k++)
                result += first.get(k) * second.get(k);
            return result;
        }

        @Override
        public Void call() {
            for (int i = 0; i < a.height; i++) {
                for (int j = start; j < end; j++) {
                    Long res = multiplyVectors(a.getRow(i), b.getColumn(j));
                    synchronized (ans) {
                        ans.setValues(j, i, res);
                    }
                }
            }
            return null;
        }
    }
}