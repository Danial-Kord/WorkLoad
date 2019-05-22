

public class Couple {
    private int start;
    private int end;
    private int jobPlaceInQueue;

    public Couple(int start, int timeSpend, int jobPlace) {
        this.start = start;
        this.end = timeSpend+start;
        this.jobPlaceInQueue = jobPlace;//shomare job_number of job
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setjobPlaceInQueue(int jobPlace) {
        this.jobPlaceInQueue = jobPlace;
    }
    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getjobPlaceInQueue() {
        return jobPlaceInQueue;
    }
}
