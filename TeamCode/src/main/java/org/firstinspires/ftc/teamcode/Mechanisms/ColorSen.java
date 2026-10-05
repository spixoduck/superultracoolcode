package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorSen {
    private NormalizedColorSensor colorSensor;
    public enum DetectedColor{
        YELLOW,
        RED,
        BLUE,
        UNKNOWN,
        NOBALL
    }
    public void init(HardwareMap hwMap, String name){
        colorSensor = hwMap.get(NormalizedColorSensor.class,name);
        colorSensor.setGain(20);
    }
    public void printColor(Telemetry telemetry){
        NormalizedRGBA color = colorSensor.getNormalizedColors();
        float normRed, normGreen, normBlue;
        normRed=color.red/color.alpha;
        normGreen=color.green/color.alpha;
        normBlue=color.blue/color.alpha;
        double[] hsv = getHSV(normRed,normGreen,normBlue);
        telemetry.addData("red",normRed);
        telemetry.addData("green",normGreen);
        telemetry.addData("blue",normBlue);
        telemetry.addData("hue", hsv[0]);
        telemetry.addData("saturation",hsv[1]);
        telemetry.addData("value",hsv[2]);
        telemetry.update();
        /*
        purple = >0.54, >0.69, <0.9
        65
        green = <.3, >0.75, <0.8
         */
    }
    public  DetectedColor getColor(){
        NormalizedRGBA color = colorSensor.getNormalizedColors();
        float normRed, normGreen, normBlue;
        normRed=color.red/color.alpha;
        normGreen=color.green/color.alpha;
        normBlue=color.blue/color.alpha;
        double[] hsv = getHSV(normRed,normGreen,normBlue);
        double hue = hsv[0];
        double sat = hsv[1];
        double value = hsv[2];
        if(value+sat>.7)
            if(Math.abs(hue-220)<20)
                return DetectedColor.BLUE;
            else if(Math.abs(hue-20)<20)
                return DetectedColor.RED;
            else if(Math.abs(hue-80)<20)
                return DetectedColor.YELLOW;
        return DetectedColor.UNKNOWN;
    }
    private double[] getHSV(double r, double g, double b){
        double max = Math.max(r, g);
        max = Math.max(max,b);
        double min = Math.min(r,g);
        min = Math.min(min,b);
        double d = max-min;
        double hue;
        if(max==r){
            hue= 60 * ((g-b)/d%6);
        }
        else if(max==g){
            hue= 60 * ((b-r)/d+2);
        }
        else{
            hue= 60 * ((r-g)/d+4);
        }
        double s;
        if(max==0)
            s=0;
        else
            s=d/max;
        double[] result= {hue,s,max};
        return result;
    }
}