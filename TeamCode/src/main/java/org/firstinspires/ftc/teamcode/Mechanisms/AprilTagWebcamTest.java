package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; // Changed to LinearOpMode
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous(name="CamTest")
public class AprilTagWebcamTest extends LinearOpMode { // Changed inheritance

    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void runOpMode() {
        // 1. Initialize the camera during the INIT phase
        aprilTagWebcam.init(hardwareMap, telemetry);

        telemetry.addData("Status", "Initialized. Open Camera Stream now!");
        telemetry.update();

        // 2. This loops CONTINUOUSLY during INIT so the stream stays alive
        while (opModeInInit()) {
            aprilTagWebcam.update();
            telemetry.addData("Detections", aprilTagWebcam.getDetectedTags().size());
            for (AprilTagDetection detection : aprilTagWebcam.getDetectedTags()) {
                aprilTagWebcam.displayDetectionTelemetry(detection);
            }
            telemetry.update();
            sleep(20); // Small delay to save CPU cycles
        }

        // 3. Wait for the driver to press the START button
        waitForStart();

        // 4. This runs after START is pressed
        while (opModeIsActive()) {
            // Your autonomous movement or scoring code goes here
        }

        // 5. Clean up when stopped
        aprilTagWebcam.stop();
    }
}
