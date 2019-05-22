package com.company;

import java.util.ArrayList;

public class WorkStation {
private int sum;
private int index;
private ArrayList<ArrayList<Integer>> timeLine =  new ArrayList<>();

    public WorkStation(int index) {
        this.index = index;
    }
    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }
    public int getIndex() {
        return index;
    }
}
