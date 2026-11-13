package org.firstinspires.ftc.teamcode.drive;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(20);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)

            .rightFrontMotorName("fr")
            .rightRearMotorName("br")
            .leftRearMotorName("bl")
            .leftFrontMotorName("fl")

            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE);

    public static TwoWheelConstants localizerConstants = new TwoWheelConstants()

            // strafe 3, forward 2
            // 2 is br, 3 is bl
            .forwardEncoder_HardwareMapName("br")
            .strafeEncoder_HardwareMapName("bl")
            .strafeEncoderDirection(Encoder.REVERSE)
            .forwardEncoderDirection(Encoder.FORWARD)
            .forwardPodY(3)
            .strafePodX(5.51870079)
            .forwardTicksToInches((Math.PI*48)/(25.4*2000))
            .strafeTicksToInches((Math.PI*32)/(25.4*2000))
            .IMU_HardwareMapName("imu")
            .IMU_Orientation(
                    new RevHubOrientationOnRobot(
                            RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                            RevHubOrientationOnRobot.UsbFacingDirection.DOWN
                    )
            );

    public static Follower createFollower(HardwareMap hardwareMap) {
        Follower follower = new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .twoWheelLocalizer(localizerConstants)
                .build();

        return follower;
    }
}