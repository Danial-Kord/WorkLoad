import java.io.*;
import java.util.ArrayList;

public class Verification {
    private int jobsNum;//how many inputJobs we have
    private int workStationNum;//how many workStation we have
    private ArrayList<Job> inputJobs;
    private ArrayList<Job> outputJobs;
    private String outputPath;
    public Verification(){}
    public Verification(String inputPath,String outputPath) throws IOException { // recieve the input and output path to check the out put is correct or not
        File input = new File(inputPath);
        File output = new File(outputPath);
        this.outputPath = outputPath;
        inputJobs = new ArrayList<Job>();
        outputJobs = new ArrayList<Job>();
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
        System.out.println(sum);
        while ((st = outPutBuffer.readLine()) != null){
            temp = st.split(" ");
            ArrayList<Integer>workStatonTemp = new ArrayList<Integer>();
            for (int i=0;i<temp.length;i++){
                workStatonTemp.add(Integer.parseInt(temp[i]));
            }
            outputJobs.add(new Job(-1));
            outputJobs.get(outputJobs.size()-1).setPos(workStatonTemp);
        }
    }
    public void write(String outputPath,long sum,ArrayList<Job>jobs){
        File file = new File(outputPath);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(""+sum);
            bufferedWriter.newLine();
            for (int i=0;i<jobs.size();i++){
                for (int j=0;j<jobs.size();j++) {
                    if(jobs.get(j).getFirstIndex() == i) {
                        String w = "";
                        for (int k=0;k<jobs.get(j).getPos().size();k++){
                            w += ""+jobs.get(j).getPos().get(k)+" ";
                        }
                        System.out.println(w);
                        bufferedWriter.write(w);
                        bufferedWriter.newLine();
                        break;
                    }
                }
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean check(){
        for (int i=0;i<jobsNum;i++){
            for (int j=0;j<workStationNum;j++){
                if(outputJobs.get(i).getPos().get(j) < inputJobs.get(i).getAvailableTime()) {//check that every job'position should be upper than available Time
                    System.out.println("available error");
                    System.out.println(i);
                    return false;
                }
                Job cur = outputJobs.get(i);
                int start = cur.getPos().get(j);
                int end = start + inputJobs.get(i).getWorkStationTimeSpend().get(j);
                for (int k=j+1;k<workStationNum;k++){//checking that no hit event happening between the same job in different stations
                    int start2 = cur.getPos().get(k);
                    int end2 = start2 + inputJobs.get(i).getWorkStationTimeSpend().get(k);
                    if(start < start2){
                        if(start2 < end) {
                            System.out.println("tadakhol"+i);
                            System.out.println(""+ j +"   "+k);
                            System.out.println("s1 : "+start);
                            System.out.println("s2 : "+start2);
                            System.out.println("e1 : "+end);
                            System.out.println("e2 : "+end2);
                            return false;
                        }
                    }
                    if(start == start2)
                        return false;
                    if(start > start2){
                        if(start < end2) {
                            System.out.println("tadakhool"+i);
                            System.out.println(""+ j +"   "+k);
                            System.out.println("s1 : "+start);
                            System.out.println("s2 : "+start2);
                            System.out.println("e1 : "+end);
                            System.out.println("e2 : "+end2);
                            return false;
                        }
                    }
                }
                for (int l=i+1;l<jobsNum;l++){
                    int start2 = outputJobs.get(l).getPos().get(j);
                    int end2 = start2 + inputJobs.get(l).getWorkStationTimeSpend().get(j);
                    if(start < start2){
                        if(start2 < end) {
                            System.out.println("sotooni"+i);
                            System.out.println(""+ j +"   "+l);
                            System.out.println("s1 : "+start);
                            System.out.println("s2 : "+start2);
                            System.out.println("e1 : "+end);
                            System.out.println("e2 : "+end2);
                            return false;
                        }
                    }
                    if(start == start2) {
                        System.out.println("s1=s2");
                        System.out.println(""+start +" "+start2);
                        return false;
                    }
                    if(start > start2){
                        if(start < end2) {
                            System.out.println("sotooni"+i);
                            System.out.println(""+ j +"   "+l);
                            System.out.println("s1 : "+start);
                            System.out.println("s2 : "+start2);
                            System.out.println("e1 : "+end);
                            System.out.println("e2 : "+end2);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
