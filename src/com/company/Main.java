package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<Job> jobs = new ArrayList<Job>();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); //job
        int m = scanner.nextInt();//station
        ArrayList<WorkStation>workStations = new ArrayList<WorkStation>();
        long finalSum = 0;
        for (int i=0;i<n;i++) {
            workStations.add(new WorkStation(i));
        }

        for (int i =0;i<n;i++){//inputs and adding sum into workstation array
            int availableTime = scanner.nextInt();
            jobs.add(new Job(availableTime));

            for (int j=0;j<m;j++){
                int time = scanner.nextInt();
                workStations.get(j).setSum(workStations.get(j).getSum()+time);
                jobs.get(jobs.size()-1).addTime(time);
                finalSum += time;
            }
        }

        for (int i =0;i<n;i++){
            for (int j=i;j<n;j++){
                   if(jobs.get(i).getAvailableTime()> jobs.get(j).getAvailableTime()){//sorting jobs array by its available time field
                       Job temp = jobs.get(i);
                       jobs.set(i,jobs.get(j));
                       jobs.set(j,temp);
                   }
            }
        }
        for (int i =0;i<n;i++)
            System.out.println(jobs.get(i).getAvailableTime());

        for (int i=n-1;i>=0;i--){
            for (int j=0;j<workStations.size();j++){
                workStations.get(j).setSum(workStations.get(j).getSum()-jobs.get(i).getWorkStationTimeSpend().get(workStations.get(j).getIndex()));
                finalSum-=jobs.get(i).getWorkStationTimeSpend().get(i);
            }
            for (int j=0;j<m;j++){
                for (int k=j;k<m;k++){
                    if(workStations.get(j).getSum()> workStations.get(k).getSum()){//sorting workStation array
                        WorkStation temp = workStations.get(j);
                        workStations.set(j,workStations.get(k));
                        workStations.set(k,temp);
                    }
                }
            }
            int uper = 0;
            for (int j=0;j<m;j++){
                jobs.get(i).getPos().set(workStations.get(j).getIndex(), (int) (finalSum+jobs.get(i).getAvailableTime())+uper);
                uper+=jobs.get(i).getAvailableTime();
            }
        }
    }
}
