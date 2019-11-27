package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private DcMotor left;
    private DcMotor right;
    private Servo ramp;
    public Intake(HardwareMap hardwareMap){
        left = hardwareMap.get(DcMotor.class, "leftIntake");
        right = hardwareMap.get(DcMotor.class, "rightIntake");
        ramp = hardwareMap.get(Servo.class, "ramp");
    }

    public void moveRampUp(){
        ramp.setPosition(.8);
    }
    public void moveRampDown(){
        ramp.setPosition(.45);
    }
    public void intake(double power){
        left.setPower(power);
        right.setPower(power);
    }
    public void outtake(double power){
        left.setPower(power);
        right.setPower(power);
    }
    public void stop(){
        left.setPower(0);
        right.setPower(0);
    }
}
