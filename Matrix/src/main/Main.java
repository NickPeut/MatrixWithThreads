package main;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(args[0]);
        Matrix matrix2 = new Matrix(args[1]);
        int p = Integer.parseInt(args[2]);
        long start = System.currentTimeMillis();
        Matrix newMatrix = matrix.multiply(matrix2, p);
        System.out.println("Время вычислений: " + (System.currentTimeMillis() - start) + "мс.");
        if (newMatrix != null)
            newMatrix.writeMatrixInFile(args[3]);
    }
}
