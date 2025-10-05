# Shell Sort Implementation

This is my Java implementation of the Shell Sort algorithm that I built for my Data Structures and Algorithms class. I've included three different gap sequences and some basic benchmarking tools to compare their performance.

## What is Shell Sort?

So basically, Shell Sort is like an improved version of insertion sort. Instead of comparing adjacent elements, it compares elements that are far apart first, then gradually reduces the gap. It's named after Donald Shell who came up with it in 1959.

The cool thing about it is that while insertion sort is O(n²), Shell Sort can actually do better - sometimes even O(n log n) in good cases. It's not as fast as quicksort or mergesort, but it's still pretty decent and doesn't need extra memory.

## What's included

I implemented three different gap sequences because I wanted to see which one works best:

- **Shell's Original**: The basic one (gap = n/2, n/4, n/8, etc.)
- **Knuth's Sequence**: Uses the formula 3^k - 1 (apparently this is better)
- **Sedgewick's Sequence**: Some fancy math stuff that's supposed to be even better

I also added some benchmarking stuff to measure:
- How long each algorithm takes
- How many comparisons it makes
- How many swaps it does

Plus there's a CLI tool to run tests and export results to CSV so you can analyze the data. I even included comparison with Java's built-in Arrays.sort() to see how we stack up.

Oh, and there are tests too - unit tests, performance tests, and some edge cases I could think of.

## Setup

You'll need:
- Java 17 (or higher)
- Maven (I used 3.6 but newer versions should work fine)

To build it:
```bash
# First clone the repo
git clone <repository-url>
cd ShellSort

# Compile everything
mvn compile

# Run the tests to make sure everything works
mvn test
```

## How to use it

### Basic stuff

First, compile everything:
```bash
mvn compile
```

Then you can run the main class (though it's pretty basic right now):
```bash
java -cp target/classes org.example.Main
```

### Running benchmarks

The interesting part is the benchmark runner. It has two main commands:

**Quick benchmark:**
```bash
java -cp target/classes org.example.cli.BenchmarkRunner benchmark
```

**Custom benchmark with your own settings:**
```bash
java -cp target/classes org.example.cli.BenchmarkRunner benchmark \
  --min=100 \
  --max=5000 \
  --step=200 \
  --iterations=10 \
  --output=my_results.csv
```

**Compare different algorithms:**
```bash
java -cp target/classes org.example.cli.BenchmarkRunner compare \
  --sizes=100,500,1000,2000,5000 \
  --output=comparison.csv
```

### Using it in your own code

```java
import org.example.algorithm.ShellSort;

// Make an array to sort
int[] array = {64, 34, 25, 12, 22, 11, 90};

// Sort it with different methods
ShellSort.shellSortOriginal(array);        // Basic version
ShellSort.shellSortKnuth(array);           // Knuth's version
ShellSort.shellSortSedgewick(array);       // Sedgewick's version

// If you want to see the performance metrics
ShellSort.PerformanceResult result = ShellSort.shellSortOriginalWithMetrics(array);
System.out.println("Time: " + result.executionTime + " ns");
System.out.println("Comparisons: " + result.comparisons);
System.out.println("Swaps: " + result.swaps);
```

## Algorithm stuff (the boring theory part)

### Time Complexity

Here's what I found in my research:

| Gap Sequence | Best Case | Average Case | Worst Case |
|--------------|-----------|--------------|------------|
| Shell's Original | O(n log n) | O(n^1.5) | O(n²) |
| Knuth's | O(n log n) | O(n^1.5) | O(n^1.5) |
| Sedgewick's | O(n log n) | O(n^1.3) | O(n^1.3) |

### Space Complexity
All of them use O(1) space (in-place sorting), except Sedgewick's which needs O(log n) for storing the gap array.

### Why the different gap sequences?

**Shell's Original:**
- It's the simplest one - just divide by 2 each time
- Easy to understand and implement
- But the performance can be pretty inconsistent

**Knuth's:**
- Uses the formula 3^k - 1
- More mathematically sound
- Generally performs better than the original

**Sedgewick's:**
- This one is really optimized
- Uses two interleaved sequences with some fancy math
- Usually gives the best performance in practice

## Performance results

I ran some tests on my laptop with random arrays. Here's what I got:

| Array Size | Shell's Original | Knuth's | Sedgewick's | Arrays.sort() |
|------------|------------------|---------|-------------|---------------|
| 100        | ~0.5ms           | ~0.4ms  | ~0.3ms      | ~0.2ms        |
| 1,000      | ~8ms             | ~6ms    | ~5ms        | ~2ms          |
| 10,000     | ~120ms           | ~90ms   | ~75ms       | ~25ms         |

*Disclaimer: These times are just rough estimates from my machine. Yours might be different depending on your hardware and JVM settings.*

## Testing

I wrote some tests to make sure everything works:

```bash
# Run all tests
mvn test

# Or run specific test files
mvn test -Dtest=ShellSortTest
mvn test -Dtest=PerformanceTest
mvn test -Dtest=ValidationTest
```

What the tests cover:
- **ShellSortTest:** Basic sorting functionality
- **PerformanceTest:** Making sure the algorithms are actually fast
- **ValidationTest:** Edge cases like empty arrays, single elements, etc.

## Project structure

Here's how I organized everything:

```
ShellSort/
├── src/main/java/org/example/
│   ├── Main.java                    # Main entry point (pretty basic)
│   ├── algorithm/
│   │   └── ShellSort.java          # The actual sorting algorithms
│   ├── cli/
│   │   └── BenchmarkRunner.java    # CLI tool for running benchmarks
│   └── perfomancetracker/
│       └── PerformanceTracker.java # Helper for measuring performance
├── src/test/java/org/example/algorithm/
│   ├── ShellSortTest.java          # Basic tests
│   ├── PerformanceTest.java        # Performance tests
│   └── ValidationTest.java         # Edge case tests
├── pom.xml                         # Maven config
└── README.md                       # This file
```

## What I learned

This project was actually pretty educational. I learned:

1. How different gap sequences can make a huge difference in performance
2. How to measure and compare algorithm efficiency properly
3. More Java stuff - OOP, testing, building CLI tools
4. How to export data to CSV for analysis
5. Git branching (though I'm still figuring this out)

## Issues and limitations

There are definitely some things that could be better:

- Doesn't handle really big arrays (>1M elements) that well
- Performance can vary a lot depending on your JVM setup
- The Sedgewick sequence generation is kind of slow for huge datasets
- The Main.java is basically empty (I focused on the algorithm stuff)

## Things I might add later

- [ ] Support for different data types (maybe generics?)
- [ ] More gap sequences (Hibbard, Pratt, etc.)
- [ ] Some kind of visualization for comparing the algorithms
- [ ] Better memory handling for large datasets
- [ ] Maybe parallel sorting? (though that might be overkill)

## References

I mostly used:
- [Shell Sort - Wikipedia](https://en.wikipedia.org/wiki/Shellsort) (obviously)
- Sedgewick's paper on Shellsort analysis
- Knuth's "The Art of Computer Programming" (though I didn't read all of it, it's huge)

## About this project

This was my Data Structures and Algorithms coursework project. I tried to make it comprehensive but I'm sure there are things I missed or could have done better. Let me know if you find any bugs or have suggestions!

---

*Hope this helps with your sorting needs! 🎯*
