package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.RobotStatus;
//import org.firstinspires.ftc.teamcode.Collect;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class RobotHardware {
    public HardwareMap hardwareMap = null;
    public Drive d = null;
    public Collect c = null;
    public DcMotor lift = null;
    //public ColorSensor color = null;
    public Servo f = null;
    public TwoPosServo g = null;
   // public BNO055IMU imu = null;
    public Orientation lastAngles = null; // new Orientation();
    public int camId;
    public WebcamName cam;
    
    private double globalangle = 0;

    
    public void build(HardwareMap hm) {
        hardwareMap = hm;
        d = new Drive(
            hardwareMap.get(DcMotor.class, "rbmotor"),
            hardwareMap.get(DcMotor.class, "rfmotor"),
            hardwareMap.get(DcMotor.class, "lfmotor"),
            hardwareMap.get(DcMotor.class, "lbmotor")
        );

        c = new Collect(
                hardwareMap.get(DcMotor.class, "liftmotor"),
                hardwareMap.get(Servo.class, "release")
        );
        
//        lastAngles = new Orientation();
//        imu = hardwareMap.get(BNO055IMU.class, "imu");
//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//          parameters.mode                = BNO055IMU.SensorMode.IMU;
//          parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
//          parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
//          parameters.loggingEnabled      = false;
//        imu.initialize(parameters);
//        globalangle = 0;
        
        lift = hardwareMap.get(DcMotor.class, "liftmotor");
        //color = hardwareMap.get(ColorSensor.class, "colorsensor");
        f = hardwareMap.get(Servo.class, "claw");
        g = (TwoPosServo) hardwareMap.get(Servo.class, "gearbox");

        camId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        cam = hardwareMap.get(WebcamName.class, "Webcam 1");
    }
    
    public void reset() {
        d.resetEncoderlf();
        globalangle = 0;
        d.setPower(0,0,0,0);
    }
    
    //call this in loop when we need to be tracking the imu
//    public double updateGlobalAngle() {
//        globalangle = globalangle + getAngle();
//        return globalangle;
//    }
//
//    public double getAngle() {
//      //this function and the note below taken from somewhere else
//
//      // We experimentally determined the Z axis is the axis we want to use for heading angle.
//      // We have to process the angle because the imu works in euler angles so the Z axis is
//      // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
//      // 180 degrees. We detect this transition and track the total cumulative angle of rotation.
//
//      Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//
//      double deltaAngle = angles.firstAngle - lastAngles.firstAngle;
//
//      if (deltaAngle < -180)
//          deltaAngle += 360;
//      else if (deltaAngle > 180)
//          deltaAngle -= 360;
//
//      // globalAngle += deltaAngle;
//
//      lastAngles = angles;
//
//      return deltaAngle;
//    }
    
    public RobotStatus update() {
        // get data from Vuforia
        RobotStatus r = new RobotStatus(0f, 0f, 0f); // x, y, heading
        return r;
    }
    
}



