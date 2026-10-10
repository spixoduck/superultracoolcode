package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class Launcher {
    private DcMotor launcher;
    private Servo hood;
    private Servo switcher;
    private DcMotor turret;
    private boolean nectarSkibidi;
    //may have to switch direction of motor.
    public void init(HardwareMap hwMap){
        launcher = hwMap.get(DcMotor.class,"launch");
        turret = hwMap.get(DcMotor.class,"turret");
        hood = hwMap.get(Servo.class,"hood");
        switcher = hwMap.get(Servo.class,"switch");
    }
    public boolean changeMode(boolean nectar){
        nectarSkibidi=nectar;
        if(nectar){
            switcher.setPosition(Constants.openPos);
        }else{
            switcher.setPosition(0);
        }
        return true;
    }
    public void runMotor(double power){
        launcher.setPower(power);
    }
    public void setHood(double angle){
        hood.setPosition(angle);
    }
    public void setTurretAngle(double angle){
//        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        turret.setTargetPosition(angle);
    }
    public boolean getNectar(){
        return nectarSkibidi;
    }
}
