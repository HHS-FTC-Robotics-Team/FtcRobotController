/*
Copyright 2020 FIRST Tech Challenge Team 15317

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.states.ForwardUntil;
import org.firstinspires.ftc.teamcode.states.MoveClaw;
import org.firstinspires.ftc.teamcode.states.MoveArm;
import org.firstinspires.ftc.teamcode.states.MoveGearbox;
import org.firstinspires.ftc.teamcode.states.TurnUntilAngle;
import org.firstinspires.ftc.teamcode.states.DetectRings;
//import org.firstinspires.ftc.teamcode.states.StrafeUntilClicks;
////import org.firstinspires.ftc.teamcode.states.CollectUntilDist;
////import org.firstinspires.ftc.teamcode.states.DispenseUntilDist;
//import org.firstinspires.ftc.teamcode.states.SeekUntilColor;
//import org.firstinspires.ftc.teamcode.states.GrabFoundation;
//import org.firstinspires.ftc.teamcode.states.DragFoundationR;
//import org.firstinspires.ftc.teamcode.states.LiftUntilTime;
import org.firstinspires.ftc.teamcode.RobotHardware;
//
import java.text.SimpleDateFormat;
import java.util.Date;

///**
// * This file contains an example of an iterative (Non-Linear) "OpMode".
// * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
// * The names of OpModes appear on the menu of the FTC Driver Station.
// * When an selection is made from the menu, the corresponding OpMode
// * class is instantiated on the Robot Controller and executed.
// *
// * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
// * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
// */
@Autonomous
public class Auto2021 extends OpMode {
    /* Declare OpMode members. */
    public RobotHardware robotHardware = new RobotHardware();
    public LinearStack states = new LinearStack(new OurState[] {

            new MoveClaw("close"),
            new DetectRings(),
//            Blue Left 0
            //Lift(Something)

            //Lift(Something)
//            new ForwardUntil(-72),
//            new TurnUntilAngle(180),
//            new MoveClaw("open"),
            //MoveUntilWhite()

//            //Blue Left 1
//            new ForwardUntil(-96),
//            new MoveClaw("close"),
//
//            //Blue Left 4
//            new ForwardUntil(-120),
//            new TurnUntilAngle(180),
//            new MoveClaw("close"),
//
//            //Red Right 0
//            new ForwardUntil(-72),
//            new MoveClaw("close"),
//
//            //Red Right 1
//            new ForwardUntil(-96),
//            new TurnUntilAngle(180),
//            new MoveClaw("close"),
//
//            //Red Right 4
//            new ForwardUntil(-120),
//            new MoveClaw("close"),



            // Phase 1
            //new MoveGearbox("mesh")
            //new MoveArm(1000)
            //new MoveClaw("open"),
            //new MoveClaw("close")
            // new LiftUntilTime(120, -1),
            //new ForwardUntil(24),
            //new TurnUntilAngle(90),
            // new ForwardUntil(-900),
            // new SeekUntilColor(),
            // new LinearStack(new OurState[] {
//                 new CollectUntilDist(),
//                 new ForwardUntil(2200), // + y
            //     new StrafeUntilClicks(-9000) // + x
            //}
            //),

            // Phase 2
            // new DispenseUntilDist(),
            // new TurnUntilAngle(180),
            // new StrafeUntilClicks(3000),
            // new ForwardUntil(3000),
            //new GrabFoundation(),
            //new DragFoundationR(-180),
    });
    public LinearStack zerostates = new LinearStack(new OurState[] {
            new MoveClaw("close"),
            new MoveClaw("open"),
    });
    public LinearStack onestates = new LinearStack(new OurState[] {
            new TurnUntilAngle(90),
    });
    public LinearStack fourstates = new LinearStack(new OurState[] {
            new TurnUntilAngle(180),
    });

    @Override
    public void init() {
        robotHardware.build(hardwareMap);
        telemetry.addData("Status", "Initialized");
        states.init(robotHardware);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
       // robotHardware.init(); // build this
        states.init_loop();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        // robotHardware.start();
        states.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // RobotStatus r = robotHardware.update(); // update state of robot based on sensor input
        // states.loop(r);
        telemetry.addData("Status", "Looping");
        if (states.running) {
            states.loop();
        } else {
            //transition to next states
            telemetry.addData("rings", states.getVariable());
            if (states.getVariable() == (double) 0.0) {
                //zerostates.loop();
                states = zerostates;
                states.init(robotHardware);
            } else if (states.getVariable() == (double) 1.0) {
                states = onestates;
                states.init(robotHardware);
            } else if (states.getVariable() == (double) 4.0) {
                states = fourstates;
                states.init(robotHardware);
            }
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        states.stop();
        // robotHardware.stop();
    }
}
