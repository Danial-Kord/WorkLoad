

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

        for (int i = 1; i < n; i++) {
            Job cur = jobs.get(i);
            Job pre = jobs.get(i - 1);

            int index = cur.getPriorites().indexOf(0);
            cur.getPos().set(index, Math.max(cur.getAvailableTime(), pre.getPos().get(index) + pre.getWorkStationTimeSpend().get(index)));
            for (int j = 1; j < m; j++) {
                index = cur.getPriorites().indexOf(j);
                int preIndex = cur.getPriorites().indexOf(j - 1);
                cur.getPos().set(index, Math.max(cur.getAvailableTime(), Math.max(pre.getPos().get(index) + pre.getWorkStationTimeSpend().get(index),
                        jobs.get(i).getPos().get(preIndex) + jobs.get(i).getWorkStationTimeSpend().get(preIndex))));
            }
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


        //in this section we want to creat time line to show what we have in workstation
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < m; k++) {
                Couple couple = new Couple(jobs.get(j).getPos().get(k), jobs.get(j).getWorkStationTimeSpend().get(k), j);
                workStations.get(k).timeLine.add(couple);
            }
        }

        for (WorkStation sourece : workStations) { //now couples sort in timeline
            sourece.sortTimeLine();
        }

        System.out.println("___");
        System.out.println("___");
        printerTable(workStations);

        //bubble remover
        for (int p=0;p<n;p++) {
            for (int i = 0; i < m; i++) {//all workstation
                int curentPivot = workStations.get(i).timeLine.get(0).getEnd();
                int curentStage = -1;
                while (/*curentPivot <= workStations.get(i).timeLine.get(workStations.get(i).timeLine.size()-1).getEnd()&&*/ curentStage < n - 1) {//finding null spaces
                    Couple nullSpace;
                    if(curentStage!=-1) {
                        nullSpace = new Couple(workStations.get(i).timeLine.get(curentStage).getEnd(), workStations.get(i).timeLine.get(curentStage + 1).getStart() - workStations.get(i).timeLine.get(curentStage).getEnd(), -1);
                    }else {
                        nullSpace= new Couple(0,workStations.get(i).timeLine.get(curentStage + 1).getStart(),-1);
                    }

                    if (nullSpace.getEnd() - nullSpace.getStart() == 0) {
                        curentPivot = workStations.get(i).timeLine.get(curentStage + 1).getEnd();
                        curentStage++;
                        continue;
                    }
                    for (int v = curentStage + 1; v < n; v++) {//check who is appropriate for this null space
                        Couple target = workStations.get(i).timeLine.get(v);

                        if (target.getEnd() - target.getStart() <= nullSpace.getEnd() - nullSpace.getStart()) {

                            //agar az tahesh zad biron
                            if (jobs.get(target.getjobPlaceInQueue()).getAvailableTime() + target.getEnd() - target.getStart() > nullSpace.getEnd()) {
                                continue;//if you break end of null space
                            }
                            //what we want to move
                            boolean isDrawable = true;
                            Couple map = new Couple(nullSpace.getStart(), target.getEnd() - target.getStart(), target.getjobPlaceInQueue());
                            while (map.getEnd() <= nullSpace.getEnd()) {//set the position of block for new condition
                                for (int w = 0; w < m; w++) {
                                    if (w == i) {
                                        continue;
                                    }
                                    //brothers of what we want to move
                                    Couple temp = new Couple(jobs.get(target.getjobPlaceInQueue()).getPos().get(w), jobs.get(target.getjobPlaceInQueue()).getWorkStationTimeSpend().get(w), target.getjobPlaceInQueue());
                                    if (interact(map, temp)) {
//                                    System.out.printf("tadakhol");
                                        isDrawable = false;
                                        break;
                                    }
//                                System.out.printf("search");
//                            while (map.getEnd()<=nullSpace.getEnd()){
//                                if(interact(map,temp))
//                                map.shif();
//                            }

                                }
                                if (isDrawable == true) {
                                    System.out.println("//");
                                    jobs.get(target.getjobPlaceInQueue()).getPos().set(i, map.getStart());

                                    System.out.println(" " + i + " " + target.getjobPlaceInQueue());

                                    System.out.print(target.getStart() + " ");
                                    System.out.print(target.getEnd() + " ");


                                    System.out.print(map.getStart() + " ");
                                    System.out.println(map.getEnd() + " ");


                                    workStations.get(i).timeLine.remove(target);
                                    if (curentStage == workStations.get(i).timeLine.size()) {
                                        workStations.get(i).timeLine.add(map);
                                    } else {
                                        workStations.get(i).timeLine.add(curentStage + 1, map);
                                    }
//                                System.out.println(workStations.get(i).timeLine.get(curentStage+1).getStart());
                                    curentPivot = map.getEnd();
                                    curentStage++;
                                    break;
                                }
                                map.shif();
//                            System.out.printf("\n"+ map.getStart());
                            }
                            if (isDrawable == false) {
                                curentPivot = nullSpace.getEnd();
                                curentStage++;
                            }
                            if (isDrawable == true) {
                                break;
                            }
                        }
//                    else {
//                        curentStage++;
//                    }
                    }
                    curentStage++;
//                curentPivot = workStations.get(i).timeLine.get(curentStage + 1).getEnd();
                }

            }
        }






        //this section must be done in the end of project
        System.out.println("___");
        System.out.println("___");
        printerTable(workStations);
        des=0;
//        for (int i = 0; i < n; i++) {
//            System.out.println(jobs.get(i).info());
//        }

        System.out.println("final answer \n\n-------------->>>");
        for (int i = 0; i < m; i++) {
            int temp = jobs.get(n - 1).getPos().get(jobs.get(n - 1).getPriorites().indexOf(i)) + jobs.get(n - 1).getWorkStationTimeSpend().get(jobs.get(n - 1).getPriorites().indexOf(i));
            if (temp > des)
                des = temp;
        }
        System.out.println(des);
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++) {
                if(jobs.get(j).getFirstIndex() == i)
                System.out.printf(""+ jobs.get(j).formalInfo());
            }
        }
//        System.out.println(workStations.get(2).timeLine.get(m).getEnd());


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
        return false;

    }
}
