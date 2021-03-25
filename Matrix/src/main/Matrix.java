package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Matrix {
    private int height;
    private int width;
    private final ArrayList<ArrayList<Long>> values = new ArrayList<>(new ArrayList<>());

    public Matrix(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                ArrayList<Long> lineValues = new ArrayList<>();
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
            ArrayList<Long> newValues = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                newValues.add(0L);
            }
            values.add(newValues);
        }
    }

    public void generateValues() {
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            ArrayList<Long> newValues = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                newValues.add(random.nextLong());
            }
            values.add(newValues);
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

    public ArrayList<Long> getColumn(int column) {
        ArrayList<Long> result = new ArrayList<>();
        values.forEach(element -> result.add(element.get(column)));
        return result;
    }

    public ArrayList<Long> getRow(int row) {
        return new ArrayList<>(values.get(row));
    }

    public Matrix multiply(Matrix matrix, int p) {
        if (this.width != matrix.height) {
            System.err.println("Illegal matrix parameters");
            return null;
        }

        Matrix newMatrix = new Matrix(this.height, matrix.width);
        ExecutorService executorService = Executors.newFixedThreadPool(p);

        List<vectorMultiplication> task = new ArrayList<>();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < matrix.width; j++) {
                task.add(new vectorMultiplication(i, j, getRow(i), matrix.getColumn(j)));
            }
        }
        try {
            List<Future<String>> futures = executorService.invokeAll(task);

            executorService.shutdown();
            executorService.awaitTermination(10L, TimeUnit.SECONDS);

            for (Future<String> i : futures) {
                String[] s = i.get().split(" ");
                int x = Integer.parseInt(s[0]);
                int y = Integer.parseInt(s[1]);
                Long res = Long.parseLong(s[2]);
                newMatrix.setValues(x, y, res);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Exception with multiply");
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
}

class vectorMultiplication implements Callable<String> {
    ArrayList<Long> first;
    ArrayList<Long> second;
    int i, j;

    public vectorMultiplication(int i, int  j, ArrayList<Long> first, ArrayList<Long> second) {
        this.i = i;
        this.j = j;
        this.first = first;
        this.second = second;
    }

    @Override
    public String call() {
        long result = 0;
        for (int k = 0; k < first.size(); k++)
            result += first.get(k) * second.get(k);
        return i + " " + j + " " + result;
    }
}
