package org.firstinspires.ftc.teamcode.Mechanisms;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;
import java.util.List;

public class AprilTagWebcam {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    private List<AprilTagDetection> detectedTags = new ArrayList<>();

    private Telemetry telemetry;

    public void init(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hwMap.get(WebcamName.class, "WebcamTest")); // change according to name set on driver hub
        builder.setCameraResolution(new Size(640,480 )); // change according to camera
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();


    }

    public void update() {
        detectedTags = aprilTagProcessor.getDetections();
    }

    public List<AprilTagDetection> getDetectedTags() {
        return detectedTags;
    }


    public void displayDetectionTelemetry(AprilTagDetection detectedId) {
        if (detectedId == null) {
            return;
        }

        if (detectedId instanceof AprilTagSingleDetection) {
            AprilTagSingleDetection singleDet =
                    (AprilTagSingleDetection) detectedId;

            if (singleDet.metadata != null) {
                telemetry.addLine(String.format(
                        "\n==== (ID %d) %s",
                        singleDet.id, singleDet.metadata.name));
            } else {
                telemetry.addLine(String.format(
                        "\n==== (ID %d) Unknown", singleDet.id));
                telemetry.addLine(String.format(
                        "Center %6.0f %6.0f (pixels)",
                        singleDet.center.x, singleDet.center.y));
                return;
            }

        } else if (detectedId instanceof AprilTagClusterDetection) {
            AprilTagClusterDetection clusterDet =
                    (AprilTagClusterDetection) detectedId;

            telemetry.addLine(String.format(
                    "\n==== Cluster: %s", clusterDet.metadata.name));

            telemetry.addLine(String.format(
                    "Percent tags found: %d%%",
                    clusterDet.percentClusterFound));
        } else {
            return;
        }

        // Display position data for both single tags and clusters
        if (detectedId.ftcPose != null) {
            telemetry.addLine(String.format(
                    "XYZ %6.1f %6.1f %6.1f (cm)",
                    detectedId.ftcPose.x,
                    detectedId.ftcPose.y,
                    detectedId.ftcPose.z));

            telemetry.addLine(String.format(
                    "PRY %6.1f %6.1f %6.1f (deg)",
                    detectedId.ftcPose.pitch,
                    detectedId.ftcPose.roll,
                    detectedId.ftcPose.yaw));

            telemetry.addLine(String.format(
                    "RBE %6.1f %6.1f %6.1f (cm, deg, deg)",
                    detectedId.ftcPose.range,
                    detectedId.ftcPose.bearing,
                    detectedId.ftcPose.elevation));
        }
    }




    public AprilTagDetection getTagBySpecificId(int id) {
        for (AprilTagDetection detection : detectedTags) {

            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection singleDet =
                        (AprilTagSingleDetection) detection;

                if (singleDet.id == id) {
                    return singleDet;
                }
            }
        }
        return null;
    }

    public AprilTagClusterDetection getClusterByName(String name) {
        for (AprilTagDetection detection : detectedTags) {

            if (detection instanceof AprilTagClusterDetection) {

                AprilTagClusterDetection cluster =
                        (AprilTagClusterDetection) detection;

                if (cluster.metadata.name.equals(name)) {
                    return cluster;
                }
            }
        }

        return null;
    }


    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
