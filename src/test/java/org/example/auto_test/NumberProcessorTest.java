package org.example.auto_test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;


public class NumberProcessorTest {

    // Тест проверяет зависимость времени выполнения от количества чисел в файле
    @Test
    public void testPerformance() {
        // Создаем коллекцию для хранения данных о времени выполнения и количестве чисел в файле
        XYSeries series = new XYSeries("Время выполнения");

        // Попробуем разные значения количества чисел в файле
        for (int numCount = 1000; numCount <= 1000000; numCount += 100000) {
            // Генерируем файл с заданным количеством чисел
            String fileName = "numbers_" + numCount + ".txt";
            try {
                FileWriter writer = new FileWriter(fileName);
                for (int i = 1; i <= numCount; i++) {
                    writer.write(i + " ");
                }
                writer.close();
            } catch (IOException e) {
                fail("Failed to write numbers to file");
            }

            // Измеряем время выполнения
            long startTime = System.currentTimeMillis();
            try {
                String[] arg = {fileName};
                Main.main(arg);
            } catch (Exception e) {
                fail("Exception occurred during execution: " + e.getMessage());
            }
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            // Добавляем данные в коллекцию
            series.add(numCount, duration);
        }

        // Создаем коллекцию данных для построения графика
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Зависимость времени выполнения от количества чисел в файле",
                "Количество чисел в файле", "Время выполнения (мс)",
                dataset);

        // Отображаем график на экране
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("График");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            ChartPanel chartPanel = new ChartPanel(chart);
            frame.add(chartPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        // Ждем 60 секунд перед завершением теста, чтобы пользователь мог увидеть график (время изменено для CI)
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
