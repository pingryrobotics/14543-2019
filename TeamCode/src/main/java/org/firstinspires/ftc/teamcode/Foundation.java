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
        foundLeft.setPosition(0);
        foundRight.setPosition(0);
    }

    public void moveUp(){
        foundLeft.setPosition(-.9);
        foundRight.setPosition(.9);
    }

}
