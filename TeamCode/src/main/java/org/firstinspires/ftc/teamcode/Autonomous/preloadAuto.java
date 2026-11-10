package org.firstinspires.ftc.teamcode.Autonomous;

import com.bylazar.configurables.PanelsConfigurables;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Mechanisms.Launcher;
import org.firstinspires.ftc.teamcode.drive.Constants;


@Autonomous(name="Preload-park auto")
public class preloadAuto extends OpMode {
    public static Follower follower;
    Timer pathTimer =new Timer();
    public int pathState=0;
    static TelemetryManager telemetryM;
    public DcMotor transfer;
    public DcMotor intake;
    private Path path1,path2,path3,path4;
    private Path backwards;
    Launcher launcher = new Launcher();
    boolean launching = true;
    boolean chrisisgay = true;

    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class,"int");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer=hardwareMap.get(DcMotor.class,"trans");
        path1 = new Path(new BezierLine(new Pose(0,0), new Pose(0,-3)));
        path1.setConstantHeadingInterpolation(Math.toRadians(90));
        path2 = new Path(new BezierLine(new Pose(0,-3), new Pose(24,-3)));
        path2.setConstantHeadingInterpolation(Math.toRadians(90));
        path3 = new Path(new BezierLine(new Pose(24,-3), new Pose(24,-90)));
        path3.setConstantHeadingInterpolation(Math.toRadians(90));
        path4 = new Path(new BezierLine(new Pose(24,-90), new Pose(40,-90)));
        path4.setConstantHeadingInterpolation(Math.toRadians(90));
        launcher.init(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        PanelsConfigurables.INSTANCE.refreshClass(this);
        follower.setStartingPose(new Pose(0,0,Math.toRadians(90)));

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

//        backwards = new Path(new BezierLine(new Pose(36,48), new Pose(36,72)));
//        backwards.setConstantHeadingInterpolation(0);
    }

    @Override
    public void loop() {

        follower.update();
        followPath();

        if(launching){
            launcher.setSpeed(2000);
        }else{
            launcher.setPower(0);
        }
        telemetryM.update(telemetry);
    }
    public void followPath(){
        switch (pathState){
            case 0 :
                launching=true;
                follower.followPath(path1);
                setPath(1);
                break;
            case 1:
                if(!follower.isBusy()&&pathTimer.getElapsedTimeSeconds()>3.0){
                    intake.setPower(1);
                    transfer.setPower(1);
                    setPath(2);
                    pathTimer.resetTimer();
                }
                break;
            case 2:
                if(!follower.isBusy()&&pathTimer.getElapsedTimeSeconds()>5.0){
                    launching=false;
                    intake.setPower(0);
                    transfer.setPower(0);
                    launcher.setPower(0);
                    follower.followPath(path2);
                    setPath(3);
                }
                break;
            case 3:
                if(!follower.isBusy()){
                    follower.followPath(path3);
                    setPath(4);
                }
                break;
            case 4:
                if(!follower.isBusy()){
                    follower.followPath(path4);
                    setPath(5);
                }
                break;
        }
    }
    public void setPath(int pathNum){
        pathTimer.resetTimer();
        pathState=pathNum;
    }
}