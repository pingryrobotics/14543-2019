package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by maryjaneb  on 11/13/2016.
 *
 * nerverest ticks
 * 60 1680
 * 40 1120
 * 20 560
 *
 * monitor: 640 x 480
 *YES
 */
@Autonomous(name= "SkyStoneAutoBlue", group="Sky autonomous")
//@Disabled//comment out this line before using
public class SkyStoneAutoBlue extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //0 means skystone, 1 means yellow stone
    //-1 for debug, but we can keep it like this because if it works, it should change to either 0 or 255
    private static int valMid = -1;
    private static int valLeft = -1;
    private static int valRight = -1;

    private static float rectHeight = .6f/8f;
    private static float rectWidth = 1.5f/8f;

    private static float offsetX = 1.9f/8f;//changing this moves the three rects and the three circles left or right, range : (-2, 2) not inclusive
    private static float offsetY = -2.7f/8f;//changing this moves the three rects and circles up or down, range: (-4, 4) not inclusive

    private static float[] midPos = {4f/8f+offsetX, 4f/8f+offsetY};//0 = col, 1 = row
    private static float[] leftPos = {2f/8f+offsetX, 4f/8f+offsetY};
    private static float[] rightPos = {6f/8f+offsetX, 4f/8f+offsetY};
    //moves all rectangles right or left by amount. units are in ratio to monitor

    private final int rows = 320;
    private final int cols = 240;

    OpenCvCamera webcam;
    private Mecanum mecanum;
    private Servo stoneArm;


    @Override
    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);


        webcam.openCameraDevice();


        webcam.setPipeline(new StageSwitchingPipeline());
        mecanum = new Mecanum(hardwareMap);
        stoneArm = hardwareMap.get(Servo.class, "stoneArm");

        /*
         * Tell the webcam to start streaming images to us! Note that you must make sure
         * the resolution you specify is supported by the camera. If it is not, an exception
         * will be thrown.
         *
         * Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
         * supports streaming from the webcam in the uncompressed YUV image format. This means
         * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
         * Streaming at 720p will limit you to up to 10FPS. However, streaming at frame rates other
         * than 30FPS is not currently supported, although this will likely be addressed in a future
         * release. TLDR: You can't stream in greater than 480p from a webcam at the moment.
         *
         * Also, we specify the rotation that the webcam is used in. This is so that the image
         * from the camera sensor can be rotated such that it is always displayed with the image upright.
         * For a front facing camera, rotation is defined assuming the user is looking at the screen.
         * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
         * away from the user.
         */
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);



        //width, height
        //width = height in this case, because camera is in portrait mode.

        waitForStart();
        runtime.reset();

        boolean detected = false;

        while (!detected) {
            telemetry.addData("Values", valLeft+"   "+valMid+"   "+valRight);
            telemetry.addData("Height", rows);
            telemetry.addData("Width", cols);

            telemetry.update();
            sleep(100);
            //call movement functions
//            strafe(0.4, 200);
//            moveDistance(0.4, 700)
            if(valLeft == 0){
                telemetry.addData("Values", valLeft+"   "+valMid+"   "+valRight);
                telemetry.addData("Height", rows);
                telemetry.addData("Width", cols);

                telemetry.update();
                //move toward stones
                mecanum.moveEncoderStraight(-2,.4);
                waitForEncoders();
                mecanum.rawMove(-.4,.4,.4,-.4);
                twait(2950);
                mecanum.stop();
                twait(700);
                detected = true;

                // mecanum.moveEncoderStraight(19.5,.5);
                stoneArm.setPosition(.4);
                twait(600);
                mecanum.moveStrafe(19,.4);
                waitForEncoders();
                //mecanum.rawMove(.4,-.4,-.4,.4);
                //twait(3000);
                //mecanum.stop();
                //twait(200);
                mecanum.moveEncoderStraight(-55,.6);
                waitForEncoders();
                stoneArm.setPosition(.9);
                twait(600);
                mecanum.moveEncoderStraight(16,.6);
                waitForEncoders();
                mecanum.moveStrafe(5,.4);
                waitForEncoders();


            }
            else if(valMid == 0){
                telemetry.addData("Values", valLeft+"   "+valMid+"   "+valRight);
                telemetry.addData("Height", rows);
                telemetry.addData("Width", cols);
                detected = true;
                telemetry.update();
                mecanum.moveEncoderStraight(7.5,.4);
                waitForEncoders();
                mecanum.rawMove(-.4,.4,.4,-.4);
                twait(2970);
                mecanum.stop();
                twait(700);

                waitForEncoders();
                stoneArm.setPosition(.4);
                twait(700);
               mecanum.moveStrafe(19,.4);
                waitForEncoders();
                //mecanum.rawMove(.4,-.4,-.4,.4);
                //twait(3000);
                //mecanum.stop();
                //twait(200);
                mecanum.moveEncoderStraight(-55,.6);
                waitForEncoders();
                stoneArm.setPosition(.9);
                twait(600);
                mecanum.moveEncoderStraight(16,.6);
                waitForEncoders();
                mecanum.moveStrafe(5,.4);
                waitForEncoders();

            }
            else{
                telemetry.addData("Values", valLeft+"   "+valMid+"   "+valRight);
                telemetry.addData("Height", rows);
                telemetry.addData("Width", cols);
                detected = true;
                telemetry.update();
                mecanum.moveEncoderStraight(15,.4);
                waitForEncoders();
                mecanum.rawMove(-.4,.4,.4,-.4);
                twait(2990);
                mecanum.stop();
                twait(200);

                waitForEncoders();
                stoneArm.setPosition(.4);
                twait(600);
               mecanum.moveStrafe(19,.4);
                waitForEncoders();
                //mecanum.rawMove(.4,-.4,-.4,.4);
                //twait(3000);
                //mecanum.stop();
                //twait(200);
                mecanum.moveEncoderStraight(-55,.4);
                waitForEncoders();
                stoneArm.setPosition(.9);
                twait(600);
                mecanum.moveEncoderStraight(16,.4);
                waitForEncoders();
                mecanum.moveStrafe(5,.4);
                waitForEncoders();

                /*
                mecanum.moveStrafe(-42,.4);
                waitForEncoders();
                detected = true;
                mecanum.moveEncoderStraight(16,.4);
                waitForEncoders();
                stoneArm.setPosition(.4);
                twait(2000);
                mecanum.moveStrafe(42,.4);
                mecanum.moveEncoderStraight(38,.4);

                 */
            }

        }



    }
    public void waitForEncoders(){
        while(!mecanum.oneEncoderDone()&&opModeIsActive() && runtime.milliseconds() < 7000);
        mecanum.resetEncoders();
        runtime.reset();
    }
    public void twait(long millis) throws InterruptedException{
        Thread.sleep(millis);
    }
    //detection pipeline
    static class StageSwitchingPipeline extends OpenCvPipeline
    {
        Mat yCbCrChan2Mat = new Mat();
        Mat thresholdMat = new Mat();
        Mat all = new Mat();
        List<MatOfPoint> contoursList = new ArrayList<>();

        enum Stage
        {//color difference. greyscale
            detection,//includes outlines
            THRESHOLD,//b&w
            RAW_IMAGE,//displays raw view
        }

        private Stage stageToRenderToViewport = Stage.detection;
        private Stage[] stages = Stage.values();

        @Override
        public void onViewportTapped()
        {
            /*
             * Note that this method is invoked from the UI thread
             * so whatever we do here, we must do quickly.
             */

            int currentStageNum = stageToRenderToViewport.ordinal();

            int nextStageNum = currentStageNum + 1;

            if(nextStageNum >= stages.length)
            {
                nextStageNum = 0;
            }

            stageToRenderToViewport = stages[nextStageNum];
        }

        @Override
        public Mat processFrame(Mat input)
        {
            contoursList.clear();
            /*
             * This pipeline finds the contours of yellow blobs such as the Gold Mineral
             * from the Rover Ruckus game.
             */

            //color diff cb.
            //lower cb = more blue = skystone = white
            //higher cb = less blue = yellow stone = grey
            Imgproc.cvtColor(input, yCbCrChan2Mat, Imgproc.COLOR_RGB2YCrCb);//converts rgb to ycrcb
            Core.extractChannel(yCbCrChan2Mat, yCbCrChan2Mat, 2);//takes cb difference and stores

            //b&w
            Imgproc.threshold(yCbCrChan2Mat, thresholdMat, 102, 255, Imgproc.THRESH_BINARY_INV);

            //outline/contour
            Imgproc.findContours(thresholdMat, contoursList, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            yCbCrChan2Mat.copyTo(all);//copies mat object
            //Imgproc.drawContours(all, contoursList, -1, new Scalar(255, 0, 0), 3, 8);//draws blue contours


            //get values from frame
            double[] pixMid = thresholdMat.get((int)(input.rows()* midPos[1]), (int)(input.cols()* midPos[0]));//gets value at circle
            valMid = (int)pixMid[0];

            double[] pixLeft = thresholdMat.get((int)(input.rows()* leftPos[1]), (int)(input.cols()* leftPos[0]));//gets value at circle
            valLeft = (int)pixLeft[0];

            double[] pixRight = thresholdMat.get((int)(input.rows()* rightPos[1]), (int)(input.cols()* rightPos[0]));//gets value at circle
            valRight = (int)pixRight[0];

            //create three points
            Point pointMid = new Point((int)(input.cols()* midPos[0]), (int)(input.rows()* midPos[1]));
            Point pointLeft = new Point((int)(input.cols()* leftPos[0]), (int)(input.rows()* leftPos[1]));
            Point pointRight = new Point((int)(input.cols()* rightPos[0]), (int)(input.rows()* rightPos[1]));

            //draw circles on those points
            Imgproc.circle(all, pointMid,5, new Scalar( 255, 0, 0 ),1 );//draws circle
            Imgproc.circle(all, pointLeft,5, new Scalar( 255, 0, 0 ),1 );//draws circle
            Imgproc.circle(all, pointRight,5, new Scalar( 255, 0, 0 ),1 );//draws circle

            //draw 3 rectangles
            Imgproc.rectangle(//1-3
                    all,
                    new Point(
                            input.cols()*(leftPos[0]-rectWidth/2),
                            input.rows()*(leftPos[1]-rectHeight/2)),
                    new Point(
                            input.cols()*(leftPos[0]+rectWidth/2),
                            input.rows()*(leftPos[1]+rectHeight/2)),
                    new Scalar(0, 255, 0), 3);
            Imgproc.rectangle(//3-5
                    all,
                    new Point(
                            input.cols()*(midPos[0]-rectWidth/2),
                            input.rows()*(midPos[1]-rectHeight/2)),
                    new Point(
                            input.cols()*(midPos[0]+rectWidth/2),
                            input.rows()*(midPos[1]+rectHeight/2)),
                    new Scalar(0, 255, 0), 3);
            Imgproc.rectangle(//5-7
                    all,
                    new Point(
                            input.cols()*(rightPos[0]-rectWidth/2),
                            input.rows()*(rightPos[1]-rectHeight/2)),
                    new Point(
                            input.cols()*(rightPos[0]+rectWidth/2),
                            input.rows()*(rightPos[1]+rectHeight/2)),
                    new Scalar(0, 255, 0), 3);

            switch (stageToRenderToViewport)
            {
                case THRESHOLD:
                {
                    return thresholdMat;
                }

                case detection:
                {
                    return all;
                }

                case RAW_IMAGE:
                {
                    return input;
                }

                default:
                {
                    return input;
                }
            }
        }

    }
}


