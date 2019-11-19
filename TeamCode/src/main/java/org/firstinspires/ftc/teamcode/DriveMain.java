package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "BlankTest", group = "Iterative Opmode")
public class DriveMain extends OpMode {
    private Servo foundLeft;
    private Servo foundRight;
    public void init(){
        foundLeft = hardwareMap.get(Servo.class, "foundLeft");
        foundRight = hardwareMap.get(Servo.class, "foundRight");
        telemetry.addData("Status","Initialized");

    }
    public void loop(){
        telemetry.addData("ticks per inch",1/(4*Math.PI*(1/25)/28));
        if(gamepad1.a){
            foundLeft.setPosition(.5);
            foundRight.setPosition(.5);
            telemetry.addData("Foundation Servo Position","Down");
        }
        if(gamepad1.b){
            foundLeft.setPosition(.9);
            foundRight.setPosition(.1);
            telemetry.addData("Foundation Servo Position","Up");
        }
        telemetry.update();
    }
}
