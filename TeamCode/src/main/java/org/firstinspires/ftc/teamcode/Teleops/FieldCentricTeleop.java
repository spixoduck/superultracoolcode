package org.firstinspires.ftc.teamcode.Teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.Launcher;
import org.firstinspires.ftc.teamcode.Mechanisms.Transfer;
import org.firstinspires.ftc.teamcode.drive.Constants;

@TeleOp(name="Auto Aim testing fieldcentric")
@Config
public class FieldCentricTeleop extends OpMode {
    public static double kp=1, ki, kd, targetAngle;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry telemetry = dashboard.getTelemetry();
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    Follower follower;
    IMU imu;
    double minPos=36;
    double maxPos=36;
    boolean autoAim = false;
    PIDController rotationController = new PIDController(kp,ki,kd);
//    public boolean running = false;
//    public Launcher launcher = new Launcher();
//    public Transfer transfer = new Transfer();
//    public boolean julianA = false;
//    public boolean julianB = false;
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(36,12,0));
        frontLeftMotor = hardwareMap.dcMotor.get("fl");
        backLeftMotor = hardwareMap.dcMotor.get("bl");
        frontRightMotor = hardwareMap.dcMotor.get("fr");
        backRightMotor = hardwareMap.dcMotor.get("br");
        imu  = hardwareMap.get(IMU.class, "imu");
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // initializing IMU, can probably replace all of the IMU code with currentPosition.getHeading() but i wouldn't know how to reset heading for that so for teleop this is prolly better until I figure that out;
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        imu.initialize(parameters);

//        launcher.init(hardwareMap);
//        transfer.init(hardwareMap);
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
        Pose currentPose = follower.getPose();
        rotationController.setPID(kp,ki,kd); // setting the pid coefficients so they actually change when you change them in dashboard
        follower.update(); //updates the follower.  you: "no way say ong rn"
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        // resets the imu so you can manually set frame of reference or forwards backwards and such
        //now that I think about it putting this in our auto code would eliminate the need to reset the robot all the time
        if (gamepad1.options) {
            imu.resetYaw();
        }
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        //this is just the difference between our current heading and the heading we want (towards the goal) it's just a changeable variable for now for tuning purposes
        targetAngle = Math.atan2(36-currentPose.getY(),36-currentPose.getX());
        double error = polarDistance(botHeading,targetAngle); //after testing replace targetAngle with Math.atan2(36-currentPosition.getY(),36-currentPosition.getX())
        //this is what the rotation component of the robot will be, it checks if autoAim is true or false and either makes it change due to player input or the PIDController
        double rx = autoAim ? rotationController.calculate(error) : gamepad1.right_stick_x; // this is a trick I learned from leetcode lmao
        //another way to write this is
        //if(autoAim)
        //    rx=val;
        //else
        //    rx=gamepad1.right_stick_x;

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading); //part of fieldcentric code. this is a rotation matrix, just rotates x and y so that they're the same no matter orientation of the robot
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;
        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
        autoAim=gamepad1.a;
        //telemetry to make sure our follower is giving us the right data.
        minPos = Math.min(currentPose.getX(),minPos);
        maxPos = Math.max(currentPose.getX(),maxPos);
        telemetry.addData("currentPos",currentPose.getX()+ ", "+currentPose.getY() + ", "+ currentPose.getHeading());
        telemetry.addData("range",maxPos-minPos);
        telemetry.update();
    }
    public double polarDistance(double angle1, double angle2){
        return Math.acos(Math.cos(angle1-angle2))*Math.signum(Math.sin(angle2-angle1));
    }
    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}