package memoryPlacement_9_10_11_12;

import java.util.*;

//Java program for different memory management algorithms
public class MemoryPlacement {

// First Fit algorithm: allocate the first available block with sufficient size
static void firstFit(int blockSize[], int m, int processSize[], int n) {
   // Stores block id of the block allocated to each process
   int allocation[] = new int[n];

   // Initially no block is assigned to any process
   for (int i = 0; i < allocation.length; i++)
       allocation[i] = -1;

   // Pick each process and find suitable blocks according to its size and assign to it
   for (int i = 0; i < n; i++) {
       for (int j = 0; j < m; j++) {
           if (blockSize[j] >= processSize[i]) {
               // Allocate block j to process i
               allocation[i] = j;

               // Reduce available memory in this block
               blockSize[j] -= processSize[i];
               break;
           }
       }
   }

   // Display allocation results
   System.out.println("First Fit Allocation:");
   System.out.println("Process No.\tProcess Size\tBlock no.");
   for (int i = 0; i < n; i++) {
       System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
       if (allocation[i] != -1)
           System.out.print(allocation[i] + 1);
       else
           System.out.print("Not Allocated");
       System.out.println();
   }
}

// Best Fit algorithm: allocate the smallest available block that is large enough
static void bestFit(int blockSize[], int m, int processSize[], int n) {
   // Stores block id of the block allocated to each process
   int allocation[] = new int[n];

   // Initially no block is assigned to any process
   for (int i = 0; i < allocation.length; i++)
       allocation[i] = -1;

   // Pick each process and find the best fit block according to its size
   for (int i = 0; i < n; i++) {
       int bestIdx = -1;
       for (int j = 0; j < m; j++) {
           if (blockSize[j] >= processSize[i]) {
               if (bestIdx == -1 || blockSize[bestIdx] > blockSize[j])
                   bestIdx = j;
           }
       }

       // If we found a suitable block, allocate it to the process
       if (bestIdx != -1) {
           allocation[i] = bestIdx;
           blockSize[bestIdx] -= processSize[i];
       }
   }

   // Display allocation results
   System.out.println("\nBest Fit Allocation:");
   System.out.println("Process No.\tProcess Size\tBlock no.");
   for (int i = 0; i < n; i++) {
       System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
       if (allocation[i] != -1)
           System.out.print(allocation[i] + 1);
       else
           System.out.print("Not Allocated");
       System.out.println();
   }
}

// Next Fit algorithm: allocate the next available block with sufficient size
static void nextFit(int blockSize[], int m, int processSize[], int n) {
   // Stores block id of the block allocated to each process
   int allocation[] = new int[n];
   Arrays.fill(allocation, -1);
   int j = 0;

   // Pick each process and find the next suitable block according to its size
   for (int i = 0; i < n; i++) {
       while (j < m) {
           if (blockSize[j] >= processSize[i]) {
               // Allocate block j to process i
               allocation[i] = j;
               blockSize[j] -= processSize[i];
               break;
           }
           j = (j + 1) % m;
       }
   }

   // Display allocation results
   System.out.println("\nNext Fit Allocation:");
   System.out.println("Process No.\tProcess Size\tBlock no.");
   for (int i = 0; i < n; i++) {
       System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
       if (allocation[i] != -1)
           System.out.print(allocation[i] + 1);
       else
           System.out.print("Not Allocated");
       System.out.println();
   }
}

// Worst Fit algorithm: allocate the largest available block
static void worstFit(int blockSize[], int m, int processSize[], int n) {
   // Stores block id of the block allocated to each process
   int allocation[] = new int[n];

   // Initially no block is assigned to any process
   for (int i = 0; i < allocation.length; i++)
       allocation[i] = -1;

   // Pick each process and find the worst fit block according to its size
   for (int i = 0; i < n; i++) {
       int wstIdx = -1;
       for (int j = 0; j < m; j++) {
           if (blockSize[j] >= processSize[i]) {
               if (wstIdx == -1 || blockSize[wstIdx] < blockSize[j])
                   wstIdx = j;
           }
       }

       // If we found a suitable block, allocate it to the process
       if (wstIdx != -1) {
           allocation[i] = wstIdx;
           blockSize[wstIdx] -= processSize[i];
       }
   }

   // Display allocation results
   System.out.println("\nWorst Fit Allocation:");
   System.out.println("Process No.\tProcess Size\tBlock no.");
   for (int i = 0; i < n; i++) {
       System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
       if (allocation[i] != -1)
           System.out.print(allocation[i] + 1);
       else
           System.out.print("Not Allocated");
       System.out.println();
   }
}

// Main method to test all four memory allocation algorithms
public static void main(String[] args) {
   int blockSize1[] = {100, 500, 200, 300, 600};
   int processSize1[] = {212, 417, 112, 426};
   int blockSize2[] = {100, 500, 200, 300, 600};
   int blockSize3[] = {5, 10, 20};
   int processSize2[] = {10, 20, 5};
   int blockSize4[] = {100, 500, 200, 300, 600};
   
   System.out.println("Memory Allocation using Different Fit Strategies:\n");

   // First Fit Allocation
   firstFit(blockSize1, blockSize1.length, processSize1, processSize1.length);
   
   // Best Fit Allocation
   bestFit(blockSize2, blockSize2.length, processSize1, processSize1.length);

   // Next Fit Allocation
   nextFit(blockSize3, blockSize3.length, processSize2, processSize2.length);

   // Worst Fit Allocation
   worstFit(blockSize4, blockSize4.length, processSize1, processSize1.length);
}
}
