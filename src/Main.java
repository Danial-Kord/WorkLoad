

import java.io.IOException;
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
        ArrayList<ArrayList<Couple>> allTables = new ArrayList<>(); //in this place we creat Arraylist for save all of workstations ___ in for you make empty work stations
        for (int i = 0; i < m; i++) {
            allTables.add(new ArrayList<Couple>());
        }


        ArrayList<WorkStation> workStations = new ArrayList<WorkStation>();
        long finalSum = 0;
        for (int i = 0; i < m; i++) {
            workStations.add(new WorkStation(i));
        }

        for (int i = 0; i < n; i++) {//inputs and adding sum into workstation array
            int availableTime = scanner.nextInt();
            jobs.add(new Job(availableTime));
            jobs.get(jobs.size()-1).setFirstIndex(i);
            for (int j = 0; j < m; j++) {
                int time = scanner.nextInt();
                workStations.get(j).setSum(workStations.get(j).getSum() + time);
                jobs.get(jobs.size() - 1).addTime(time);
                jobs.get(jobs.size() - 1).getPos().add(-1);
                jobs.get(jobs.size() - 1).addPriority(-1);
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
                jobs.get(i).getPriorites().set(workStations.get(j).getIndex(), j);
            }
        }
        System.out.println("phase 1 :\n");
        for (int i = 0; i < n; i++) {
            System.out.println(jobs.get(i).info());
        }

//        for (int i = 1; i < n; i++) {
//            Job cur = jobs.get(i);
//            Job pre = jobs.get(i - 1);
//
//            int index = cur.getPriorites().indexOf(0);
//            cur.getPos().set(index, Math.max(cur.getAvailableTime(), pre.getPos().get(index) + pre.getWorkStationTimeSpend().get(index)));
//            for (int j = 1; j < m; j++) {
//                index = cur.getPriorites().indexOf(j);
//                int preIndex = cur.getPriorites().indexOf(j - 1);
//                cur.getPos().set(index, Math.max(cur.getAvailableTime(), Math.max(pre.getPos().get(index) + pre.getWorkStationTimeSpend().get(index),
//                        jobs.get(i).getPos().get(preIndex) + jobs.get(i).getWorkStationTimeSpend().get(preIndex))));
//            }
//        }//TODO
        FactorielMethod factorielMethod = new FactorielMethod(n,m,jobs,workStations);
        //in this section we want to creat time line to show what we have in workstation


        jobs = factorielMethod.bestFactorielplacement();
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < m; k++) {
                Couple couple = new Couple(jobs.get(j).getPos().get(k), jobs.get(j).getWorkStationTimeSpend().get(k), j);
                workStations.get(k).timeLine.add(couple);
            }
        }
        for (WorkStation sourece : workStations) { //now couples sort in timeline
            sourece.sortTimeLine();
        }
        System.out.println("phase 2 :\n");
        for (int i = 0; i < n; i++) {
            System.out.println(jobs.get(i).info());
        }
        long des = 0;
        for (int i = 0; i < m; i++) {
            int temp = jobs.get(n - 1).getPos().get(jobs.get(n - 1).getPriorites().indexOf(i)) + jobs.get(n - 1).getWorkStationTimeSpend().get(jobs.get(n - 1).getPriorites().indexOf(i));
            if (temp > des)
                des = temp;
        }
        System.out.println(des);


        System.out.println("phase 3");


        System.out.println("___");
        System.out.println("___");
        printerTable(workStations);

        jobs = factorielMethod.kashMethod(jobs,workStations);

        //this section must be done in the end of project
        System.out.println("___");
        System.out.println("___");
        des=0;
//        for (int i = 0; i < n; i++) {
//            System.out.println(jobs.get(i).info());
//        }

        System.out.println("final answer \n\n-------------->>>");
        int max = workStations.get(0).timeLine.get(n-1).getEnd();

        for (int i=1;i<workStations.size();i++){
            if(max < workStations.get(i).timeLine.get(n-1).getEnd()){
                max = workStations.get(i).timeLine.get(n-1).getEnd();
            }
        }
        System.out.println(max);


        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++) {
                if(jobs.get(j).getFirstIndex() == i)
                System.out.printf(""+ jobs.get(j).formalInfo());
            }
        }
        printerTable(workStations);
        System.out.println(max);
        System.out.println("enter output name");
        String name = scanner.next();
        Verification verification = new Verification();
        verification.write("D:\\IdeaProjects\\projects\\WorkLoad2\\WorkLoad\\output\\"+name+".txt",des,jobs);
//

        //check
//        Verification verification = new Verification();
//        System.out.println("enter input name");
//        String namein = scanner.next();
//        System.out.println("enter output name");
//        String name = scanner.next();
//
//        try {
//            verification = new Verification("D:\\IdeaProjects\\projects\\WorkLoad2\\WorkLoad\\inputs\\"+namein+".txt",
//                    "D:\\IdeaProjects\\projects\\WorkLoad2\\WorkLoad\\output\\"+name+".txt");
//            System.out.println(verification.check());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public static void printerTable(ArrayList<WorkStation> temp) {
        for (WorkStation satr : temp) {
            char[] reshte = new char[250000];
            for (Couple area : satr.timeLine) {
                int start = area.getStart();
                int finish = area.getEnd();
                for (int v = start; v < finish; v++) {
                    if (v == start && area.getjobPlaceInQueue() < 10) {
                        reshte[v] = (char) (area.getjobPlaceInQueue() + 48);//when int cast to char you must add that with 48 to get number result
                    } else if (v == start && area.getjobPlaceInQueue() < 100) {
                        reshte[v] = (char) ((area.getjobPlaceInQueue() % 10 + 48));
                        reshte[v + 1] = (char) ((int) (area.getjobPlaceInQueue() / 10 + 48));
                        v++;
                    } else {
                        reshte[v] = '$';
                    }


                }
            }
            String i ="";
            for (int t = 0; t < 2000; t++) {
                if (reshte[t] == (char) 0) {
                    System.out.printf(" ");
                } else {
                    System.out.print(reshte[t]);
                }
            }

            System.out.println("LL");

        }

    }

    public static boolean interact(Couple c1, Couple c2) {//check collision of two couple
//        boolean result=false;
        if (c1.getStart() > c2.getStart() && c1.getStart() < c2.getEnd()) {
            return true;
        }
        if (c1.getEnd() < c2.getEnd() && c1.getEnd() > c2.getStart()) {
            return true;
        }
        if (c1.getStart() < c2.getStart() && c1.getEnd() > c2.getEnd()) {
            return true;
        }
        if(c1.getStart() == c2.getStart())
        return true;

        return false;

    }
}
