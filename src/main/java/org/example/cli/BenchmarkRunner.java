package org.example.cli;

import org.example.algorithm.ShellSort;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    
    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }
        
        String command = args[0];
        
        switch (command) {
            case "benchmark":
                runBenchmark(args);
                break;
            case "compare":
                runComparison(args);
                break;
            case "help":
                printUsage();
                break;
            default:
                System.out.println("Unknown command: " + command);
                printUsage();
        }
    }
    
    private static void runBenchmark(String[] args) {
        int minSize = 100;
        int maxSize = 10000;
        int step = 100;
        int iterations = 5;
        String outputFile = "benchmark_results.csv";
        
        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("--min=")) {
                minSize = Integer.parseInt(args[i].substring(6));
            } else if (args[i].startsWith("--max=")) {
                maxSize = Integer.parseInt(args[i].substring(6));
            } else if (args[i].startsWith("--step=")) {
                step = Integer.parseInt(args[i].substring(7));
            } else if (args[i].startsWith("--iterations=")) {
                iterations = Integer.parseInt(args[i].substring(13));
            } else if (args[i].startsWith("--output=")) {
                outputFile = args[i].substring(9);
            }
        }
        
        System.out.println("Running benchmark...");
        System.out.println("Size range: " + minSize + " to " + maxSize + " (step: " + step + ")");
        System.out.println("Iterations per size: " + iterations);
        System.out.println("Output file: " + outputFile);
        
        runBenchmarkInternal(minSize, maxSize, step, iterations, outputFile);
    }
    
    private static void runComparison(String[] args) {
        int[] sizes = {100, 500, 1000, 2000, 5000, 10000};
        String outputFile = "comparison_results.csv";
        
        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("--sizes=")) {
                String[] sizeStrings = args[i].substring(8).split(",");
                sizes = new int[sizeStrings.length];
                for (int j = 0; j < sizeStrings.length; j++) {
                    sizes[j] = Integer.parseInt(sizeStrings[j].trim());
                }
            } else if (args[i].startsWith("--output=")) {
                outputFile = args[i].substring(9);
            }
        }
        
        System.out.println("Running comparison...");
        System.out.println("Array sizes: " + Arrays.toString(sizes));
        System.out.println("Output file: " + outputFile);
        
        runComparisonInternal(sizes, outputFile);
    }
    
    private static void runBenchmarkInternal(int minSize, int maxSize, int step, int iterations, String outputFile) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.append("ArraySize,Algorithm,ExecutionTime(ns),Comparisons,Swaps\n");
            
            for (int size = minSize; size <= maxSize; size += step) {
                System.out.println("Testing size: " + size);
                
                for (int iter = 0; iter < iterations; iter++) {
                    int[] array = generateRandomArray(size);
                    
                    int[] copy1 = copyArray(array);
                    int[] copy2 = copyArray(array);
                    int[] copy3 = copyArray(array);
                    
                    ShellSort.PerformanceResult result1 = ShellSort.shellSortOriginalWithMetrics(copy1);
                    ShellSort.PerformanceResult result2 = ShellSort.shellSortKnuthWithMetrics(copy2);
                    ShellSort.PerformanceResult result3 = ShellSort.shellSortSedgewickWithMetrics(copy3);
                    
                    writer.append(size + ",Shell's Original," + result1.executionTime + "," + result1.comparisons + "," + result1.swaps + "\n");
                    writer.append(size + ",Knuth's," + result2.executionTime + "," + result2.comparisons + "," + result2.swaps + "\n");
                    writer.append(size + ",Sedgewick's," + result3.executionTime + "," + result3.comparisons + "," + result3.swaps + "\n");
                }
            }
            
            System.out.println("Benchmark completed. Results saved to " + outputFile);
            
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
    
    private static void runComparisonInternal(int[] sizes, String outputFile) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.append("ArraySize,Algorithm,ExecutionTime(ns),Comparisons,Swaps\n");
            
            for (int size : sizes) {
                System.out.println("Testing size: " + size);
                
                int[] array = generateRandomArray(size);
                
                int[] copy1 = copyArray(array);
                int[] copy2 = copyArray(array);
                int[] copy3 = copyArray(array);
                int[] copy4 = copyArray(array);
                
                long start1 = System.nanoTime();
                ShellSort.shellSortOriginal(copy1);
                long time1 = System.nanoTime() - start1;
                
                long start2 = System.nanoTime();
                ShellSort.shellSortKnuth(copy2);
                long time2 = System.nanoTime() - start2;
                
                long start3 = System.nanoTime();
                ShellSort.shellSortSedgewick(copy3);
                long time3 = System.nanoTime() - start3;
                
                long start4 = System.nanoTime();
                Arrays.sort(copy4);
                long time4 = System.nanoTime() - start4;
                
                writer.append(size + ",Shell's Original," + time1 + ",0,0\n");
                writer.append(size + ",Knuth's," + time2 + ",0,0\n");
                writer.append(size + ",Sedgewick's," + time3 + ",0,0\n");
                writer.append(size + ",Arrays.sort," + time4 + ",0,0\n");
            }
            
            System.out.println("Comparison completed. Results saved to " + outputFile);
            
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
    
    private static void printUsage() {
        System.out.println("Shell Sort Benchmark Runner");
        System.out.println("Usage:");
        System.out.println("  java BenchmarkRunner benchmark [options]");
        System.out.println("  java BenchmarkRunner compare [options]");
        System.out.println("  java BenchmarkRunner help");
        System.out.println();
        System.out.println("Benchmark options:");
        System.out.println("  --min=<size>        Minimum array size (default: 100)");
        System.out.println("  --max=<size>        Maximum array size (default: 10000)");
        System.out.println("  --step=<size>       Step size (default: 100)");
        System.out.println("  --iterations=<num>  Iterations per size (default: 5)");
        System.out.println("  --output=<file>     Output CSV file (default: benchmark_results.csv)");
        System.out.println();
        System.out.println("Comparison options:");
        System.out.println("  --sizes=<list>      Comma-separated list of sizes (default: 100,500,1000,2000,5000,10000)");
        System.out.println("  --output=<file>     Output CSV file (default: comparison_results.csv)");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java BenchmarkRunner benchmark --min=100 --max=1000 --step=100");
        System.out.println("  java BenchmarkRunner compare --sizes=100,500,1000 --output=results.csv");
    }
    
    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }
    
    private static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }
}
