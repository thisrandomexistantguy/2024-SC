package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Johnny6 {

    private HardwareMap hwMap;

    LinearOpMode auton;

    public enum Drivetrain {
        MECHANUM,
        JOHNNY6,
        TEST
    }

    public enum Team {
        RED,
         BLUE
    }

    private Drivetrain drive;

    private Telemetry telem;

    //Definitions for global variables

    public DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;
    //[] means array
    public DcMotor[] allDriveMotors;

    public DcMotor suspendMotor;

    public DcMotor launchMotor;

    public DcMotor rotationMotor;

    public Servo flickServo, clawServo;

    //public CRServo //future necessary robot functions using servos
    private IMU imu;

    private IMU.Parameters parameters;

    public WebcamName webcamName;

    //Put any CONSTANTS here

    static final double Y_INCH_TICKS = 40;

    static final double X_INCH_TICKS = 40;

    public Johnny6 (OpMode opmode, Drivetrain drivetrain) {

        this.hwMap = opmode.hardwareMap;

        this.drive = drivetrain;

        this.telem = opmode.telemetry;

        //setUpHardware maps variables to their hardware object
        setupHardware();
    }

    public Johnny6(LinearOpMode opmode, Drivetrain type) {

        this.auton = opmode;

        hwMap = opmode.hardwareMap;

        telem = opmode.telemetry;

        drive = type;

        setupHardware();

        telem.addLine("init motor test");
    }

    public Johnny6(HardwareMap hardwareMap, Drivetrain drivetrain) {

        this.hwMap = hardwareMap;

        this.drive = drivetrain;

        setupHardware();
    }

    private void setupHardware() {

        //This switch statement is used to choose which drivetrain
        //depending on the drive variable
        switch ( drive ) {

            case JOHNNY6:

                motorFrontLeft = hwMap.dcMotor.get("motorFrontLeft");
                motorFrontRight = hwMap.dcMotor.get("motorFrontRight");
                motorBackLeft = hwMap.dcMotor.get("motorBackLeft");
                motorBackRight = hwMap.dcMotor.get("motorBackRight");



                motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);


                imu=hwMap.get(IMU.class,"imu");



                parameters=new IMU.Parameters(new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT));


                imu.initialize(parameters);

                //swebcamName = hwMap.get(WebcamName.class, "eyeofjohnny6");

                //Add arm mechanism hardware devices here
                suspendMotor = hwMap.dcMotor.get( "suspendMotor" );
                launchMotor = hwMap.dcMotor.get( "launchMotor" );
                rotationMotor = hwMap.dcMotor.get( "rotationMotor" );
                flickServo = hwMap.servo.get( "flickServo" );
                clawServo = hwMap.servo.get( "clawServo" );

                break;

            case MECHANUM:

                //Setup motors that are used for the drivetrain
                motorFrontLeft = hwMap.dcMotor.get("motorFrontLeft");
                motorFrontRight = hwMap.dcMotor.get("motorFrontRight");
                motorBackLeft = hwMap.dcMotor.get("motorBackLeft");
                motorBackRight = hwMap.dcMotor.get("motorBackRight");

                //Reverse motors
                motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

                //Here would go any additional hardware devices for the robot

                imu = hwMap.get( IMU.class, "imu");

                parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT));

                imu.initialize(parameters);


                //camera setup!
                //webcamName=hwMap.get(WebcamName.class,"eyeofjohnny6");

                //add arm mechanisms and servo thingys

                allDriveMotors=new DcMotor[]{motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight};
                break;


            case TEST:

                //setup motors for drivetrain
                motorFrontLeft = hwMap.dcMotor.get("motorFrontLeft");
                motorFrontRight = hwMap.dcMotor.get("motorFrontRight");
                motorBackLeft = hwMap.dcMotor.get("motorBackLeft");
                motorBackRight = hwMap.dcMotor.get("motorBackRight");

                //Reverse motors
                motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
                motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);

                //Here would go any additional hardware devices for the robot

                imu = hwMap.get( IMU.class, "imu");

                parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT));

                imu.initialize(parameters);


                //camera setup!
                webcamName=hwMap.get(WebcamName.class,"eyeofjohnny6");
                allDriveMotors=new DcMotor[]{motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight};

                break;

            default:

                telem.addLine("Invalid type " + drive + " passed to Johnny6's init function. Nothing has been set up ");
                break;
        }
    }

    //Set motor power for all drivetrain motors on robot to 0

    //set powers for motors and positions for servos
    public void rest() {
        motorBackLeft.setPower( 0 );
        motorBackRight.setPower( 0 );
        motorFrontLeft.setPower( 0 );
        motorFrontRight.setPower( 0 );
        suspendMotor.setPower( 0 );
        launchMotor.setPower( 0 );
        rotationMotor.setPower( 0 );
        flickServo.setPosition( 0 );
        clawServo.setPosition( 0 );
    }

    /*
    This function controls movement for the robot.
    @param x the x speed value
    @param y the y speed value
    @param turn the turn speed value
     */

    public void move( double x, double y, double turn ) {

        switch ( drive ) {

            case JOHNNY6:
                //Denominator is the larget motor power (absolute value) or 1
                //This ensures all the powers maintain the same ratio, but only when
                //at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(turn), 1);

                //Compute values for the power of each motor
                double frontLeftPower = ( y + x + turn) / denominator;
                double backLeftPower = ( y - x + turn) / denominator;
                double frontRightPower = ( y - x - turn) / denominator;
                double backRightPower = ( y + x - turn) / denominator;

                telem.addLine( "frontLeft: " + frontLeftPower);
                telem.addLine( "frontRight: " + frontRightPower);
                telem.addLine( "backLeft: " + backLeftPower);
                telem.addLine( "backRight: " + backRightPower);

                //Assign that motor power to each motor
                motorFrontLeft.setPower( frontLeftPower );
                motorBackLeft.setPower( backLeftPower );
                motorFrontRight.setPower( frontRightPower );
                motorBackRight.setPower( backRightPower );

                break;

            case MECHANUM:

                //Denominator is the larget motor power (absolute value) or 1
                //This ensures all the powers maintain the same ratio, but only when
                //at least one is out of the range [-1, 1]
                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(turn), 1);

                //Compute values for the power of each motor
                frontLeftPower = ( y + x + turn) / denominator;
                backLeftPower = ( y - x + turn) / denominator;
                frontRightPower = ( y - x - turn) / denominator;
                backRightPower = ( y + x - turn) / denominator;

                //Assign that motor power to each motor
                motorFrontLeft.setPower( frontLeftPower );
                motorBackLeft.setPower( backLeftPower );
                motorFrontRight.setPower( frontRightPower );
                motorBackRight.setPower( backRightPower );

                break;


            case TEST:

                //Denominator is the larget motor power (absolute value) or 1
                //This ensures all the powers maintain the same ratio, but only when
                //at least one is out of the range [-1, 1]

                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(turn), 1);

                //Compute values for the power of each motor
                frontLeftPower = ( y + x + turn) / denominator;
                backLeftPower = ( y - x + turn) / denominator;
                frontRightPower = ( y - x - turn) / denominator;
                backRightPower = ( y + x - turn) / denominator;

                //Assign that motor power to each motor
                motorFrontLeft.setPower( frontLeftPower );
                motorBackLeft.setPower( backLeftPower );
                motorFrontRight.setPower( frontRightPower );
                motorBackRight.setPower( backRightPower );

                break;

        }
    }

    public void moveLeft( double speed ) { move( -speed, 0, 0 ); }

    public void moveRight( double speed ) { move( speed, 0, 0 ); }

    public void moveForward( double speed ) { move( speed, 0, 0 ); }

    public void moveBackward( double speed ) { move( 0, speed, 0 ); }

    public void turnLeft( double speed ) { move( 0, 0, -speed ); }

    public void turnRight( double speed ) { move( 0, 0, speed ); }


    public void resetYaw() { imu.resetYaw(); }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw( AngleUnit.DEGREES);
    }

    //Set arm motor to the given power here
    //@param power the power to send to the arm motor

    public void armExtend() { suspendMotor.setPower(1); }

    public void armDetract() { suspendMotor.setPower(-1); }

    public void droneLaunch() { launchMotor.setPower(1); }

    public void rotate() { rotationMotor.setPower(1); }

    public void setRotateMotor( double power ) { rotationMotor.setPower( power ); }

    public void setSuspendMotor( double power ) { suspendMotor.setPower( power ); }

    public void setDroneMotor(double power ) { launchMotor.setPower( power ); }

    //set servo to the given position
    public void raiseArm() { flickServo.setPosition( 0.9 ); }

    public void setArm() { flickServo.setPosition( 0.3 ); }

    public void openClaw() { clawServo.setPosition( 0.6 ); }

    public void closeClaw() { clawServo.setPosition( 0.1 ); }




    /*
    public void turnRightDegrees(double degrees, double speed){
        double target= getHeading()+degrees;

    }*/

}
