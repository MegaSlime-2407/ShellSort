package org.example.algorithm;

import java.util.Arrays;
import java.util.Random;

public class ShellSortTest {
    
    public static void main(String[] args) {
        System.out.println("=== SHELL SORT UNIT TESTS ===");
        
        testEmptyArray();
        testSingleElement();
        testTwoElements();
        testDuplicates();
        testSortedArray();
        testReverseSortedArray();
        testRandomArrays();
        testLargeArrays();
        
        System.out.println("\n=== PROPERTY-BASED TESTING ===");
        propertyBasedTest();
        
        System.out.println("\n=== CROSS-VALIDATION TESTS ===");
        crossValidationTest();
        
        System.out.println("\n=== PERFORMANCE SCALABILITY TESTS ===");
        scalabilityTest();
        
        System.out.println("\n=== INPUT DISTRIBUTION TESTS ===");
        inputDistributionTest();
        
        System.out.println("\n=== MEMORY PROFILING ===");
        memoryProfilingTest();
        
        System.out.println("\nAll tests completed!");
    }
    
    public static void testEmptyArray() {
        System.out.println("Testing empty array...");
        int[] empty = {};
        int[] copy = copyArray(empty);
        
        ShellSort.shellSortOriginal(copy);
        assert isSorted(copy) : "Empty array should remain empty and sorted";
        System.out.println("✓ Empty array test passed");
    }
    
    public static void testSingleElement() {
        System.out.println("Testing single element array...");
        int[] single = {42};
        int[] copy = copyArray(single);
        
        ShellSort.shellSortOriginal(copy);
        assert copy.length == 1 && copy[0] == 42 : "Single element should remain unchanged";
        System.out.println("✓ Single element test passed");
    }
    
    public static void testTwoElements() {
        System.out.println("Testing two element array...");
        int[] two = {5, 3};
        int[] copy = copyArray(two);
        
        ShellSort.shellSortOriginal(copy);
        assert isSorted(copy) && copy[0] == 3 && copy[1] == 5 : "Two elements should be sorted";
        System.out.println("✓ Two elements test passed");
    }
    
    public static void testDuplicates() {
        System.out.println("Testing array with duplicates...");
        int[] duplicates = {5, 3, 5, 1, 3, 5, 1};
        int[] copy = copyArray(duplicates);
        
        ShellSort.shellSortOriginal(copy);
        assert isSorted(copy) : "Array with duplicates should be sorted";
        assert countOccurrences(copy, 1) == 2 : "Should preserve duplicate count";
        assert countOccurrences(copy, 3) == 2 : "Should preserve duplicate count";
        assert countOccurrences(copy, 5) == 3 : "Should preserve duplicate count";
        System.out.println("✓ Duplicates test passed");
    }
    
    public static void testSortedArray() {
        System.out.println("Testing already sorted array...");
        int[] sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] copy = copyArray(sorted);
        
