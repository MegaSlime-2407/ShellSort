package org.example.algorithm;

import java.util.Arrays;
import java.util.Random;

public class PerformanceTest {
    
    public static void main(String[] args) {
        System.out.println("=== COMPREHENSIVE PERFORMANCE TESTING ===");
        
        performanceComparison();
        memoryUsageAnalysis();
        garbageCollectionImpact();
        algorithmComparison();
        
        System.out.println("\nPerformance testing completed!");
    }
    
    public static void performanceComparison() {
        System.out.println("Performance comparison across input sizes...");
        int[] sizes = {100, 500, 1000, 2000, 5000, 10000, 20000, 50000};
        
        System.out.println("Size\tShell(ns)\tKnuth(ns)\tSedgewick(ns)\tArrays.sort(ns)");
        
        for (int size : sizes) {
            int[] array = generateRandomArray(size);
            
            long shellTime = measureTime(() -> {
                int[] copy = copyArray(array);
                ShellSort.shellSortOriginal(copy);
            });
            
            long knuthTime = measureTime(() -> {
                int[] copy = copyArray(array);
                ShellSort.shellSortKnuth(copy);
            });
            
            long sedgewickTime = measureTime(() -> {
                int[] copy = copyArray(array);
                ShellSort.shellSortSedgewick(copy);
            });
            
            long arraysSortTime = measureTime(() -> {
                int[] copy = copyArray(array);
                Arrays.sort(copy);
            });
            
            System.out.println(size + "\t" + shellTime + "\t" + knuthTime + "\t" + 
                             sedgewickTime + "\t" + arraysSortTime);
        }
    }
    
    public static void memoryUsageAnalysis() {
        System.out.println("\nMemory usage analysis...");
        Runtime runtime = Runtime.getRuntime();
        
        int[] sizes = {1000, 5000, 10000, 50000};
        
        for (int size : sizes) {
            System.gc();
            long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
            
            int[] array = generateRandomArray(size);
            ShellSort.shellSortOriginal(array);
            
            long afterMemory = runtime.totalMemory() - runtime.freeMemory();
            long memoryUsed = afterMemory - beforeMemory;
            
            System.out.println("Size " + size + ": " + memoryUsed / 1024 + " KB memory used");
        }
    }
    
    public static void garbageCollectionImpact() {
        System.out.println("\nGarbage collection impact analysis...");
        
        int iterations = 1000;
        int arraySize = 1000;
        
        long timeWithGC = measureTimeWithGC(() -> {
            for (int i = 0; i < iterations; i++) {
                int[] array = generateRandomArray(arraySize);
                ShellSort.shellSortOriginal(array);
            }
        });
        
        long timeWithoutGC = measureTimeWithoutGC(() -> {
            for (int i = 0; i < iterations; i++) {
                int[] array = generateRandomArray(arraySize);
                ShellSort.shellSortOriginal(array);
            }
        });
        
        System.out.println("Time with GC: " + timeWithGC + " ns");
        System.out.println("Time without GC: " + timeWithoutGC + " ns");
        System.out.println("GC overhead: " + ((double)(timeWithGC - timeWithoutGC) / timeWithoutGC * 100) + "%");
    }
    
    public static void algorithmComparison() {
        System.out.println("\nDetailed algorithm comparison...");
        
        int[] testSizes = {100, 1000, 10000};
        String[] distributions = {"Random", "Sorted", "Reverse", "Nearly Sorted"};
        
        for (int size : testSizes) {
            System.out.println("\nArray size: " + size);
            System.out.println("Distribution\tShell\tKnuth\tSedgewick");
            
            for (String dist : distributions) {
                int[] array = generateArray(size, dist);
                
                long shellTime = measureTime(() -> {
                    int[] copy = copyArray(array);
                    ShellSort.shellSortOriginal(copy);
                });
                
                long knuthTime = measureTime(() -> {
                    int[] copy = copyArray(array);
                    ShellSort.shellSortKnuth(copy);
                });
                
                long sedgewickTime = measureTime(() -> {
                    int[] copy = copyArray(array);
                    ShellSort.shellSortSedgewick(copy);
                });
                
                System.out.println(dist + "\t" + shellTime + "\t" + knuthTime + "\t" + sedgewickTime);
            }
        }
    }
    
    private static long measureTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    
    private static long measureTimeWithGC(Runnable task) {
        System.gc();
        return measureTime(task);
    }
    
    private static long measureTimeWithoutGC(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    
    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }
    
    private static int[] generateArray(int size, String distribution) {
        switch (distribution) {
            case "Random":
                return generateRandomArray(size);
            case "Sorted":
                return generateSortedArray(size);
            case "Reverse":
                return generateReverseSortedArray(size);
            case "Nearly Sorted":
                return generateNearlySortedArray(size);
            default:
                return generateRandomArray(size);
        }
    }
    
    private static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }
    
    private static int[] generateReverseSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }
    
    private static int[] generateNearlySortedArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        
        for (int i = 0; i < size / 20; i++) {
            int pos1 = random.nextInt(size);
            int pos2 = random.nextInt(size);
            int temp = array[pos1];
            array[pos1] = array[pos2];
            array[pos2] = temp;
        }
        
        return array;
    }
    
    private static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }
}
