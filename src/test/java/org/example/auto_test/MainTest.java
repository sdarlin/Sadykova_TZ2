package org.example.auto_test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;

public class MainTest {

    @Test
    public void testFindMin() {
        int[] numbers = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        assertEquals(1, Main._min(numbers));
    }

    @Test
    public void testFindMax() {
        int[] numbers = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        assertEquals(9, Main._max(numbers));
    }

    @Test
    public void testCalculateSum() {
        int[] numbers = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        assertEquals(39, Main._sum(numbers));
    }

    @Test
    public void testCalculateProduct() {
        int[] numbers = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        assertEquals(97200, Main._mult(numbers));
    }

    @Test // Тест по моему усмотрению :)
    public void testSingleNumberFile() {
        String fileName = "single_number.txt";
        int expectedNumber = 42;

        // Генерируем файл с единственным числом
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(String.valueOf(expectedNumber));
            writer.close();
        } catch (IOException e) {
            fail("Failed to write number to file");
        }

        // Проверяем, что программа корректно обрабатывает файл с одним числом
        try {
            String[] arg = {"single_number.txt"};
            Main.main(arg);
        } catch (Exception e) {
        }
    }

    @Test
    public void testPerformance() {
        // Генерируем файл с большим количеством чисел
        String fileName = "input.txt";
        int numCount = 1000000;
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 1; i <= numCount; i++) {
                writer.write(i + " ");
            }
            writer.close();
        } catch (IOException e) {
            fail("Failed to write numbers to file");
        }

        // Замеряем время выполнения для файла с большим количеством чисел
        long startTime = System.currentTimeMillis();
        try {
            String[] arg = {"input.txt"};
            Main.callMainWithArguments(arg);
        } catch (Exception e) {
            fail("Exception occurred during execution: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Время выполнения для " + numCount + " чисел: " + duration + " мс");

        // Проверяем, что время выполнения не превышает разумных пределов
        assertTrue(duration < 1000); // Проверяем, что время выполнения меньше 1 секунды
    }
}
