
package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous
public class AprilTagWebcamTest extends OpMode {

    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void init() {
        aprilTagWebcam.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        // Update the detected AprilTags and clusters
        aprilTagWebcam.update();

        // Show how many detections were found
        telemetry.addData("Detections",
                aprilTagWebcam.getDetectedTags().size());

        // Display every detection
        for (AprilTagDetection detection :
                aprilTagWebcam.getDetectedTags()) {

            aprilTagWebcam.displayDetectionTelemetry(detection);
        }

        // Send telemetry to Driver Station
        telemetry.update();
    }

    @Override
    public void stop() {
        aprilTagWebcam.stop();
    }
}
