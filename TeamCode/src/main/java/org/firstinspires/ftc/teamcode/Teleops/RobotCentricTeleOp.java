package org.firstinspires.ftc.teamcode.Teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
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

@TeleOp(name="Main teleop👍✅")
@Config
public class RobotCentricTeleOp extends OpMode {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry telemetry = dashboard.getTelemetry();
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    public boolean running = false;
    public Launcher launcher = new Launcher();
    public Transfer transfer = new Transfer();
    public boolean julianA = false;
    public boolean julianB = false;
    @Override
    public void init() {
        frontLeftMotor = hardwareMap.dcMotor.get("fl");
        backLeftMotor = hardwareMap.dcMotor.get("bl");
        frontRightMotor = hardwareMap.dcMotor.get("fr");
        backRightMotor = hardwareMap.dcMotor.get("br");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        // Adjust the orientation parameters to match your robot
        launcher.init(hardwareMap);
        transfer.init(hardwareMap);
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
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

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
        if(gamepad1.left_trigger>0.5){
            transfer.in();
        }
        else if(gamepad1.right_trigger>.5) {
            transfer.out();
        }else{
            transfer.stop();
        }
        if (gamepad1.a && !julianA) {
            julianA = true;
            if (!running) {
                launcher.runMotor(1);
                running = true;
            } else {
                launcher.runMotor(0);
                running = false;
            }
        }
        if (!gamepad1.a) {
            julianA = false;
        }
        if (gamepad1.b && !julianB) {
            julianB = true;
            if(launcher.getMode()) {
                launcher.changeMode(false);
            }
            else{
                launcher.changeMode(true);
            }
        }
        //launcher.setSwitcher(servoPosition);
        //launcher.setHood(hoodPos);
        if (!gamepad1.b) {
            julianB = false;
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}