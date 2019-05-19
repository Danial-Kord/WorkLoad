package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<Job> jobs = new ArrayList<Job>();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();;
        for (int i =0;i<n;i++){
            int availableTime = scanner.nextInt();
            jobs.add(new Job(availableTime));
            for (int j=0;j<m;j++){
                int time = scanner.nextInt();
                jobs.get(jobs.size()-1).addTime(time);
                System.out.println("Dadad");
            }
        }

    }
}
