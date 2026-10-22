package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.openftc.apriltag.AprilTagDetection;

public class TurretAutoAimer {
    private DcMotorEx turret;

    private double kP = 0.0001;
    private double kD = 0.0000;
    private double goalX = 0; // offset of where turret aims if camera isn't perfectly in middle
    private double lastError = 0;
    private double angleTolerage = 0.2; // how close the turret gets to the goal before it stops
    private final double MAX_POWER = 0.6;
    private double power = 0;
    private final ElapsedTime timer = new ElapsedTime();
    private void init(HardwareMap hwMap) {
        turret = hwMap.get(DcMotorEx.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setkP(double newKP) {
        kP = newKP;
    }

    public double getkP() {
        return kP;
    }
    public void setkD(double newKD) {
        kD = newKD;
    }

    public double getkD() {
        return kD;
    }

    public void resetTimer() {
        timer.reset();
    }

    public void update(AprilTagDetection id) {
        double deltaTime = timer.seconds();
        timer.reset();

        if (id == null) {
            turret.setPower(0);
            lastError = 0;
            return;
        }
        //start supa awesome PD controller
        double error = goalX - id.ftcPose.bearing;
    }

}
