package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Launcher {
    private DcMotor launcher;
    private Servo hood;
    private Servo switcher;
    private DcMotor turret;
    private boolean pollen;
    public void init(HardwareMap hwMap){
        launcher = hwMap.get(DcMotor.class,"launch");
        turret = hwMap.get(DcMotor.class,"turret");
        hood = hwMap.get(Servo.class,"hood");
        switcher = hwMap.get(Servo.class,"switch");
    }
    public boolean changeMode(boolean Nectar){
        switcher.setPosition(0);
        return true;
    }
}
