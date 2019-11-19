package org.firstinspires.ftc.teamcode;

public class TicksPerInch {
    public static void main(String args[]){
        double x = inchPerTick(1);
        System.out.println(1/x);
    }

    public static double inchPerTick(int ticks){
        return 4*Math.PI*(1/25)*ticks/28;
    }
}
