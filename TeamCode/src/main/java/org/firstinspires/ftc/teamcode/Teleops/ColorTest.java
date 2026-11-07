package org.firstinspires.ftc.teamcode.Teleops;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.controllers.PIDController;
import com.pedropathing.utils.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Mechanisms.ColorSen;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.Launcher;

import java.util.concurrent.TimeUnit;

@TeleOp(name="color testing with shooter!")
@Config
public class ColorTest extends OpMode {
    public static double kp,ki,kd;
    public static double hoodPos;
    public static double servoPosition=0.1;
    public DcMotor launcher;
    public DcMotor transfer;
    public Timer timer = new Timer();
    public double pastTime, pastPosition;
    public DcMotor intake;
    public PIDController speedController = new PIDController(kp,ki,kd);
    public boolean julianA = false;
    public boolean julianB = false;
    public boolean servOn = false;
    public Servo servo;
    public boolean running = false;

    @Override
    public void init() {
        //color.init(hardwareMap, "ColorSen1");
        launcher = hardwareMap.get(DcMotor.class, "shoot");
        launcher.setDirection(DcMotorSimple.Direction.REVERSE);
        intake = hardwareMap.get(DcMotor.class,"int");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer=hardwareMap.get(DcMotor.class,"trans");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {

    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
        pastPosition=launcher.getCurrentPosition();
        pastTime=timer.seconds();
        //color.printColor(telemetry);
        //ColorSen.DetectedColor detectedColor = color.getColor();
        //launcher.setSwitcher(servoPosition);
        //launcher.setHood(hoodPos);
//        if(detectedColor== ColorSen.DetectedColor.BLUE||detectedColor== ColorSen.DetectedColor.RED){
//            launcher.changeMode(true);
//        }else{
//            launcher.changeMode(false);
//        }
//        telemetry.addData("openPos", Constants.openPos);
//        telemetry.update();
//        //
//        // Constants.openPos = servoPosition;
        if (gamepad1.a && !julianA) {
            julianA = true;
            if (!running) {
                launcher.setPower(1);
                running = true;
            } else {
                launcher.setPower(0);
                running = false;
            }
        }
        if (!gamepad1.a) {
            julianA = false;
        }
        if(gamepad1.left_trigger>0.5){
            intake.setPower(.6);
        }else{
            intake.setPower(0);
        }
        if(gamepad1.right_trigger>0.5){
            transfer.setPower(.6);
        }else{
            transfer.setPower(0);
        }
        double currentSpeed = (launcher.getCurrentPosition()-pastPosition)/(timer.seconds()-pastTime);
        telemetry.addData("shooter speed",currentSpeed);

        //servo.setPosition(servoPosition);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}