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
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.states.ForwardUntil;
import org.firstinspires.ftc.teamcode.states.TurnUntilAngle;
//import org.firstinspires.ftc.teamcode.states.StrafeUntilClicks;
//import org.firstinspires.ftc.teamcode.states.CollectUntilDist;
//import org.firstinspires.ftc.teamcode.states.DispenseUntilDist;
//import org.firstinspires.ftc.teamcode.states.SeekUntilColor;
//import org.firstinspires.ftc.teamcode.states.GrabFoundation;
//import org.firstinspires.ftc.teamcode.states.DragFoundationR;
import org.firstinspires.ftc.teamcode.RobotHardware;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
public class SynchronousStack extends OurState {
    /* Declare OpMode members. */
    //public OurState states[];

//    public OurState[] states = null;
    public ArrayList<OurState> states = new ArrayList<OurState>();

    public SynchronousStack(OurState[] stack) {
        states.addAll(Arrays.asList(stack)); //java moment
    }

    private int count = 0;


    public void init(RobotHardware r) {
        robotHardware = r;
        for (int i = 0; i < states.size(); i++) {
            states.get(i).init(robotHardware);
        }
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        // be careful with this, we probably won't use it
        for (int i = 0; i < states.size(); i++) {
            states.get(i).init_loop();
        }
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        for (int i = 0; i < states.size(); i++) {
            states.get(i).start();
        }
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

//        if(states[count].running == true) {
//            states[count].loop();
//        } else if(count < states.size() - 1) {
//            //go to next state and input the last state's output
//            double tempvariable = states[count].getVariable();
//            count += 1;
//            robotHardware.reset();
//            states[count].init(robotHardware);
//            states[count].addToGoal(tempvariable);
//        } else {
//            running = false;
//        }

        //running = false when the stack is empty; remove states when they're done running
        for (int i = 0; i < states.size(); i++) {
            if(states.get(i).running == true) {
                states.get(i).loop();
            } else {
                //remove from stack
                states.get(i).stop();
                states.remove(i);
            }
        }
        if (states.size() == 0) {
            running = false;
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        for (int i = 0; i < states.size(); i++) {
            states.get(i).stop();
        }
    }

    @Override
    public void addToGoal(double variable) {
        //only will pass to last state in linear stack
        states.get(states.size() - 1).addToGoal(variable);
    }

    public double getVariable(int i) {
        return states.get(i).getVariable();
    }

    //add state(s) to the stack
    public void addState(OurState[] stack) {
        for (int i = 0; i < stack.length; i++) {
            stack[i].init(robotHardware);
        }
        states.addAll(Arrays.asList(stack));
    }

    public int getSize() {
        return states.size();
    }

}
