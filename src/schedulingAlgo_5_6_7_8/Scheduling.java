package schedulingAlgo_5_6_7_8;

import java.util.Scanner;

class Process {
    int pid;                // Process ID
    int waitingTime;        // Time a process waits before getting CPU
    int arrivalTime;        // Time when the process arrives in the system
    int burstTime;          // CPU time required for the process (execution time)
    int turnAroundTime;     // Total time from arrival to completion
    int timeToComplete;     // Remaining burst time for preemptive algorithms
    int completionTime = 0; // Time when the process finishes execution
    int priority;           // Priority of the process (used in priority scheduling)

    // Constructor without priority for processes without priority scheduling
    Process(int pid, int sub, int bur) {
        this.pid = pid;
        this.arrivalTime = sub;
        this.burstTime = bur;
        this.timeToComplete = burstTime;
    }

    // Constructor with priority for priority scheduling processes
    Process(int pid, int sub, int bur, int priority) {
        this.pid = pid;
        this.arrivalTime = sub;
        this.burstTime = bur;
        this.priority = priority;
        this.timeToComplete = burstTime;
    }
}

public class Scheduling {
    public static void main(String[] args) {
        // Define the number of processes and each process's arrival time, burst time, and priority
        int n = 4;
        Process[] myProcess = new Process[n];
        myProcess[0] = new Process(1, 0, 8, 2); // Process 1 with arrival, burst time, and priority
        myProcess[1] = new Process(2, 1, 4, 1); // Process 2
        myProcess[2] = new Process(3, 2, 9, 3); // Process 3
        myProcess[3] = new Process(4, 3, 5, 4); // Process 4

        // Calling FCFS scheduling; other algorithms can be tested similarly
        FCFS(myProcess);
        // SJF(myProcess);        // Shortest Job First (Preemptive)
        // PriorityScheduling(myProcess); // Priority Scheduling (Non-preemptive)
        // RoundRobin(myProcess); // Round Robin
    }

    // First-Come, First-Serve (FCFS) Scheduling: Processes are scheduled in arrival order
    static void FCFS(Process[] myProcess) {
        int x = 0; // Variable to keep track of total CPU time spent
        Process temp;

        // Sort processes by arrival time to simulate FCFS scheduling
        for (int i = 0; i < myProcess.length; i++) {
            for (int j = i; j < myProcess.length; j++) {
                if (myProcess[i].arrivalTime > myProcess[j].arrivalTime) {
                    temp = myProcess[j];
                    myProcess[j] = myProcess[i];
                    myProcess[i] = temp;
                }
            }
        }

        // Calculate completion, turnaround, and waiting times
        for (int i = 0; i < myProcess.length; i++) {
            x += myProcess[i].burstTime; // Add burst time to the total time
            myProcess[i].completionTime = x; // Set completion time
            myProcess[i].turnAroundTime = myProcess[i].completionTime - myProcess[i].arrivalTime;
            myProcess[i].waitingTime = myProcess[i].turnAroundTime - myProcess[i].burstTime;
            System.out.println("Process " + myProcess[i].pid + ":");
            System.out.println("Turnaround Time\tCompletion Time\tWaiting Time");
            System.out.println(myProcess[i].turnAroundTime + "\t\t\t" + myProcess[i].completionTime + "\t\t" + myProcess[i].waitingTime);
        }
    }

    // Shortest Job First (SJF) Scheduling - Preemptive
    // The process with the shortest remaining burst time is chosen for execution
    static void SJF(Process[] myProcess) {
        int curTimeInterval = 0, completedProcesses = 0;
        Process curProcess;

        // Begin with the first process for initial comparison
        curProcess = myProcess[0];

        // Loop until all processes are completed
        while (completedProcesses < myProcess.length) {
            // Find a process with remaining burst time
            for (int i = 0; i < myProcess.length; i++) {
                if (myProcess[i].timeToComplete > 0) {
                    curProcess = myProcess[i];
                    break;
                }
            }

            // Select process with shortest remaining burst time that has arrived
            for (int i = 0; i < myProcess.length; i++) {
                if (myProcess[i].arrivalTime > curTimeInterval || myProcess[i].timeToComplete == 0) {
                    continue;
                }
                if (myProcess[i].timeToComplete < curProcess.timeToComplete) {
                    curProcess = myProcess[i];
                }
            }

            // Execute process for 1 time unit
            curProcess.timeToComplete -= 1;
            if (curProcess.timeToComplete == 0) { // Process completes
                completedProcesses++;
                curProcess.completionTime = curTimeInterval + 1;
            }
            curTimeInterval++;
        }

        // Calculate waiting and turnaround times
        for (Process process : myProcess) {
            process.waitingTime = process.completionTime - process.arrivalTime - process.burstTime;
            process.turnAroundTime = process.waitingTime + process.burstTime;
            System.out.println("Process " + process.pid + ":");
            System.out.println("Turnaround Time\tCompletion Time\tWaiting Time");
            System.out.println(process.turnAroundTime + "\t\t\t" + process.completionTime + "\t\t" + process.waitingTime);
        }
    }

    // Priority Scheduling - Non-preemptive: Processes are scheduled based on priority
    static void PriorityScheduling(Process[] myProcess) {
        Process temp;
        int x = 0;

        // Sort processes by priority (lower value means higher priority)
        for (int i = 0; i < myProcess.length; i++) {
            for (int j = i; j < myProcess.length; j++) {
                if (myProcess[i].priority > myProcess[j].priority) {
                    temp = myProcess[j];
                    myProcess[j] = myProcess[i];
                    myProcess[i] = temp;
                }
            }
        }

        // Calculate completion, turnaround, and waiting times
        for (Process process : myProcess) {
            x += process.burstTime; // Increment total time by the process's burst time
            process.completionTime = x; // Update completion time
            process.turnAroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnAroundTime - process.burstTime;
            System.out.println("Process " + process.pid + ":");
            System.out.println("Turnaround Time\tCompletion Time\tWaiting Time");
            System.out.println(process.turnAroundTime + "\t\t\t" + process.completionTime + "\t\t" + process.waitingTime);
        }
    }

    // Round Robin Scheduling: Each process is given a fixed time (quantum) for execution in a cyclic order
    static void RoundRobin(Process[] myProcess) {
        int curTimeInterval = 0, completedProcesses = 0;
        int quantum = 2; // Set the time quantum for each process

        // Execute processes in a round-robin manner until all are completed
        while (completedProcesses < myProcess.length) {
            for (Process process : myProcess) {
                if (process.timeToComplete > 0 && process.timeToComplete > quantum) {
                    // Execute for the quantum time and reduce remaining time
                    curTimeInterval += quantum;
                    process.timeToComplete -= quantum;
                } else if (process.timeToComplete > 0) {
                    // Complete the process if remaining time is within quantum
                    curTimeInterval += process.timeToComplete;
                    process.timeToComplete = 0;
                    process.completionTime = curTimeInterval;
                    process.turnAroundTime = process.completionTime - process.arrivalTime;
                    process.waitingTime = process.turnAroundTime - process.burstTime;
                    completedProcesses++;
                }
            }
        }

        // Display final turnaround, completion, and waiting times for each process
        for (Process process : myProcess) {
            System.out.println("Process " + process.pid + ":");
            System.out.println("Turnaround Time\tCompletion Time\tWaiting Time");
            System.out.println(process.turnAroundTime + "\t\t\t" + process.completionTime + "\t\t" + process.waitingTime);
        }
    }
}