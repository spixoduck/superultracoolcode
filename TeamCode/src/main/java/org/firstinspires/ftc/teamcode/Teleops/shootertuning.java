package org.firstinspires.ftc.teamcode.Teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.utils.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Mechanisms.PIDFController;

@TeleOp(name="color testing with shooter!")
@Config
public class shootertuning extends OpMode {
    public static double kp=0.001,ki,kd,kf,targetSpeed;
    public static double hoodPos;
    public static double servoPosition=0.1;
    public DcMotor launcher;
    public static double DASH_TEST = 1.0;
    public DcMotor transfer;
    public Timer timer = new Timer();
    public double pastTime, pastPosition;
    public DcMotor intake;
    public PIDFController speedController = new PIDFController(kp,ki,kd,kf);
    FtcDashboard dashboard;
    public boolean running = false;

    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance();

        telemetry = new MultipleTelemetry(
                telemetry,
                dashboard.getTelemetry()
        );

        launcher = hardwareMap.get(DcMotor.class, "shoot");
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
        speedController.setPIDFValues(kp,ki,kd,kf);
        double currentSpeed = (launcher.getCurrentPosition()-pastPosition)/(timer.seconds()-pastTime);
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
            intake.setPower(.6);
        }else{
            intake.setPower(0);
        }
        if(gamepad1.right_trigger>0.5){
            transfer.setPower(.6);
        }else{
            transfer.setPower(0);
        }


        telemetry.addData("dashTest", DASH_TEST);

        telemetry.addData("shooter speed",currentSpeed);
        telemetry.addData("targetSpeed",targetSpeed);
        telemetry.addData("power",speedController.run());
        telemetry.update();
        //servo.setPosition(servoPosition);
        pastPosition=launcher.getCurrentPosition();
        pastTime=timer.seconds();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
    public void setLaunchSpeed(double currentSpeed,double targetSpeed){
        speedController.setTargetPosition(targetSpeed);
        speedController.updatePosition(currentSpeed);
        launcher.setPower(speedController.run());
    }

}