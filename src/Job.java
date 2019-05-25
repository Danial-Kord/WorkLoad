import java.util.ArrayList;

public class Job {
    private int availableTime;
    private ArrayList<Integer> pos;
    private ArrayList<Integer> priorites;
    private ArrayList<Integer> workStationTimeSpend;
    public  ArrayList<ArrayList<Integer>>alpriorities = new ArrayList<ArrayList<Integer>>();
    private int firstIndex;// index refers to witch station and value referes to what time it tackes to do that job in that station

    public Job(int availableTime) {
        String []temp = new String[6];
        temp[0] = "0 1 2";
        temp[1] = "0 2 1";
        temp[2] = "1 0 2";
        temp[3] = "2 0 1";
        temp[4] = "2 1 0";
        temp[5] = "1 2 0";
        for(int i=0;i<6;i++){
            String[]strings = temp[i].split(" ");
            alpriorities.add(new ArrayList<Integer>());
            for (int j=0;j<3;j++){
                alpriorities.get(alpriorities.size()-1).add(Integer.parseInt(strings[j]));
            }
        }
        this.availableTime = availableTime;
        pos = new ArrayList<Integer>();
        priorites = new ArrayList<Integer>();
        workStationTimeSpend = new ArrayList<Integer>();
    }

    public void setWorkStationTimeSpend(ArrayList<Integer> workStationTimeSpend) {
        this.workStationTimeSpend = workStationTimeSpend;
    }

    public Job(int availableTime, ArrayList<Integer> workStationTimeSpend) {
        this.availableTime = availableTime;
        this.workStationTimeSpend = workStationTimeSpend;
    }

    public void setPos(ArrayList<Integer> pos) {
        this.pos = pos;
    }

    public void setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public void addPos(Integer position){

        pos.add(position);
    }
    public void addPriority(Integer in){
        priorites.add(in);
    }

    public ArrayList<Integer> getPriorites() {
        return priorites;
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
    public String info(){
        String out = "available time : ";
        out+=""+availableTime+"\n";
        out+="pos : ";
        for (int i=0;i<pos.size();i++)
            out+=""+pos.get(i) + " ";
        out+="\n time spend ";
        for (int i=0;i<workStationTimeSpend.size();i++)
            out+=""+workStationTimeSpend.get(i) + " ";
        out+= "\n\n\n";
        return out;

    }
    public String formalInfo(){
        String out = "";
        out+="";
        for (int i=0;i<pos.size();i++)
            out+=""+pos.get(i) + " ";
        out+="\n";
        out+= "";
        return out;
    }
}
