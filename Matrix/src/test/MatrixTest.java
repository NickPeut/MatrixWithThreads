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
    void multiplicationTest() {
        Matrix matrix = new Matrix("./src/files/firstMatrix.txt");
        Matrix matrix2 = new Matrix("./src/files/secondMatrix.txt");
        int p = 5;
        Matrix newMatrixThreads = matrix.multiplyThreads(matrix2, p);
        Matrix newMatrixSimple = matrix.simpleMultiply(matrix2);
        for(int i = 0; i < matrix.getHeight(); i++) {
            for(int j = 0; j < matrix.getWidth(); j++) {
                Assertions.assertEquals(newMatrixThreads.getValues(j, i), newMatrixSimple.getValues(i, j));
            }
        }
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

        int p = 1;
        start = System.currentTimeMillis();
        Matrix newMatrix = matrix.multiplyThreads(matrix2, p);
        if(newMatrix.equals(newMatrix0))
            System.out.println(p +" поток \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");

        p = 2;
        start = System.currentTimeMillis();
        newMatrix = matrix.multiplyThreads(matrix2, p);
        if(newMatrix.equals(newMatrix0))
            System.out.println(p +" потока \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");


        p = 3;
        start = System.currentTimeMillis();
        newMatrix = matrix.multiplyThreads(matrix2, p);
        if(newMatrix.equals(newMatrix0))
            System.out.println(p +" потока \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");


        p = 4;
        start = System.currentTimeMillis();
        newMatrix = matrix.multiplyThreads(matrix2, p);
        if(newMatrix.equals(newMatrix0))
            System.out.println( p +" потока \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");

        p = 8;
        start = System.currentTimeMillis();
        newMatrix = matrix.multiplyThreads(matrix2, p);
        if(newMatrix.equals(newMatrix0))
            System.out.println( p +" потока \nВремя вычислений: " + (System.currentTimeMillis() - start) + "мс.");

        newMatrix.writeMatrixInFile("./src/files/resultMatrix.txt");
    }

}
