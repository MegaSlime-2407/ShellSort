package org.example.algorithm;

public class ShellSort {
    
    public static void shellSortOriginal(int[] arr) {
        int n = arr.length;
        
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
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
    
    public static void shellSortKnuth(int[] arr) {
        int n = arr.length;
        int gap = 1;
        
        while (gap < n / 3) {
            gap = 3 * gap + 1;
        }
        
        while (gap >= 1) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
            gap = (gap - 1) / 3;
        }
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
    
    public static void shellSortSedgewick(int[] arr) {
        int n = arr.length;
        int[] gaps = generateSedgewickGaps(n);
        
        for (int k = gaps.length - 1; k >= 0; k--) {
            int gap = gaps[k];
            if (gap <= 0) continue;
            
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }
    
    public static PerformanceResult shellSortSedgewickWithMetrics(int[] arr) {
        int n = arr.length;
        long comparisons = 0;
        long swaps = 0;
        long startTime = System.nanoTime();
        int[] gaps = generateSedgewickGaps(n);
        
        for (int k = gaps.length - 1; k >= 0; k--) {
            int gap = gaps[k];
            if (gap <= 0) continue;
            
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
        java.util.List<Integer> gaps = new java.util.ArrayList<>();
        int k = 0;
        
        while (true) {
            int gap1 = 9 * (int) Math.pow(4, k) - 9 * (int) Math.pow(2, k) + 1;
            int gap2 = (int) Math.pow(4, k + 1) - 3 * (int) Math.pow(2, k + 1) + 1;
            
            if (gap1 > 0 && gap1 < n) {
                gaps.add(gap1);
            }
            if (gap2 > 0 && gap2 < n && gap2 != gap1) {
                gaps.add(gap2);
            }
            
            if (gap1 >= n && gap2 >= n) {
                break;
            }
            k++;
        }
        
        if (gaps.isEmpty()) {
            gaps.add(1);
        }
        
        return gaps.stream().mapToInt(i -> i).toArray();
    }
    
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
    
    public static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
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
