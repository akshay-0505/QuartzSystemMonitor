package com.practice.SystemMonitor.utils;

import com.practice.SystemMonitor.model.SystemMetricsRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CsvMetricsReader {
    private static final String FILE_PATH = "./logs/system_metrics.csv";

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

    public static List<SystemMetricsRecord> readRecordsBetween(String from, String to) {
        List<SystemMetricsRecord> filtered = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean isHeader = true;

            LocalDateTime fromTime = LocalDateTime.parse(from, formatter);
            LocalDateTime toTime = LocalDateTime.parse(to, formatter);

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] tokens = line.split(",");
                LocalDateTime timestamp = LocalDateTime.parse(tokens[0], formatter);

                if (timestamp.isBefore(toTime) && timestamp.isAfter(fromTime)) {
                    double cpuLoad = Double.parseDouble(tokens[1]);
                    long usedMemory = Long.parseLong(tokens[2]);
                    long totalMemory = Long.parseLong(tokens[3]);
                    filtered.add(new SystemMetricsRecord(tokens[0], cpuLoad, usedMemory, totalMemory));
                }
            }

        } catch (IOException | DateTimeParseException e) {
            e.printStackTrace();
        }

        return filtered;
    }

}
