import java.util.ArrayList;
import java.util.Scanner;

public class FactorielMethod {
    private ArrayList<Job> jobs = new ArrayList<Job>();
    private int n ; //job
    private int m ;//station
    public FactorielMethod(int n,int m,ArrayList<Job>jobs){
        this.m = m;
        this.n = n;
        this.jobs = jobs;
    }
    public ArrayList<Job> bestFactorielplacement(){
        for (int i=0;i<n;i++){
            best(i,jobs);
        }
        return jobs;
    }
    public Job best(int in,ArrayList<Job>jobArrayList){

        Job best = null;
        int sum = 0;
        for (int i=0;i<6;i++) {//jaygasht haye momken
            if(best == null) {
                best = new Job(jobArrayList.get(in).getAvailableTime(), (ArrayList<Integer>) jobArrayList.get(in).getWorkStationTimeSpend().clone());
                best.setPos((ArrayList<Integer>) jobArrayList.get(in).getPos().clone());
                sum = maxFinishTime(best);
            }
            Job temp;
            temp = new Job(jobArrayList.get(in).getAvailableTime(), (ArrayList<Integer>) jobArrayList.get(in).getWorkStationTimeSpend().clone());
            temp.setPos((ArrayList<Integer>) jobArrayList.get(in).getPos().clone());
            Job cur = jobArrayList.get(in);
            Job pre = null;
            if(in>0)
                pre = jobArrayList.get(in - 1);
            else
            {
                pre = new Job(0);
                pre.setPos(new ArrayList<Integer>());
                pre.setWorkStationTimeSpend(new ArrayList<Integer>());
                for (int h=0;h<m;h++){
                    pre.addTime(0);
                    pre.addPos(0);
                }
            }
            int index = cur.alpriorities.get(0).indexOf(0);
            cur.getPos().set(index, Math.max(cur.getAvailableTime(), pre.getPos().get(index) + pre.getWorkStationTimeSpend().get(index)));
            for (int j = 1; j < m; j++) {
                index = cur.alpriorities.get(0).indexOf(j);
                int preIndex = cur.alpriorities.get(0).indexOf(j - 1);
                cur.getPos().set(index, Math.max(cur.getAvailableTime(), Math.max(pre.getPos().get(index) + pre.getWorkStationTimeSpend().get(index),
                        jobArrayList.get(in).getPos().get(preIndex) + jobArrayList.get(in).getWorkStationTimeSpend().get(preIndex))));
            }
            if(sum > maxFinishTime(temp)){
                sum = maxFinishTime(temp);
                best = temp;
            }
        }
        return best;

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
