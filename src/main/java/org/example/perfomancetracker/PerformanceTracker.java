package org.example.perfomancetracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerformanceTracker {
    private List<PerformanceMetrics> metrics;
    private String algorithmName;
    
    public PerformanceTracker(String algorithmName) {
        this.algorithmName = algorithmName;
        this.metrics = new ArrayList<>();
    }
    
    public void startTimer() {
        PerformanceMetrics metric = new PerformanceMetrics();
        metric.startTime = System.nanoTime();
        metric.algorithmName = this.algorithmName;
        metrics.add(metric);
    }
    
    public void endTimer(int arraySize, long comparisons, long swaps) {
        if (!metrics.isEmpty()) {
            PerformanceMetrics metric = metrics.get(metrics.size() - 1);
            metric.endTime = System.nanoTime();
            metric.executionTime = metric.endTime - metric.startTime;
            metric.arraySize = arraySize;
            metric.comparisons = comparisons;
            metric.swaps = swaps;
        }
    }
    
    public void recordMetrics(int arraySize, long executionTime, long comparisons, long swaps) {
        PerformanceMetrics metric = new PerformanceMetrics();
        metric.algorithmName = this.algorithmName;
        metric.arraySize = arraySize;
        metric.executionTime = executionTime;
        metric.comparisons = comparisons;
        metric.swaps = swaps;
        metrics.add(metric);
    }
    
    public void exportToCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("Algorithm,ArraySize,ExecutionTime(ns),Comparisons,Swaps\n");
            
            for (PerformanceMetrics metric : metrics) {
                writer.append(metric.algorithmName)
                      .append(",")
                      .append(String.valueOf(metric.arraySize))
                      .append(",")
                      .append(String.valueOf(metric.executionTime))
                      .append(",")
                      .append(String.valueOf(metric.comparisons))
                      .append(",")
                      .append(String.valueOf(metric.swaps))
                      .append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
    
    public void printSummary() {
        System.out.println("Performance Summary for " + algorithmName + ":");
        System.out.println("Total runs: " + metrics.size());
        
        if (!metrics.isEmpty()) {
            long totalTime = 0;
            long totalComparisons = 0;
            long totalSwaps = 0;
            
            for (PerformanceMetrics metric : metrics) {
                totalTime += metric.executionTime;
                totalComparisons += metric.comparisons;
                totalSwaps += metric.swaps;
            }
            
            double avgTime = (double) totalTime / metrics.size();
            double avgComparisons = (double) totalComparisons / metrics.size();
            double avgSwaps = (double) totalSwaps / metrics.size();
            
            System.out.println("Average execution time: " + String.format("%.2f", avgTime) + " ns");
            System.out.println("Average comparisons: " + String.format("%.2f", avgComparisons));
            System.out.println("Average swaps: " + String.format("%.2f", avgSwaps));
        }
    }
    
    public List<PerformanceMetrics> getMetrics() {
        return new ArrayList<>(metrics);
    }
    
    public void clear() {
        metrics.clear();
    }
    
    public static void runBenchmark() {
        int[] sizes = {100, 500, 1000, 2000, 5000};
        PerformanceTracker shellTracker = new PerformanceTracker("Shell's Original");
        PerformanceTracker knuthTracker = new PerformanceTracker("Knuth's");
        PerformanceTracker sedgewickTracker = new PerformanceTracker("Sedgewick's");
        
        for (int size : sizes) {
            System.out.println("Testing array size: " + size);
            
            for (int i = 0; i < 5; i++) {
                int[] testArray = generateRandomArray(size);
                
                int[] array1 = copyArray(testArray);
                int[] array2 = copyArray(testArray);
                int[] array3 = copyArray(testArray);
                
                PerformanceResult result1 = shellSortOriginalWithMetrics(array1);
                PerformanceResult result2 = shellSortKnuthWithMetrics(array2);
                PerformanceResult result3 = shellSortSedgewickWithMetrics(array3);
                
                shellTracker.recordMetrics(size, result1.executionTime, result1.comparisons, result1.swaps);
                knuthTracker.recordMetrics(size, result2.executionTime, result2.comparisons, result2.swaps);
                sedgewickTracker.recordMetrics(size, result3.executionTime, result3.comparisons, result3.swaps);
            }
        }
        
        System.out.println("\n=== PERFORMANCE SUMMARY ===");
        shellTracker.printSummary();
        System.out.println();
        knuthTracker.printSummary();
        System.out.println();
        sedgewickTracker.printSummary();
        
        shellTracker.exportToCSV("shell_original_performance.csv");
        knuthTracker.exportToCSV("knuth_performance.csv");
        sedgewickTracker.exportToCSV("sedgewick_performance.csv");
        
        System.out.println("\nCSV files exported successfully!");
    }
    
    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }
    
    public static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }
    
    public static int[] generateReverseSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }
    
    public static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }
    
    public static PerformanceResult shellSortOriginalWithMetrics(int[] arr) {
        int n = arr.length;
        long comparisons = 0;
        long swaps = 0;
        long startTime = System.nanoTime();
        
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap; j -= gap) {
                    comparisons++;
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        swaps++;
                    } else {
                        break;
                    }
                }
                arr[j] = temp;
            }
        }
        
        long endTime = System.nanoTime();
        return new PerformanceResult(endTime - startTime, comparisons, swaps);
    }
    
    public static PerformanceResult shellSortKnuthWithMetrics(int[] arr) {
        int n = arr.length;
        long comparisons = 0;
        long swaps = 0;
        long startTime = System.nanoTime();
        int gap = 1;
        
        while (gap < n / 3) {
            gap = 3 * gap + 1;
        }
        
        while (gap >= 1) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap; j -= gap) {
                    comparisons++;
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        swaps++;
                    } else {
                        break;
                    }
                }
                arr[j] = temp;
            }
            gap = (gap - 1) / 3;
        }
        
        long endTime = System.nanoTime();
        return new PerformanceResult(endTime - startTime, comparisons, swaps);
    }
    
    public static PerformanceResult shellSortSedgewickWithMetrics(int[] arr) {
        int n = arr.length;
        long comparisons = 0;
        long swaps = 0;
        long startTime = System.nanoTime();
        int[] gaps = generateSedgewickGaps(n);
        
        for (int k = gaps.length - 1; k >= 0; k--) {
            int gap = gaps[k];
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap; j -= gap) {
                    comparisons++;
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        swaps++;
                    } else {
                        break;
                    }
                }
                arr[j] = temp;
            }
        }
        
        long endTime = System.nanoTime();
        return new PerformanceResult(endTime - startTime, comparisons, swaps);
    }
    
    private static int[] generateSedgewickGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        int k = 0;
        
        while (true) {
            int gap1 = 9 * (int) Math.pow(4, k) - 9 * (int) Math.pow(2, k) + 1;
            int gap2 = (int) Math.pow(4, k + 1) - 3 * (int) Math.pow(2, k + 1) + 1;
            
            if (gap1 < n) {
                gaps.add(gap1);
            }
            if (gap2 < n && gap2 != gap1) {
                gaps.add(gap2);
            }
            
            if (gap1 >= n && gap2 >= n) {
                break;
            }
            k++;
        }
        
        return gaps.stream().mapToInt(i -> i).toArray();
    }
    
    public static class PerformanceMetrics {
        public String algorithmName;
        public int arraySize;
        public long startTime;
        public long endTime;
        public long executionTime;
        public long comparisons;
        public long swaps;
    }
    
    public static class PerformanceResult {
        public long executionTime;
        public long comparisons;
        public long swaps;
        
        public PerformanceResult(long executionTime, long comparisons, long swaps) {
            this.executionTime = executionTime;
            this.comparisons = comparisons;
            this.swaps = swaps;
        }
    }
}
