package com.grument.listat_test_project.data_objects;



public class CalculationResult implements java.io.Serializable {

    public CalculationResult(){}

    public CalculationResult(int threadNumber, int primeNumberGenerated) {
        this.threadNumber = threadNumber;
        this.primeNumberGenerated = primeNumberGenerated;
    }

    private int threadNumber;

    private int primeNumberGenerated;

    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public int getPrimeNumberGenerated() {
        return primeNumberGenerated;
    }

    public void setPrimeNumberGenerated(int primeNumberGenerated) {
        this.primeNumberGenerated = primeNumberGenerated;
    }

    @Override
    public String toString() {
        return "CalculationResult{" +
                "threadNumber=" + threadNumber +
                ", primeNumberGenerated=" + primeNumberGenerated +
                '}';
    }
}
