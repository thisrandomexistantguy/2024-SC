package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp(name="Johnny6Teleop")
public class Teleop extends OpMode {
    Johnny6 johnny6;

    //allows for driver customisation
    static final double STRAFE_FACTOR=1.1;

    boolean clawOpen = false;
    boolean propUp=false;

    boolean rbumperPressed = false;
    boolean rbumperProcessed = false;

    boolean lbumperPressed=false;
    boolean lbumperProcessed=false;

    @Override
    public void init(){
        johnny6=new Johnny6(this, Johnny6.Drivetrain.JOHNNY6);
    }

    @Override
    public void loop(){
        //telemetry.addData("front left encoder: ",johnny6.motorFrontLeft.getCurrentPosition());
        //telemetry.addData("back left encoder: ",johnny6.motorBackLeft.getCurrentPosition());
        //telemetry.addData("front right encoder: ",johnny6.motorFrontRight.getCurrentPosition());
        //telemetry.addData("back right encoder: ",johnny6.motorBackRight.getCurrentPosition());

        //telemetry.update();

        //Key variables for movement
        double y=-gamepad1.left_stick_y;
        double x=gamepad1.left_stick_x;
        y *= y;
        if(gamepad1.left_stick_y > 0) {
            y = -y;
        }
        x *= x;
        if(gamepad1.left_stick_x < 0) {
            x = -x;
        }
        //double x=(gamepad1.left_stick_x*STRAFE_FACTOR)/2;
        double turn=gamepad1.right_stick_x/2;

        johnny6.move(x,y,turn);


        //if(gamepad1.atRest()) johnny6.rest();

        //code for arm extension
        if (gamepad2.dpad_up) {
            johnny6.armExtend();
        } else if (gamepad2.dpad_down) {
            johnny6.armDetract();
        } else {
            johnny6.setSuspendMotor(0);
        }

        //code for drone launch
        if (gamepad2.right_trigger > 0.5) {
            johnny6.setDroneMotor(1);

        } else {
            johnny6.setDroneMotor(0);
        }

        //code for rotating the arm up and down
        if (gamepad2.left_stick_y > 0.3) {
            telemetry.addData("rotate motor: ", gamepad2.left_stick_y);
            johnny6.setRotateMotor(-0.4);
            johnny6.propSet();

        } else if (gamepad2.left_stick_y < -0.3) {
            telemetry.addData("rotate motor: ", gamepad2.left_stick_y);
            johnny6.setRotateMotor(0.6);
            johnny6.propSet();

        } else {
            telemetry.addLine("rotate motor off");
            johnny6.setRotateMotor(0);
        }
        telemetry.update();



        //code for flicking up the hook
        if (gamepad2.x) {
            johnny6.raiseArm();

        } else {
           johnny6.setArm();
        }

        //code for opening and closing the claw
        if(gamepad2.right_bumper) {
            rbumperPressed = true;
        } else {
            rbumperPressed = false;
            rbumperProcessed = false;
        }

        if(rbumperPressed && !rbumperProcessed) {
            if(clawOpen) {
                johnny6.closeClaw();
                clawOpen = false;
            } else {
                johnny6.openClaw();
                clawOpen = true;
            }
            rbumperProcessed = true;
        }

        //code for activating the arm prop
        if(gamepad2.left_bumper) {
            lbumperPressed = true;
        } else {
            lbumperPressed = false;
            lbumperProcessed = false;
        }

        if(lbumperPressed && !lbumperProcessed) {
            if(propUp) {
                johnny6.propSet();
                propUp = false;
            } else {
                johnny6.propUp();
                propUp = true;
            }
            lbumperProcessed = true;
        }

        //code for opening the claw
        /*if (gamepad2.right_bumper) {
            johnny6.openClaw();

        }
        else if(!gamepad2.right_bumper){
           johnny6.closeClaw();
        }

        /*if(gamepad2.right_bumper&&!lock&&!toggle){
            toggle=true;
            lock=true;
        }else if(gamepad2.right_bumper&&!lock&&toggle){
            toggle=false;
            lock=true;
        }else if(!gamepad2.right_bumper){
            lock=false;
        }
        //if(gamepad1.atRest()){johnny6.rest();}*/
    }
}

