package org.firstinspires.ftc.teamcode.Teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.Mechanisms.TurretAutoAimer;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
@TeleOp
public class TurretAutoAimOpMode extends OpMode {
    private AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();
    private TurretAutoAimer turret = new TurretAutoAimer();

    double[] stepSizes = {0.1, 0.01, 0.001, 0.0001, 0.00001};
    int stepIndex = 2; //index to choose starting value, ex: 2 is 0.001

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
    public void loop() {
        //vision logic
        aprilTagWebcam.update();
        AprilTagClusterDetection audience =
                aprilTagWebcam.getClusterByName("BLUE AUDIENCE"); //Add alliance selection code eventually

        AprilTagClusterDetection scoring =
                aprilTagWebcam.getClusterByName("BLUE SCORING");

        AprilTagClusterDetection cluster = null;

        // Check if the audience side cell is scorable
        if (audience != null && audience.ftcPose != null) {
            if (Math.abs(audience.ftcPose.roll) < 90) {
                cluster = audience;
            }
        }

        // Check if the scoring side cell is scorable
        if (scoring != null && scoring.ftcPose != null) {
            if (Math.abs(scoring.ftcPose.roll) < 90) {

                // If both qualify, select the one requiring the smaller turn from the camera
                if (cluster == null || Math.abs(scoring.ftcPose.bearing) < Math.abs(cluster.ftcPose.bearing)) {
                    cluster = scoring;
                }
            }
        }
        if (gamepad1.y) {
            turret.update(cluster);
        }
        else {
            turret.update(null);
        }


        //use gamepad buttons to update P and D
        // B button cycles through the step sizes for tuning
        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        // D-pad left/right changes the P gain
        if (gamepad1.dpadLeftWasPressed()) {
            turret.setkP(turret.getkP()-stepSizes[stepIndex]);
        }
        if (gamepad1.dpadRightWasPressed()) {
            turret.setkP(turret.getkP()+stepSizes[stepIndex]);
        }
        //D-pad up/down changes the D gain
        if (gamepad1.dpadUpWasPressed()) {
            turret.setkD(turret.getkD()+stepSizes[stepIndex]);
        }
        if (gamepad1.dpadDownWasPressed()) {
            turret.setkD(turret.getkD()-stepSizes[stepIndex]);
        }


        if (cluster != null) {
            telemetry.addData("Cluster", cluster.metadata.name);
            telemetry.addData("Bearing", cluster.ftcPose.bearing);
        } else {
            telemetry.addLine("No Tag Detected. Stopping Turret Motor");
        }
        telemetry.addLine("---------------------------------------");
        telemetry.addData("Tuning P","%.5f (D-Pad L/R)", turret.getkP());
        telemetry.addData("Tuning D","%.5f (D-Pad U/D)", turret.getkD());
        telemetry.addData("Step Size","%.5f (B Button)", stepSizes[stepIndex]);
    }
}
