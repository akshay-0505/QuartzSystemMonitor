package com.practice.SystemMonitor.utils;

import com.practice.SystemMonitor.model.SystemMetricsRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CsvMetricsReader {
    private static final String FILE_PATH = "system_metrics.csv";

    public static List<SystemMetricsRecord> readFirstNRecords(int n) {
        List<SystemMetricsRecord> records = new ArrayList<>();
        Deque<String> deque = new ArrayDeque<>();

        try (BufferedReader br = new BufferedReader(new FileReader(Paths.get(FILE_PATH).toFile()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // skip header
                    continue;
                }
                deque.add(line);
                if (deque.size() > n) {
                    deque.poll(); // remove oldest
                }
            }

            for (String recordLine : deque) {
                String[] tokens = recordLine.split(",");
                if (tokens.length == 4) {
                    String timestamp = tokens[0];
                    double cpuLoad = Double.parseDouble(tokens[1]);
                    long usedMemory = Long.parseLong(tokens[2]);
                    long totalMemory = Long.parseLong(tokens[3]);
                    records.add(new SystemMetricsRecord(timestamp, cpuLoad, usedMemory, totalMemory));
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // You can replace this with proper logging
        }

        return records;

    }
}
