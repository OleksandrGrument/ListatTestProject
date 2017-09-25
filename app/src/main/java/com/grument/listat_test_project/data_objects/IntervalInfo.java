package com.grument.listat_test_project.data_objects;

public class IntervalInfo {

    public IntervalInfo(){}

    public IntervalInfo(int id, int lowInterval, int highInterval) {
        this.id = id;
        this.lowInterval = lowInterval;
        this.highInterval = highInterval;
    }

    private int id = 0;
    private int lowInterval = 0;
    private int highInterval = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLowInterval() {
        return lowInterval;
    }

    public void setLowInterval(int lowInterval) {
        this.lowInterval = lowInterval;
    }

    public int getHighInterval() {
        return highInterval;
    }

    public void setHighInterval(int highInterval) {
        this.highInterval = highInterval;
    }

    @Override
    public String toString() {
        return "IntervalInfo{" +
                "id=" + id +
                ", lowInterval=" + lowInterval +
                ", highInterval=" + highInterval +
                '}';
    }
}