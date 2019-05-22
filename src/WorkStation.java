
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
}
