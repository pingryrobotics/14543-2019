package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Foundation {
    private Servo foundLeft;
    private Servo foundRight;
    public Foundation(HardwareMap hardwareMap){
        foundLeft = hardwareMap.get(Servo.class, "foundLeft");
        foundRight = hardwareMap.get(Servo.class, "foundRight");
    }

    public void moveDown(){
        foundLeft.setPosition(.5);
        foundRight.setPosition(.5);
    }

    public void moveUp(){
        foundLeft.setPosition(.9);
        foundRight.setPosition(.9);
    }
    public double[] getServoPositions(){
        double[] arr = {foundLeft.getPosition(),foundRight.getPosition()};
        return arr;
    }

}