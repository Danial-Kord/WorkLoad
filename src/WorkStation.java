

import java.util.ArrayList;

public class WorkStation {
    private int sum;
    private int index;
    public  ArrayList<Couple> timeLine =  new ArrayList<>();

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

    public void sortTimeLine()//sort array list_with start time
    {
        Couple temp;
        for(int i=0;i<timeLine.size();i++){
            for(int j=0;j<timeLine.size()-i-1;j++){
                if(timeLine.get(j).getStart()>timeLine.get(j+1).getStart()){
                    temp=timeLine.get(j);
                    timeLine.set(j,timeLine.get(j+1));
                    timeLine.set(j+1,temp);
                }
            }
        }

    }
    public void showPipelinStart(){
        for(int i=0;i<timeLine.size();i++){
            System.out.print(timeLine.get(i).getStart()+" ");
        }
        System.out.println("");

    }
}
