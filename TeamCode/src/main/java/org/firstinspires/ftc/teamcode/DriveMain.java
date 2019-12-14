package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "BlankTest", group = "Iterative Opmode")
public class DriveMain extends OpMode {
    private Foundation foundation;
    private Intake intake;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftRear = null;
    private DcMotor rightRear = null;
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    public void init(){
        foundation = new Foundation(hardwareMap);
        intake = new Intake(hardwareMap);
        leftRear = hardwareMap.get(DcMotor.class, "leftBack");
        rightRear = hardwareMap.get(DcMotor.class, "rightBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status","Initialized");

    }
    public void loop(){
        double theta = Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
        double magnitude = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2));
        double turn = -Range.clip(gamepad1.right_stick_x, -1, 1);
        double rf = Math.sin(theta + (Math.PI/4)) * magnitude;
        double lf = Math.sin(theta - (Math.PI/4)) * magnitude;
        double rb = Math.sin(theta - (Math.PI/4)) * magnitude;
        double lb = Math.sin(theta + (Math.PI/4)) * magnitude;

        leftRear.setPower(lb + turn);
        rightRear.setPower(rb - turn);
        leftFront.setPower(lf + turn);
        rightFront.setPower(rf - turn);

        if(gamepad1.y){
            foundation.moveUp();
        }
        else if(gamepad1.b){
            foundation.moveDown();
        }

        while(gamepad1.right_trigger >= 0.3){
            intake.intake(gamepad1.right_trigger);
        }
        while(gamepad1.left_trigger >= 0.3){
            intake.outtake(gamepad1.left_trigger);
        }


        telemetry.update();
    }
}
