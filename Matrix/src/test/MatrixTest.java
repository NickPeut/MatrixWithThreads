package test;

import main.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixTest {
    @Test
    void getZeroMatrix() {
        Matrix newMatrix = new Matrix(1000, 1000);
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                Assertions.assertEquals((long) newMatrix.getValues(i, j), 0);
            }
        }
    }

    @Test
    void readMatrix() {
        Matrix newMatrix = new Matrix("./src/files/firstMatrix.txt");
        for (int i = 0; i < newMatrix.getHeight(); i++) {
            for (int j = 0; j < newMatrix.getWidth(); j++) {
                Assertions.assertEquals(i + 1, (long) newMatrix.getValues(j, i));
            }
        }
    }

    @Test
    void simpleMultiplicationTest() {
        Matrix matrix = new Matrix("./src/files/firstMatrix.txt");
        Matrix matrix2 = new Matrix("./src/files/secondMatrix.txt");
        Matrix newMatrixSimple = matrix.simpleMultiply(matrix2);
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                System.out.print(newMatrixSimple.getValues(j, i) + " ");
            }
            System.out.println();
        }
    }

    @Test
    void multiplicationTest() {
        Matrix matrix = new Matrix("./src/files/firstMatrix.txt");
        Matrix matrix2 = new Matrix("./src/files/secondMatrix.txt");

        Matrix newMatrixThreads = matrix.multiplyThreads(matrix2, 1);
        Matrix newMatrixSimple = matrix.simpleMultiply(matrix2);
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                Assertions.assertEquals(newMatrixThreads.getValues(j, i), newMatrixSimple.getValues(i, j));
            }
        }
    }

    void multiplicationMatrixWithThreads(int p, Matrix a, Matrix b, Matrix ans) {
        long start = System.currentTimeMillis();
        Matrix newMatrix = a.multiplyThreads(b, p);
        if (newMatrix.equals(ans))
            System.out.println(p + " потока \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");
    }

    @Test
    void testBigMultiplication() {
        int n = 700, m = 700, k = 700;
        Matrix matrix = new Matrix(n, m);
        matrix.generateValues();
        Matrix matrix2 = new Matrix(m, k);
        matrix.generateValues();

        long start = System.currentTimeMillis();
        Matrix newMatrix0 = matrix.simpleMultiply(matrix2);
        System.out.println("simple умножение \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");


        multiplicationMatrixWithThreads(1, matrix, matrix2, newMatrix0);
        multiplicationMatrixWithThreads(2, matrix, matrix2, newMatrix0);
        multiplicationMatrixWithThreads(3, matrix, matrix2, newMatrix0);
        multiplicationMatrixWithThreads(4, matrix, matrix2, newMatrix0);
        multiplicationMatrixWithThreads(5, matrix, matrix2, newMatrix0);
        multiplicationMatrixWithThreads(8, matrix, matrix2, newMatrix0);
        multiplicationMatrixWithThreads(16, matrix, matrix2, newMatrix0);

    }
}
