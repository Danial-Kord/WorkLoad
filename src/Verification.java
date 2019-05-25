import java.io.*;
import java.util.ArrayList;

public class Verification {
    private int jobsNum;//how many inputJobs we have
    private int workStationNum;//how many workStation we have
    private ArrayList<Job> inputJobs;
    private ArrayList<Job> outputJobs;
    public Verification(String inputPath,String outputPath) throws IOException { // recieve the input and output path to check the out put is correct or not
        File input = new File(inputPath);
        File output = new File(outputPath);

        BufferedReader inputBuffer = null;
        BufferedReader outPutBuffer = null;
        try {
            inputBuffer = new BufferedReader(new FileReader(input));
            outPutBuffer = new BufferedReader(new FileReader(output));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String st;
        st = inputBuffer.readLine();
        String[] temp = st.split(" ");
        jobsNum = Integer.parseInt(temp[0]);
        workStationNum = Integer.parseInt(temp[1]);
        while ((st = inputBuffer.readLine()) != null){
            temp = st.split(" ");
            ArrayList<Integer>workStatonTemp = new ArrayList<Integer>();
            for (int i=1;i<temp.length;i++){
                workStatonTemp.add(Integer.parseInt(temp[i]));
            }
            inputJobs.add(new Job(Integer.parseInt(temp[0]),workStatonTemp));
        }
        int sum = Integer.parseInt(outPutBuffer.readLine());
        while ((st = outPutBuffer.readLine()) != null){
            temp = st.split(" ");
            ArrayList<Integer>workStatonTemp = new ArrayList<Integer>();
            for (int i=1;i<temp.length;i++){
                workStatonTemp.add(Integer.parseInt(temp[i]));
            }
            outputJobs.add(new Job(-1));
            outputJobs.get(outputJobs.size()-1).setPos(workStatonTemp);
        }
    }
    public boolean check(){
        for (int i=0;i<jobsNum;i++){
            for (int j=0;j<workStationNum;j++){
                if(outputJobs.get(i).getPos().get(j) < inputJobs.get(i).getAvailableTime())//check that every job'position should be upper than available Time
                    return false;
                Job cur = outputJobs.get(i);
                int start = cur.getPos().get(j);
                int end = start + inputJobs.get(i).getWorkStationTimeSpend().get(j);
                for (int k=j+1;k<workStationNum;k++){//checking that no hit event happening between the same job in different stations
                    int start2 = cur.getPos().get(k);
                    int end2 = start + inputJobs.get(i).getWorkStationTimeSpend().get(k);
                    if(start <= start2){
                        if(start2 <= end)
                            return false;
                    }
                    if(start >= start2){
                        if(start <= end2)
                            return false;
                    }
                }
            }
        }
        return true;
    }
}
