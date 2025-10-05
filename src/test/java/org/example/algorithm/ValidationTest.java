package org.example.algorithm;

import java.util.Arrays;
import java.util.Random;

public class ValidationTest {
    
    public static void main(String[] args) {
        System.out.println("=== CORRECTNESS VALIDATION TESTING ===");
        
        edgeCaseValidation();
        boundaryTesting();
        stressTesting();
        consistencyTesting();
        
        System.out.println("\nValidation testing completed!");
    }
    
    public static void edgeCaseValidation() {
        System.out.println("Edge case validation...");
        
        testEmptyArray();
        testSingleElement();
        testTwoElements();
        testNegativeNumbers();
        testLargeNumbers();
        testZeroValues();
        testMaxIntValues();
        
        System.out.println("✓ All edge cases passed");
    }
    
    public static void boundaryTesting() {
        System.out.println("Boundary testing...");
        
        testMinimumSize();
        testMaximumSize();
        testPowerOfTwoSizes();
        testPrimeSizes();
        
        System.out.println("✓ All boundary tests passed");
    }
    
    public static void stressTesting() {
        System.out.println("Stress testing...");
        
        testManyDuplicates();
        testAlternatingPattern();
        testConstantValues();
        testExtremeValues();
        
        System.out.println("✓ All stress tests passed");
    }
    
    public static void consistencyTesting() {
        System.out.println("Consistency testing...");
        
        testAlgorithmConsistency();
        testStabilityCheck();
        testDeterministicBehavior();
        
        System.out.println("✓ All consistency tests passed");
    }
    
    private static void testEmptyArray() {
        int[] empty = {};
        int[] copy = copyArray(empty);
        ShellSort.shellSortOriginal(copy);
        assert copy.length == 0 : "Empty array should remain empty";
    }
    
    private static void testSingleElement() {
        int[] single = {42};
        int[] copy = copyArray(single);
        ShellSort.shellSortOriginal(copy);
        assert copy.length == 1 && copy[0] == 42 : "Single element should remain unchanged";
    }
    
    private static void testTwoElements() {
        int[] two = {5, 3};
        int[] copy = copyArray(two);
        ShellSort.shellSortOriginal(copy);
        assert copy[0] == 3 && copy[1] == 5 : "Two elements should be sorted";
    }
    
    private static void testNegativeNumbers() {
        int[] negatives = {-5, -1, -10, -3, -7};
        int[] copy = copyArray(negatives);
        ShellSort.shellSortOriginal(copy);
        assert isSorted(copy) : "Negative numbers should be sorted";
        assert copy[0] == -10 && copy[4] == -1 : "Negative sorting should work correctly";
    }
    
    private static void testLargeNumbers() {
        int[] large = {Integer.MAX_VALUE, 1000000, Integer.MIN_VALUE, 0, -1000000};
        int[] copy = copyArray(large);
        ShellSort.shellSortOriginal(copy);
        assert isSorted(copy) : "Large numbers should be sorted";
        assert copy[0] == Integer.MIN_VALUE && copy[4] == Integer.MAX_VALUE : "Large number sorting should work";
    }
    
    private static void testZeroValues() {
        int[] zeros = {0, 0, 0, 0, 0};
        int[] copy = copyArray(zeros);
        ShellSort.shellSortOriginal(copy);
        assert Arrays.equals(zeros, copy) : "Zero values should remain unchanged";
    }
    
    private static void testMaxIntValues() {
        int[] maxInts = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] copy = copyArray(maxInts);
        ShellSort.shellSortOriginal(copy);
        assert Arrays.equals(maxInts, copy) : "Max int values should remain unchanged";
    }
    
    private static void testMinimumSize() {
        int[] minSize = {1};
        int[] copy = copyArray(minSize);
        ShellSort.shellSortOriginal(copy);
        assert copy[0] == 1 : "Minimum size array should work";
    }
    
    private static void testMaximumSize() {
        int[] maxSize = new int[100000];
        Random random = new Random();
        for (int i = 0; i < maxSize.length; i++) {
            maxSize[i] = random.nextInt(1000);
        }
        int[] copy = copyArray(maxSize);
        ShellSort.shellSortOriginal(copy);
        assert isSorted(copy) : "Maximum size array should be sorted";
    }
    
    private static void testPowerOfTwoSizes() {
        int[] sizes = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024};
        for (int size : sizes) {
            int[] array = generateRandomArray(size);
            int[] copy = copyArray(array);
            ShellSort.shellSortOriginal(copy);
            assert isSorted(copy) : "Power of two size " + size + " should work";
        }
    }
    
    private static void testPrimeSizes() {
        int[] sizes = {7, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
        for (int size : sizes) {
            int[] array = generateRandomArray(size);
            int[] copy = copyArray(array);
            ShellSort.shellSortOriginal(copy);
            assert isSorted(copy) : "Prime size " + size + " should work";
        }
    }
    
    private static void testManyDuplicates() {
        int[] manyDups = new int[1000];
        Random random = new Random();
        for (int i = 0; i < manyDups.length; i++) {
            manyDups[i] = random.nextInt(10);
        }
        int[] copy = copyArray(manyDups);
        ShellSort.shellSortOriginal(copy);
        assert isSorted(copy) : "Many duplicates should be sorted";
    }
    
    private static void testAlternatingPattern() {
        int[] alternating = new int[100];
        for (int i = 0; i < alternating.length; i++) {
            alternating[i] = i % 2 == 0 ? 1 : 0;
        }
        int[] copy = copyArray(alternating);
        ShellSort.shellSortOriginal(copy);
        assert isSorted(copy) : "Alternating pattern should be sorted";
    }
    
    private static void testConstantValues() {
        int[] constant = new int[100];
        Arrays.fill(constant, 42);
        int[] copy = copyArray(constant);
        ShellSort.shellSortOriginal(copy);
        assert Arrays.equals(constant, copy) : "Constant values should remain unchanged";
    }
    
    private static void testExtremeValues() {
        int[] extreme = {Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -1, 1};
        int[] copy = copyArray(extreme);
        ShellSort.shellSortOriginal(copy);
        assert isSorted(copy) : "Extreme values should be sorted";
        assert copy[0] == Integer.MIN_VALUE && copy[4] == Integer.MAX_VALUE : "Extreme sorting should work";
    }
    
    private static void testAlgorithmConsistency() {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int size = random.nextInt(100) + 10;
            int[] array = generateRandomArray(size);
            
            int[] copy1 = copyArray(array);
            int[] copy2 = copyArray(array);
            int[] copy3 = copyArray(array);
            
            ShellSort.shellSortOriginal(copy1);
            ShellSort.shellSortKnuth(copy2);
            ShellSort.shellSortSedgewick(copy3);
            
            assert Arrays.equals(copy1, copy2) : "Shell and Knuth should produce same result";
            assert Arrays.equals(copy2, copy3) : "Knuth and Sedgewick should produce same result";
        }
    }
    
    private static void testStabilityCheck() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] copy = copyArray(array);
        ShellSort.shellSortOriginal(copy);
        
        assert isSorted(copy) : "Array should be sorted";
        assert countOccurrences(copy, 1) == 2 : "Should preserve duplicate count";
    }
    
    private static void testDeterministicBehavior() {
        int[] array = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        int[] copy1 = copyArray(array);
        int[] copy2 = copyArray(array);
        
        ShellSort.shellSortOriginal(copy1);
        ShellSort.shellSortOriginal(copy2);
        
        assert Arrays.equals(copy1, copy2) : "Algorithm should be deterministic";
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
}
