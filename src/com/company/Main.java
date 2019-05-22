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
        ArrayList<WorkStation> workStations = new ArrayList<WorkStation>();
        long finalSum = 0;
        for (int i = 0; i < m; i++) {
            workStations.add(new WorkStation(i));
        }

        for (int i = 0; i < n; i++) {//inputs and adding sum into workstation array
            int availableTime = scanner.nextInt();
            jobs.add(new Job(availableTime));

            for (int j = 0; j < m; j++) {
                int time = scanner.nextInt();
                workStations.get(j).setSum(workStations.get(j).getSum() + time);
                jobs.get(jobs.size() - 1).addTime(time);
                jobs.get(jobs.size()-1).getPos().add(-1);
                jobs.get(jobs.size()-1).addPriority(-1);
                finalSum += time;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (jobs.get(i).getAvailableTime() > jobs.get(j).getAvailableTime()) {//sorting jobs array by its available time field
                    Job temp = jobs.get(i);
                    jobs.set(i, jobs.get(j));
                    jobs.set(j, temp);
                }
            }
        }
        for (int i = 0; i < n; i++)
            System.out.println(jobs.get(i).getAvailableTime());

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < workStations.size(); j++) {
                workStations.get(j).setSum(workStations.get(j).getSum() - jobs.get(i).getWorkStationTimeSpend().get(workStations.get(j).getIndex()));
                finalSum -= jobs.get(i).getWorkStationTimeSpend().get(j);
            }
            for (int j = 0; j < m; j++) {
                for (int k = j; k < m; k++) {
                    if (workStations.get(j).getSum() > workStations.get(k).getSum()) {//sorting workStation array
                        WorkStation temp = workStations.get(j);
                        workStations.set(j, workStations.get(k));
                        workStations.set(k, temp);
                    }
                }
            }
            int uper = 0;
            for (int j = 0; j < m; j++) {
                jobs.get(i).getPos().set(workStations.get(j).getIndex(), (int) (finalSum + jobs.get(i).getAvailableTime()) + uper);
                uper += jobs.get(i).getWorkStationTimeSpend().get(workStations.get(j).getIndex());
                jobs.get(i).getPriorites().set(workStations.get(j).getIndex(),j);
            }
        }
        System.out.println("faze 1 :\n");
        for (int i = 0; i < n; i++) {
            System.out.println(jobs.get(i).info());
        }

        for (int i=1;i<n;i++){
            Job cur = jobs.get(i);
            Job pre = jobs.get(i-1);

            int index = cur.getPriorites().indexOf(0);
            cur.getPos().set(index,Math.max(cur.getAvailableTime(),pre.getPos().get(index)+pre.getWorkStationTimeSpend().get(index)));
            for (int j=1;j<m;j++){
                index = cur.getPriorites().indexOf(j);
                int preIndex = cur.getPriorites().indexOf(j-1);
                cur.getPos().set(index,Math.max(cur.getAvailableTime(),Math.max(pre.getPos().get(index)+pre.getWorkStationTimeSpend().get(index),
                        jobs.get(i).getPos().get(preIndex)+jobs.get(i).getWorkStationTimeSpend().get(preIndex))));
            }
        }


        System.out.println("faze 2 :\n");
        for (int i = 0; i < n; i++) {
            System.out.println(jobs.get(i).info());
        }
        long des=0;
        for (int i=0;i<m;i++){
            int temp = jobs.get(n-1).getPos().get(jobs.get(n-1).getPriorites().indexOf(i))+jobs.get(n-1).getWorkStationTimeSpend().get(jobs.get(n-1).getPriorites().indexOf(i));
            if(temp > des)
                des = temp;
        }
        System.out.println(des);

    }
}
