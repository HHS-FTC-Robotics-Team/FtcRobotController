package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Collect extends LinearOpMode {

  private DcMotor mtr = null;
  private Servo release = null;

  public Collect (DcMotor m, Servo s) {
    mtr = m;
    release = s;
    mtr.setDirection(DcMotor.Direction.REVERSE);
  }

  public void in() {
    mtr.setPower(-1);
   // Rmtr.setPower(1);
  }

  public void out() {
    mtr.setPower(1);
  //  Rmtr.setPower(-1);
  }

  public void rest() {
    mtr.setPower(0);
  //  Rmtr.setPower(0);
  }

  public void setPower(double p) {
    mtr.setPower(p);
  }

  public void movePlatform(double goal) { //move to given pos
    release.setPosition(goal);
  }

  public void runOpMode() {

  }
}