        ShellSort.shellSortOriginal(copy);
        assert Arrays.equals(sorted, copy) : "Already sorted array should remain unchanged";
        System.out.println("✓ Sorted array test passed");
    }
    
    public static void testReverseSortedArray() {
        System.out.println("Testing reverse sorted array...");
        int[] reverse = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] copy = copyArray(reverse);
        
        ShellSort.shellSortOriginal(copy);
        assert isSorted(copy) : "Reverse sorted array should be sorted";
        System.out.println("✓ Reverse sorted array test passed");
    }
    
    public static void testRandomArrays() {
        System.out.println("Testing random arrays...");
        Random random = new Random(42);
        
        for (int i = 0; i < 10; i++) {
            int size = random.nextInt(50) + 10;
            int[] array = new int[size];
            for (int j = 0; j < size; j++) {
                array[j] = random.nextInt(100);
            }
            
            int[] copy1 = copyArray(array);
            int[] copy2 = copyArray(array);
            int[] copy3 = copyArray(array);
            
            ShellSort.shellSortOriginal(copy1);
            ShellSort.shellSortKnuth(copy2);
            ShellSort.shellSortSedgewick(copy3);
            
            assert isSorted(copy1) : "Shell's original should sort correctly";
            assert isSorted(copy2) : "Knuth's should sort correctly";
            assert isSorted(copy3) : "Sedgewick's should sort correctly";
            assert Arrays.equals(copy1, copy2) : "All algorithms should produce same result";
            assert Arrays.equals(copy2, copy3) : "All algorithms should produce same result";
        }
        System.out.println("✓ Random arrays test passed");
    }
    
    public static void testLargeArrays() {
        System.out.println("Testing large arrays...");
        int[] large = generateRandomArray(1000);
        int[] copy1 = copyArray(large);
        int[] copy2 = copyArray(large);
        int[] copy3 = copyArray(large);
        
        ShellSort.shellSortOriginal(copy1);
        ShellSort.shellSortKnuth(copy2);
        ShellSort.shellSortSedgewick(copy3);
        
        assert isSorted(copy1) && isSorted(copy2) && isSorted(copy3) : "Large arrays should be sorted";
        assert Arrays.equals(copy1, copy2) && Arrays.equals(copy2, copy3) : "All algorithms should produce same result";
        System.out.println("✓ Large arrays test passed");
    }
    
    public static void propertyBasedTest() {
        System.out.println("Running property-based tests...");
        Random random = new Random();
        int passed = 0;
        int total = 100;
        
        for (int i = 0; i < total; i++) {
            int size = random.nextInt(200) + 1;
            int[] array = new int[size];
            
            for (int j = 0; j < size; j++) {
                array[j] = random.nextInt(1000) - 500;
            }
            
            int[] copy1 = copyArray(array);
            int[] copy2 = copyArray(array);
            int[] copy3 = copyArray(array);
            
            ShellSort.shellSortOriginal(copy1);
            ShellSort.shellSortKnuth(copy2);
            ShellSort.shellSortSedgewick(copy3);
            
            if (isSorted(copy1) && isSorted(copy2) && isSorted(copy3) && 
                Arrays.equals(copy1, copy2) && Arrays.equals(copy2, copy3)) {
                passed++;
            }
        }
        
        System.out.println("Property-based tests: " + passed + "/" + total + " passed");
        assert passed == total : "All property-based tests should pass";
    }
    
    public static void crossValidationTest() {
        System.out.println("Cross-validating with Arrays.sort()...");
        Random random = new Random();
        int passed = 0;
        int total = 50;
        
        for (int i = 0; i < total; i++) {
            int size = random.nextInt(100) + 10;
            int[] array = new int[size];
            
            for (int j = 0; j < size; j++) {
                array[j] = random.nextInt(1000);
            }
            
            int[] copy1 = copyArray(array);
            int[] copy2 = copyArray(array);
            int[] copy3 = copyArray(array);
            int[] copy4 = copyArray(array);
            
            ShellSort.shellSortOriginal(copy1);
            ShellSort.shellSortKnuth(copy2);
            ShellSort.shellSortSedgewick(copy3);
            Arrays.sort(copy4);
            
            if (Arrays.equals(copy1, copy4) && Arrays.equals(copy2, copy4) && Arrays.equals(copy3, copy4)) {
                passed++;
            }
        }
        
        System.out.println("Cross-validation tests: " + passed + "/" + total + " passed");
        assert passed == total : "All cross-validation tests should pass";
    }
    
    public static void scalabilityTest() {
        System.out.println("Testing scalability from 100 to 100,000 elements...");
        int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000};
        
        for (int size : sizes) {
            int[] array = generateRandomArray(size);
            
            long startTime = System.nanoTime();
            ShellSort.shellSortOriginal(array);
            long endTime = System.nanoTime();
            
            double timeMs = (endTime - startTime) / 1_000_000.0;
            System.out.println("Size " + size + ": " + String.format("%.2f", timeMs) + " ms");
            
            assert isSorted(array) : "Array should be sorted after Shell Sort";
        }
    }
    
    public static void inputDistributionTest() {
        System.out.println("Testing different input distributions...");
        int size = 1000;
        
        int[] randomArray = generateRandomArray(size);
        int[] sortedArray = generateSortedArray(size);
        int[] reverseArray = generateReverseSortedArray(size);
        int[] nearlySortedArray = generateNearlySortedArray(size);
        
        testDistribution("Random", randomArray);
        testDistribution("Sorted", sortedArray);
        testDistribution("Reverse Sorted", reverseArray);
        testDistribution("Nearly Sorted", nearlySortedArray);
    }
    
    public static void memoryProfilingTest() {
        System.out.println("Memory profiling test...");
        Runtime runtime = Runtime.getRuntime();
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        for (int i = 0; i < 100; i++) {
            int[] array = generateRandomArray(1000);
            ShellSort.shellSortOriginal(array);
        }
        
        System.gc();
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        long memoryUsed = afterMemory - beforeMemory;
        System.out.println("Memory used: " + memoryUsed / 1024 + " KB");
        
        assert memoryUsed < 10 * 1024 * 1024 : "Memory usage should be reasonable";
    }
    
    private static void testDistribution(String name, int[] array) {
        int[] copy1 = copyArray(array);
        int[] copy2 = copyArray(array);
        int[] copy3 = copyArray(array);
        
        long start1 = System.nanoTime();
        ShellSort.shellSortOriginal(copy1);
        long time1 = System.nanoTime() - start1;
        
        long start2 = System.nanoTime();
        ShellSort.shellSortKnuth(copy2);
        long time2 = System.nanoTime() - start2;
        
        long start3 = System.nanoTime();
        ShellSort.shellSortSedgewick(copy3);
        long time3 = System.nanoTime() - start3;
        
        System.out.println(name + " - Shell: " + String.format("%.2f", time1/1_000_000.0) + 
                          "ms, Knuth: " + String.format("%.2f", time2/1_000_000.0) + 
                          "ms, Sedgewick: " + String.format("%.2f", time3/1_000_000.0) + "ms");
        
        assert isSorted(copy1) && isSorted(copy2) && isSorted(copy3) : "All should be sorted";
    }
    
    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]) {
                return false;
            }
        }
        return true;
    }
    
    private static int countOccurrences(int[] arr, int value) {
        int count = 0;
        for (int num : arr) {
            if (num == value) count++;
        }
        return count;
    }
    
    private static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }
    
    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
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
        
        for (int i = 0; i < size / 10; i++) {
            int pos1 = random.nextInt(size);
            int pos2 = random.nextInt(size);
            int temp = array[pos1];
            array[pos1] = array[pos2];
            array[pos2] = temp;
        }
        
        return array;
    }
}
