package test;

import main.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixTest {
    @Test
    void getZeroMatrix() {
        Matrix newMatrix = new Matrix(1000, 1000);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
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
    void transposedMatrixTest() {
        Matrix matrix = new Matrix("./src/files/firstMatrix.txt");
        matrix.transposedMatrix();
        for(int i = 0; i < matrix.getHeight(); i++) {
            for(int j = 0; j < matrix.getWidth(); j++) {
                Assertions.assertEquals(matrix.getValues(j, i), matrix.getTransposedValues(i, j));
            }
        }
    }

    @Test
    void parallelMultiplication() {
        Matrix matrix = new Matrix("./src/files/firstMatrix.txt");
        Matrix matrix2 = new Matrix("./src/files/secondMatrix.txt");
        int p = 5;
        Matrix newMatrix = matrix.multiply(matrix2, p);
        newMatrix.writeMatrixInFile("./src/files/resultMatrix.txt");
    }

    @Test
    void testBigMultiplication() {
        int n = 400, m = 400, k = 500;
        Matrix matrix = new Matrix(n, m);
        matrix.generateValues();
        Matrix matrix2 = new Matrix(m, k);
        matrix.generateValues();

        int p = 1;
        long start = System.currentTimeMillis();
        Matrix newMatrix = matrix.multiply(matrix2, p);
        System.out.println(p +" поток \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");

        p = 2;
        start = System.currentTimeMillis();
        Matrix newMatrix2 = matrix.multiply(matrix2, p);
        System.out.println(p +" потока \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");


        p = 3;
        start = System.currentTimeMillis();
        Matrix newMatrix3 = matrix.multiply(matrix2, p);
        System.out.println(p +" потока \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");


        p = 4;
        start = System.currentTimeMillis();
        Matrix newMatrix4 = matrix.multiply(matrix2, p);
        System.out.println( p +" потока \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");

        newMatrix.writeMatrixInFile("./src/files/resultMatrix.txt");
    }

}
