package org.firstinspires.ftc.teamcode.pedro;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.follower.Follower;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import org.firstinspires.ftc.teamcode.pedro.Constants;
@Autonomous
public class SigmaAuto extends OpMode {
    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    private final Pose startPose = p.of(24,24,0);
    private final Pose park = p.of(24,24,90);
    //poses from before
    private Path park() {
        return line(startPose, park).linear(startPose, park);
    }
    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

    }
}