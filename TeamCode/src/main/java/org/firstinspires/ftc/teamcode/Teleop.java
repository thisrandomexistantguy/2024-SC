package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp(name="Johnny6Teleop")
public class Teleop extends OpMode {
    Johnny6 johnny6;

    //allows for driver customisation
    static final double STRAFE_FACTOR=1.1;

    @Override
    public void init(){
        johnny6=new Johnny6(this, Johnny6.Drivetrain.JOHNNY6);
    }

    @Override
    public void loop(){
        telemetry.addData("front left encoder: ",johnny6.motorFrontLeft.getCurrentPosition());
        telemetry.addData("back left encoder: ",johnny6.motorBackLeft.getCurrentPosition());
        telemetry.addData("front right encoder: ",johnny6.motorFrontRight.getCurrentPosition());
        telemetry.addData("back right encoder: ",johnny6.motorBackRight.getCurrentPosition());

        telemetry.update();

        //Key variables for movement
        double y=-gamepad1.left_stick_y/2;
        double x=(gamepad1.left_stick_x*STRAFE_FACTOR)/2;
        double turn=gamepad1.right_stick_x/2;

        johnny6.move(x,y,turn);

        if(gamepad1.atRest()) johnny6.rest();

        //code for arm extension
        if (gamepad2.dpad_up) {
            johnny6.setArmMotor(1);

        } else {
            johnny6.setArmMotor(0);
        }
        //code for arm retraction
        if (gamepad2.dpad_down) {
            johnny6.setArmMotor(-1);

        } else {
            johnny6.setArmMotor(0);
        }

        //code for drone launch
        if (gamepad2.right_trigger > 0.5) {
            johnny6.setDroneMotor(1);

        } else {
            johnny6.setDroneMotor(0);
        }

        //code for lifting the arm
        if (gamepad2.x) {
            johnny6.raiseArm();

        } else {
           johnny6.setArm();
        }

        //code for opening the claw
        if (gamepad2.right_bumper) {
            johnny6.openClaw();
        }

        //code for closing the claw
        if (gamepad2.left_bumper) {
            johnny6.closeClaw();
        }
    }
}
