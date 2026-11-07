package org.firstinspires.ftc.teamcode.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Configurable
@Config
public class Transfer {
    private DcMotor motor1, motor2;

    public static double intakeSpeed = 0;

    public void init(HardwareMap hm){
        motor1 = hm.get(DcMotor.class,"trans1");
        motor2 = hm.get(DcMotor.class,"trans2");
        //motor1.setDirection(DcMotorSimple.Direction.REVERSE); use these if necessary
        //motor2.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void in(){
        motor1.setPower(intakeSpeed);
        motor2.setPower(intakeSpeed);
    }
    public void out(){
        motor1.setPower(-intakeSpeed);
        motor2.setPower(-intakeSpeed);
    }
    public void stop(){
        motor1.setPower(0);
        motor2.setPower(0);
    }
}
