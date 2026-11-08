package org.firstinspires.ftc.teamcode.Mechanisms;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class Launcher {
    private DcMotorEx launcher;
    private Servo hood;
    private Servo switcher;
    private PIDController speedController;
    private DcMotor turret;
    private final double kp=0.008 ,kd=0.00001,kf=0.00039;
    private boolean nectarSkibidi;
    //may have to switch direction of motor.
    public void init(HardwareMap hwMap){
        speedController = new PIDController(kp,0,kd);
        launcher = hwMap.get(DcMotorEx.class,"shoot"); //change this to shoot
        launcher.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void setSpeed(double target){
        double error = launcher.getVelocity() - target;
        double val = speedController.calculate(error) + (kf*target);
        launcher.setPower(val);
    }
    public void setPower(double power){
        launcher.setPower(power);
    }
}
