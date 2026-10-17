package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.CustomIMU;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.revhub.localizers.RevHubIMU;
import com.pedropathing.revhub.localizers.TwoWheelConfig;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Constants {
    public static double openPos = 0.15;

    public static double intakeSpeed=0.8;
//    public static MecanumConfig drivetrainConfig = new MecanumConfig(
//            c -> {
//                c.frontLeftName.set("fl");
//                c.backLeftName.set("bl");
//                c.frontRightName.set("fr");
//                c.backRightName.set("br");
//                c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
//                c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
//                c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
//                c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
//                c.manualBrakeMode.set(true);
//            }
//    );

//    public static Follower create(HardwareMap h) {
//        return new Follower(
//                new PinpointLocalizer(h, localizerConfig),
//                new Mecanum(h, drivetrainConfig),
//                new Foresight(foresightConfig)
//        );
//    }
}