package com.company;

import java.util.ArrayList;

public class Job {
    private int availableTime;
    private ArrayList<Integer> pos;
    private ArrayList<Integer> workStationTimeSpend; // index refers to witch station and value referes to what time it tackes to do that job in that station

    public Job(int availableTime) {
        this.availableTime = availableTime;

        workStationTimeSpend = new ArrayList<Integer>();
    }
    public void addPos(Integer position){
        pos.add(position);
    }
    public void addTime(Integer spendTime){
        workStationTimeSpend.add(spendTime);
    }

    public ArrayList<Integer> getPos() {
        return pos;
    }

    public ArrayList<Integer> getWorkStationTimeSpend() {
        return workStationTimeSpend;
    }

    public int getAvailableTime() {
        return availableTime;
    }
}
