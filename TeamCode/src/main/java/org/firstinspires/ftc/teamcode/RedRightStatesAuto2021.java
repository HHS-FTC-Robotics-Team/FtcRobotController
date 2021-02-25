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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.states.ForwardUntil;
import org.firstinspires.ftc.teamcode.states.LiftUntilPos;
import org.firstinspires.ftc.teamcode.states.MoveClaw;
import org.firstinspires.ftc.teamcode.states.MoveHopper;
import org.firstinspires.ftc.teamcode.states.PowerShooter;
import org.firstinspires.ftc.teamcode.states.ShootRings;
import org.firstinspires.ftc.teamcode.states.TurnUntilAngle;
import org.firstinspires.ftc.teamcode.states.ReleaseCollector;
import org.firstinspires.ftc.teamcode.states.RightDetectRings;
//import org.firstinspires.ftc.teamcode.states.StrafeUntilClicks;
////import org.firstinspires.ftc.teamcode.states.CollectUntilDist;
////import org.firstinspires.ftc.teamcode.states.DispenseUntilDist;
//import org.firstinspires.ftc.teamcode.states.SeekUntilColor;
//import org.firstinspires.ftc.teamcode.states.GrabFoundation;
//import org.firstinspires.ftc.teamcode.states.DragFoundationR;
//import org.firstinspires.ftc.teamcode.states.LiftUntilTime;

//


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
@Disabled
public class RedRightStatesAuto2021 extends OpMode {
    /* Declare OpMode members. */
    public int stage = 0;
    public RobotHardware robotHardware = new RobotHardware();

    public LinearStack states = new LinearStack(new OurState[] {

////========================== OFFICIAL

            //Red Right
            new ReleaseCollector(),


            new RightDetectRings(),

    });
    public LinearStack zerostates = new LinearStack(new OurState[] {

            new ForwardUntil(-6),
            new LiftUntilPos("horizontal"),
            new MoveClaw("close"),
            new LiftUntilPos("above ground"),
            new ForwardUntil(-48),

            new TurnUntilAngle(-14),
            new PowerShooter(30, 2200),
            new MoveHopper("two"),
            new PowerShooter(30, 2000),
            new MoveHopper("one"),
            new MoveHopper("zero"),
            new PowerShooter(19, 0),
            new TurnUntilAngle(14),

            new ForwardUntil(-16),
            new MoveClaw("open"),
            new LiftUntilPos("vertical"),
    });

    public LinearStack onestates = new LinearStack(new OurState[] {
            new ForwardUntil(-6),
            new LiftUntilPos("horizontal"),
            new MoveClaw("close"),
            new LiftUntilPos("over wall"),
            new ForwardUntil(-48),

            new TurnUntilAngle(-14),
            new PowerShooter(30, 2200),
            new MoveHopper("two"),
            new PowerShooter(30, 2000),
            new MoveHopper("one"),
            new MoveHopper("zero"),
            new PowerShooter(19, 0),
            new TurnUntilAngle(14),

            new ForwardUntil(-46),
            new TurnUntilAngle(-180),
            new MoveClaw("open"),
            new ForwardUntil(-24),
            new LiftUntilPos("vertical"),
    });

    public LinearStack fourstates = new LinearStack(new OurState[] {
            new ForwardUntil(-6),
            new LiftUntilPos("horizontal"),
            new MoveClaw("close"),
            new LiftUntilPos("above ground"),
            new ForwardUntil(-48),

            new TurnUntilAngle(-14),
            new PowerShooter(30, 2200),
            new MoveHopper("two"),
            new PowerShooter(30, 2000),
            new MoveHopper("one"),
            new MoveHopper("zero"),
            new PowerShooter(19, 0),
            new TurnUntilAngle(14),

            new ForwardUntil(-62),
            new MoveClaw("open"),
            new ForwardUntil(44),
            new LiftUntilPos("vertical"),
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
        } else if (stage == 0){
            //transition to next states
            double rings = states.getVariable();
            telemetry.addData("rings", rings);
            if (rings == (double) 0.0) {
                telemetry.addData("state", "zero");
                //zerostates.loop();
                states = zerostates;
                states.init(robotHardware);
            } else if (rings == (double) 1.0) {
                telemetry.addData("state", "one");
                states = onestates;
                states.init(robotHardware);
            } else if (rings == (double) 4.0) {
                telemetry.addData("state", "four");
                states = fourstates;
                states.init(robotHardware);
            }
            stage = 1;
        } else {
            telemetry.addData("Status", "Done");
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
