package org.firstinspires.ftc.teamcode.Mechanisms;

import com.pedropathing.utils.Timer;

public class PIDFController {
    private double p,i,d,f, target,current,iHelp,dHelp, currentError,pastTime;
    private Timer timer;

    public PIDFController(double p, double i, double d,double f){
        pastTime=0;
        timer = new Timer();
        this.p=p;
        this.i=i;
        this.d=d;
        this.f=f;
    }
    public PIDFController(double[] pid){
        p=pid[0];
        i=pid[1];
        d=pid[2];
        f=pid[3];
    }
    public void setTargetPosition(double t){
        target=t;
    }
    public double run(){
        double error = target-current;
        double value = p*error+i*iHelp+d*(error-dHelp)+f*target;
        return value;
    }
    public double runSquid(){
        return p*Math.sqrt(Math.abs(target-current))*Math.signum(target-current)+f*Math.signum(target-current);
    }
    public void updatePosition(double pos){
        double error = Math.sqrt(Math.abs(target-pos))*Math.signum(target-pos);
        dHelp=Math.sqrt(Math.abs(target-current))*Math.signum(target-current);
        current=pos;
        iHelp+=error;
    }
    public void resetHelper(){
        iHelp=0;
    }
    public void setPIDFValues(double P,double I,double D,double F){
        p=P;
        i=I;
        d=D;
        f=F;
    }
    public void setPIDFValues(double[] values){
        p=values[0];
        i=values[1];
        d=values[2];
        f=values[3];
    }
}
