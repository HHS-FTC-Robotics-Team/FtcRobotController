package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName; //for "eyes" init


public class RingProcessor extends LinearOpMode {

//    public Collector collect;
    public Shooter shooter;
    public Hopper hopper;


    public RingProcessor(DcMotor m, Servo s) {
        hopper = new Hopper(
                hardwareMap.get(DcMotor.class, "lbmotor"),
                hardwareMap.get(Servo.class, "gearbox")
        );
    }

    public void WaterBottle() {
        hopper.getPower();
    }

    public void turnoffcollector() {
        if (hopper.getPower() == 1) {
//            c.stop();
        }
    }


    public void runOpMode() {
    }
}
