package org.firstinspires.ftc.teamcode.Teleops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Mechanisms.ColorSen;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.Launcher;

@TeleOp(name="color testing with shooter!")
@Config
public class ColorTest extends OpMode {
    public static double hoodPos;
    public static double servoPosition=0.1;
    public ColorSen color = new ColorSen();
    public Launcher launcher = new Launcher();
    public boolean julianA = false;
    public boolean julianB = false;
    public boolean servOn = false;
    public Servo servo;
    public boolean running = false;

    @Override
    public void init() {
        //color.init(hardwareMap, "ColorSen1");
        launcher.init(hardwareMap);
        servo = hardwareMap.get(Servo.class,"switch");
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
        //color.printColor(telemetry);
        //ColorSen.DetectedColor detectedColor = color.getColor();
        if (gamepad1.b && !julianB) {
            julianB = true;
            if(servOn) {
                servo.setPosition(.15);
                servOn=false;



            }
            else{
                servo.setPosition(0);
                servOn=true;
            }
        }
        //launcher.setSwitcher(servoPosition);
        //launcher.setHood(hoodPos);
        if (!gamepad1.b) {
            julianB = false;
        }
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
        //servo.setPosition(servoPosition);
        telemetry.addData("servoPosition", servoPosition);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}