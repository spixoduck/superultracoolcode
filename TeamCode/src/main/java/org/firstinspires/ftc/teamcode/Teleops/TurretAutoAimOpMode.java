package org.firstinspires.ftc.teamcode.Teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.Mechanisms.TurretAutoAimer;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class TurretAutoAimOpMode extends OpMode {
    private AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();
    private TurretAutoAimer turret = new TurretAutoAimer();
    @Override
    public void init(){
        aprilTagWebcam.init(hardwareMap, telemetry);
        turret.init(hardwareMap);

        telemetry.addLine("Initialize all mechanisms");
    }
    public void start() {
        turret.resetTimer();
    }

    @Override
    public void loop(){
        //vision logic
        aprilTagWebcam.update();
    }
}
