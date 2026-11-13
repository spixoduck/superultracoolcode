package org.firstinspires.ftc.teamcode.Teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.Constants;

@TeleOp(name="AutoAimTele")
@Config
public class autoAimTest extends OpMode {
    public static Follower follower;

    Pose currentPosition;
    Pose targetHive;
    public DcMotorEx launcher;
    public DcMotor transfer;
    public DcMotor intake;
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    public boolean running = false;
    public boolean julianA = false;
    public boolean julianB = false;
    double theta = 0;
    double kp=0.01 ,kd=0,kf=0.00034;
    public PIDController shootController = new PIDController(kp,0,kd);

    @Override
    public void init() {
        frontLeftMotor = hardwareMap.dcMotor.get("fl");
        backLeftMotor = hardwareMap.dcMotor.get("bl");
        frontRightMotor = hardwareMap.dcMotor.get("fr");
        backRightMotor = hardwareMap.dcMotor.get("br");
        follower = Constants.createFollower(hardwareMap);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        // Adjust the orientation parameters to match your robot
        launcher = hardwareMap.get(DcMotorEx.class, "shoot");
        launcher.setDirection(DcMotorSimple.Direction.REVERSE);
        intake = hardwareMap.get(DcMotor.class,"int");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer=hardwareMap.get(DcMotor.class,"trans");
        follower.setStartingPose(new Pose(36,12,0));
        currentPosition = follower.getPose();
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
        follower.update();
        currentPosition = follower.getPose();

        if (currentPosition.getY()>72) {
            targetHive = new Pose(36,60);
        }
        else {
            targetHive = new Pose(36,36);
        }
        //aimRobot(targetHive);
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x*0.7;
//        double y = -gamepad1.left_stick_y;
//        double x = gamepad1.left_stick_x;
//        double rx = gamepad1.right_stick_x;
//
//        follower.(y, x, rx);
//        follower.update();
        double currentSpeed = launcher.getVelocity();
        // Rotate the movement direction counter to the bot's rotation

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;
        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

        if (gamepad1.a && !julianA) {
            julianA = true;
            if (!running) {
                running = true;
            } else {
                running = false;
            }
        }
        if(running){
            setLaunchSpeed(shootController,launcher.getVelocity(),1950);
        }else{
            launcher.setPower(0);
        }
        if (!gamepad1.a) {
            julianA = false;
        }
        if(gamepad1.left_trigger>0.5) {
            intake.setPower(1);
        }else if(gamepad1.left_bumper){
            intake.setPower(-1);
        }else{
            intake.setPower(0);
        }
        if(gamepad1.right_trigger>0.5){
            transfer.setPower(.8);
        }else if(gamepad1.right_bumper){
            transfer.setPower(-.8);
        }
        else{
            transfer.setPower(0);
        }
        telemetry.addData("currentPos",currentPosition.getX()+ ", "+currentPosition.getY());
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
    public void aimRobot(Pose Hive) {
        theta = (Math.atan2(Hive.getY()-currentPosition.getY(),Hive.getX()-currentPosition.getX())) - currentPosition.getHeading();
        follower.turnToDegrees(theta);
    }
    public void setLaunchSpeed(PIDController controller, double currentSpeed, double targetSpeed){
        double error = currentSpeed - targetSpeed;
        double val = controller.calculate(error) + (kf*targetSpeed);
        launcher.setPower(val);
    }

}