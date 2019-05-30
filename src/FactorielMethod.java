import java.util.ArrayList;
import java.util.Scanner;

public class FactorielMethod {
    private ArrayList<Job> jobs = new ArrayList<Job>();
    private ArrayList<WorkStation>workStations;
    private int n ; //job
    private int m ;//station
    public FactorielMethod(int n,int m,ArrayList<Job>jobs){
        this.m = m;
        this.n = n;
        this.jobs = (ArrayList<Job>) jobs.clone();
    }
    public ArrayList<Job> bestFactorielplacement(){
        for (int k=0;k<n;k++) {
            for (int i = 0; i < n; i++) {
                best(i, jobs);
            }
        }
        return jobs;
    }
    public Job best(int in,ArrayList<Job>jobArrayList){

        Job best = null;
        ArrayList<Job>jobs = new ArrayList<Job>();
        for (int i=0;i<this.jobs.size();i++){
            jobs.add(new Job(this.jobs.get(i).getAvailableTime()));
            jobs.get(jobs.size()-1).cclone(this.jobs.get(i));
        }

//        ArrayList<Job>jobArrayList = new ArrayList<Job>();
//        for (int i=0;i<jobArrayLists.size();i++){
//            jobArrayList.add(new Job(jobArrayLists.get(i).getAvailableTime()));
//            jobArrayList.get(jobArrayList.size()-1).cclone(jobArrayLists.get(i));
//        }

//        ArrayList<ArrayList<Job>> jj = new ArrayList<ArrayList<Job>>();
//        for (int i=0;i<jobArrayList.size();i++){
//
//        }
        int sum = 0;
        for (int v=0;v<n;v++) {
            for (int i = 0; i < 6; i++) {//jaygasht haye momken
                if (best == null) {
                    best = new Job(jobArrayList.get(in).getAvailableTime(), (ArrayList<Integer>) jobArrayList.get(in).getWorkStationTimeSpend().clone());
                    best.setPos((ArrayList<Integer>) jobArrayList.get(in).getPos().clone());
                    sum = maxFinishTime(best);
                }
                Job temp;
                temp = new Job(jobArrayList.get(in).getAvailableTime(), (ArrayList<Integer>) jobArrayList.get(in).getWorkStationTimeSpend().clone());
                temp.setPos((ArrayList<Integer>) jobArrayList.get(in).getPos().clone());
                temp.cclone(jobArrayList.get(in));
                Job cur = jobArrayList.get(in);
                Job pre = null;
                if (in > 0)
                    pre = jobArrayList.get(in - 1);
                else {
                    pre = new Job(0);
                    pre.setPos(new ArrayList<Integer>());
                    pre.setWorkStationTimeSpend(new ArrayList<Integer>());
                    for (int h = 0; h < m; h++) {
                        pre.addTime(0);
                        pre.addPos(0);
                    }
                }
                int index = cur.alpriorities.get(i).indexOf(0);
                cur.getPos().set(index, Math.max(cur.getAvailableTime(), pre.getPos().get(index) + pre.getWorkStationTimeSpend().get(index)));
                for (int j = 1; j < m; j++) {
                    index = cur.alpriorities.get(i).indexOf(j);
                    int preIndex = cur.alpriorities.get(i).indexOf(j - 1);
                    cur.getPos().set(index, Math.max(cur.getAvailableTime(), Math.max(pre.getPos().get(index) + pre.getWorkStationTimeSpend().get(index),
                            jobArrayList.get(in).getPos().get(preIndex) + jobArrayList.get(in).getWorkStationTimeSpend().get(preIndex))));

                }
//            ArrayList<Job>tempJ = (ArrayList<Job>) jobs.clone();
//            ArrayList<WorkStation>tempW = (ArrayList<WorkStation>) workStations.clone();

//            if(in==jobs.size()-1) {
//                tempJ =  kashMethod(tempJ,tempW);
//                if(sum > maxFinishTime(tempJ.get(jobs.size()-1))){
//                    sum = maxFinishTime(tempJ.get(jobs.size()-1));
//                    best = temp;
//                    bestJ = tempJ;
//                }
//                if(i == 5){
//                    jobs = bestJ;
//                }
//            }
//            if(index == jobs.size()-1) {
//                jj.add(temp);
//            }
//            else {
//                for (int i = 0; i < 6; i++)
                if (sum < maxFinishTime(temp)) {
                    sum = maxFinishTime(temp);
                    best = temp;
                }
//            }
            }
        }
        return best;
    }
    public ArrayList<Job>kashMethod(ArrayList<Job>jobs2,ArrayList<WorkStation>workStations2){
       workStations  =new ArrayList<WorkStation>();
        for (int i=0;i<workStations2.size();i++){
            workStations.add(new WorkStation(workStations2.get(i).getIndex()));
            workStations.get(i).timeLine = new ArrayList<Couple>();
        }
        ArrayList<Job>jobs = new ArrayList<Job>();
        for (int i=0;i<jobs2.size();i++){
            jobs.add(new Job(jobs2.get(i).getAvailableTime()));
            jobs.get(jobs.size()-1).cclone(jobs2.get(i));
        }
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < m; k++) {
                Couple couple = new Couple(jobs.get(j).getPos().get(k), jobs.get(j).getWorkStationTimeSpend().get(k), j);
                workStations.get(k).timeLine.add(couple);
            }
        }
        for (WorkStation sourece : workStations) { //now couples sort in timeline
            sourece.sortTimeLine();
        }
        //bubble remover
//        System.out.println(workStations.size());
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
                                if(jobs.get(target.getjobPlaceInQueue()).getAvailableTime()>map.getStart()){
                                    isDrawable=false;
                                }else {
                                    for (int w = 0; w < m; w++) {
                                        if (w == i) {
                                            continue;
                                        }
                                        //brothers of what we want to move
                                        Couple temp = new Couple(jobs.get(target.getjobPlaceInQueue()).getPos().get(w), jobs.get(target.getjobPlaceInQueue()).getWorkStationTimeSpend().get(w), target.getjobPlaceInQueue());
                                        if (Main.interact(map, temp)) {
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
                                }
                                if (isDrawable == true) {
//                                    System.out.println("//");
                                    jobs.get(target.getjobPlaceInQueue()).getPos().set(i, map.getStart());

//                                    System.out.println(" " + i + " " + target.getjobPlaceInQueue());
//
//                                    System.out.print(target.getStart() + " ");
//                                    System.out.print(target.getEnd() + " ");
//
//
//                                    System.out.print(map.getStart() + " ");
//                                    System.out.println(map.getEnd() + " ");


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
        workStations2 = workStations;
        int sum1=0;
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                if(jobs.get(i).getPos().get(j)+jobs.get(i).getWorkStationTimeSpend().get(j) > sum1) {
                    sum1 = jobs.get(i).getPos().get(j) + jobs.get(i).getWorkStationTimeSpend().get(j);
                }
            }
        }
//        System.out.println(sum1);
//        System.out.println("??????????");
        return jobs;
    }

    public ArrayList<WorkStation> getWorkStations2() {
        return workStations;
    }

    private int maxFinishTime(Job in){
        int max = in.getPos().get(0)+in.getWorkStationTimeSpend().get(0);
        for (int i=1;i<in.getWorkStationTimeSpend().size();i++){
            if(max < in.getPos().get(i)+in.getWorkStationTimeSpend().get(i)){
                max = in.getPos().get(i)+in.getWorkStationTimeSpend().get(i);
            }
        }
        return max;
    }
}
