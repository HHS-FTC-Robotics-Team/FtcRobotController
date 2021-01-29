package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Collect extends LinearOpMode { // TODO change name to CollectAndLift or something spiffy

  private DcMotor mtr = null;
  private Servo release = null;

  private double liftclicks = 0; // for lift
  private String liftposition = "vertical"; // lift positions: "vertical" , "horizontal" , "above ground" , "over wall"


  public Collect (DcMotor m, Servo s) {
    mtr = m;
    release = s;
    mtr.setDirection(DcMotor.Direction.REVERSE); //TODO maybe change this to FORWARD if it goes the wrong way
    mtr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    mtr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
  }

  public void in() {
    mtr.setPower(-1); //TODO if you change line 22 to FORWARD, you need to make this positive and make out() negative
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

  public void nextLiftPosition() { //move lift to the next state
    if (liftposition == "vertical") {
      liftposition = "horizontal";
      moveLift(100); //TODO find the right positions here
    } else if (liftposition == "horizontal") {
      liftposition = "above ground";
      moveLift(200);
    } else if (liftposition == "above ground") {
      liftposition = "over wall";
      moveLift(300);
    } else if (liftposition == "over wall") {
      liftposition = "vertical";
      moveLift(0);
    }
  }

  public void moveLift(double position) {
    mtr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //reset encoder first
    mtr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    double goal = position - liftclicks; //find the relative amount the motor has to move, because now the motor clicks say zero. Use the clicks variable that we save

    mtr.setTargetPosition((int)goal); // move that relative amount to get to new position
    mtr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    mtr.setPower(0.3); //TODO maybe change this power if you need?

    liftclicks = position;
  }

  public double getClicks() {
    return liftclicks;
  }

  public void updateClicks() {
    liftclicks = liftclicks + mtr.getCurrentPosition(); //set clicks variable to the lift's actual position
    mtr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    mtr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
  }

  public boolean isBusy() {
    return mtr.isBusy();
  }


  public void runOpMode() {

  }
}
