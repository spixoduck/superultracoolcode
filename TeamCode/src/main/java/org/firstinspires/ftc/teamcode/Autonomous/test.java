package org.firstinspires.ftc.teamcode.Autonomous;

import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.PoseHistory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


public class test extends OpMode {
    public static Follower follower;


    static TelemetryManager telemetryM;
    private Path forwards;
    private Path backwards;

    @Override
    public void init() {}

    /** This initializes the Follower and creates the forward and backward Paths. */
    @Override
    public void init_loop() {

        follower.update();
    }

    @Override
    public void start() {
        follower.activateAllPIDFs();
        forwards = new Path(new BezierLine(new Pose(0,0), new Pose(48,0)));
        forwards.setConstantHeadingInterpolation(0);
        backwards = new Path(new BezierLine(new Pose(48,0), new Pose(0,0)));
        backwards.setConstantHeadingInterpolation(0);
        follower.followPath(forwards);
    }

    @Override
    public void loop() {
        follower.update();

        if (!follower.isBusy()) {
            follower.followPath(backwards);
        }

        telemetryM.update(telemetry);
    }
}