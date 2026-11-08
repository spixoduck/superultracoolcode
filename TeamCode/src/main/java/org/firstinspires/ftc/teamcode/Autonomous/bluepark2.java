package org.firstinspires.ftc.teamcode.Autonomous;

import com.bylazar.configurables.PanelsConfigurables;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.PoseHistory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.Constants;


@Autonomous(name="parkAuto")
public class bluepark2 extends OpMode {
    public static Follower follower;

    static TelemetryManager telemetryM;
    private Path forwards;
    private Path backwards;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        PanelsConfigurables.INSTANCE.refreshClass(this);
        follower.setStartingPose(new Pose(0,0, Math.toRadians(90)));
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }


    /** This initializes the Follower and creates the forward and backward Paths. */
    @Override
    public void init_loop() {

        follower.update();
    }

    @Override
    public void start() {
        follower.activateAllPIDFs();
        forwards = new Path(new BezierLine(new Pose(0,0), new Pose(-24,0)));
        forwards.setConstantHeadingInterpolation(Math.toRadians(90));
//        backwards = new Path(new BezierLine(new Pose(-36,48, Math.toRadians(90)), new Pose(-36,72, Math.toRadians(90))));
//        backwards.setConstantHeadingInterpolation(Math.toRadians(90));
        follower.followPath(forwards);
    }

    @Override
    public void loop() {
        follower.update();

//        if (!follower.isBusy()) {
//            follower.followPath(backwards);
//        }

        telemetryM.update(telemetry);
    }
}