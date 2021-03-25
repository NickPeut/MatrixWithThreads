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
    void parallelMultiplication() {
        Matrix matrix = new Matrix("./src/files/firstMatrix.txt");
        Matrix matrix2 = new Matrix("./src/files/secondMatrix.txt");
        int p = 5;
        Matrix newMatrix = matrix.multiply(matrix2, p);
        newMatrix.writeMatrixInFile("./src/files/resultMatrix.txt");
    }

    @Test
    void testBigMultiplication() {
        Matrix matrix = new Matrix(600, 700);
        matrix.generateValues();
        Matrix matrix2 = new Matrix(700, 600);
        matrix.generateValues();
        int p = 4;
        long start = System.currentTimeMillis();
        Matrix newMatrix = matrix.multiply(matrix2, p);
        System.out.println("Время вычислений: " + (System.currentTimeMillis() - start) + "мс.");
        newMatrix.writeMatrixInFile("./src/files/resultMatrix.txt");
    }

}
