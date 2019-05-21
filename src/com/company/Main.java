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
            }
        }

        for (int i =0;i<n;i++){
            for (int j=i;j<n;j++){
                   if(jobs.get(i).getAvailableTime()> jobs.get(j).getAvailableTime()){//sorting jobs array by its available time field
                       Job temp = jobs.get(i);
                       jobs.set(i,jobs.get(j));
                       jobs.set(j,temp);
                   }
                if(workStations.get(i).getSum()> workStations.get(j).getSum()){//sorting workStation array
                    WorkStation temp = workStations.get(i);
                    workStations.set(i,workStations.get(j));
                    workStations.set(j,temp);
                }
            }
        }
        for (int i =0;i<n;i++)
            System.out.println(jobs.get(i).getAvailableTime());


    }
}
