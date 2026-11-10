package org.firstinspires.ftc.teamcode.Teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Mechanisms.PIDFController;

import java.util.ArrayList;

@TeleOp(name="shooter tuning")
@Config
public class shootertuning extends OpMode {
    public ArrayList<Double> speeds = new ArrayList<Double>();
    int counter =0;
    double speedSum =0;
    public static double kp=0.08 ,ki,kd=0.00001,kf=0.00034,targetSpeed;
    public static int window=10;
    public DcMotorEx launcher;
    public static double DASH_TEST = 1.0;
    public DcMotor transfer;
    public Timer timer = new Timer();
    public double pastPosition,pastSpeed;
    public DcMotor intake;
    public PIDController speedController = new PIDController(kp,ki,kd);
    FtcDashboard dashboard;

    public boolean running = false;

    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance();

        telemetry = new MultipleTelemetry(
                telemetry,
                dashboard.getTelemetry()
        );

        launcher = hardwareMap.get(DcMotorEx.class, "shoot");
        launcher.setDirection(DcMotorSimple.Direction.REVERSE);
        intake = hardwareMap.get(DcMotor.class,"int");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer=hardwareMap.get(DcMotor.class,"trans");
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        telemetry.addData("started", "true");
        telemetry.update();
    }

    @Override
    public void loop() {
        speedController.setPID(kp, ki, kd);
        double currentSpeed = launcher.getVelocity();
        speedSum += currentSpeed;
        telemetry.addData("currentSpeed",currentSpeed);
        speeds.add(currentSpeed);
        while (speeds.size() > window){
            speedSum -= speeds.remove(0);
        }
        currentSpeed = speedSum/speeds.size();
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


//        if (gamepad1.a && !julianA) {
//            julianA = true;
//            if (!running) {
//                setLaunchSpeed(currentSpeed-pastSpeed,targetSpeed);
//                running = true;
//            } else {
//                launcher.setPower(0);
//                running = false;
//            }
//        }
//        if (!gamepad1.a) {
//            julianA = false;
//        }

        setLaunchSpeed(currentSpeed,targetSpeed);
        if(gamepad1.left_trigger>0.5){
            intake.setPower(1);
        }else{
            intake.setPower(0);
        }
        if(gamepad1.right_trigger>0.5){
            transfer.setPower(1);
        }else{
            transfer.setPower(0);
        }


        telemetry.addData("dashTest", DASH_TEST);
        telemetry.addData("speedSum",speedSum);
        telemetry.addData("listSize",speeds.size());
        telemetry.addData("shooter speed",currentSpeed);
        telemetry.addData("targetSpeed",targetSpeed);
//        telemetry.addData("power",speedController.get());
        telemetry.update();
        //servo.setPosition(servoPosition);
        pastPosition=launcher.getCurrentPosition();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
    public void setLaunchSpeed(double currentSpeed,double targetSpeed){
        double error = currentSpeed - targetSpeed;
        double val = speedController.calculate(error) + (kf*targetSpeed);
        launcher.setPower(val);
    }

}