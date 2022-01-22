package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
    private CANSparkMax frontLeft = new CANSparkMax(DriveConstants.FRONT_LEFT, MotorType.kBrushless);
    private CANSparkMax frontRight = new CANSparkMax(DriveConstants.FRONT_RIGHT, MotorType.kBrushless);
    private CANSparkMax backLeft = new CANSparkMax(DriveConstants.BACK_LEFT, MotorType.kBrushless);
    private CANSparkMax backRight = new CANSparkMax(DriveConstants.BACK_RIGHT, MotorType.kBrushless);

    private MotorControllerGroup leftMotors = new MotorControllerGroup(frontLeft, backLeft);
    private MotorControllerGroup rightMotors = new MotorControllerGroup(frontRight, backRight);

    private DifferentialDrive differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
    
    private RelativeEncoder leftEncoder;
    private RelativeEncoder rightEncoder;

    private boolean isForward = true;
    private double maxSpeed = DriveConstants.NORMAL_SPEED;
    // private Pigeon pigeon = new Pigeon(0);

    public DriveSubsystem() {
        frontLeft.restoreFactoryDefaults();
        frontRight.restoreFactoryDefaults();
        backLeft.restoreFactoryDefaults();
        backRight.restoreFactoryDefaults();

        frontLeft.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
        frontRight.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
        backLeft.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
        backRight.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

        frontLeft.setIdleMode(IdleMode.kBrake);
        frontRight.setIdleMode(IdleMode.kBrake);
        backLeft.setIdleMode(IdleMode.kBrake);
        backRight.setIdleMode(IdleMode.kBrake);

        leftEncoder = frontLeft.getEncoder();
        rightEncoder = frontRight.getEncoder();

        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        differentialDrive.setDeadband(0.1);
    }

    public void turboOn() {
        maxSpeed = DriveConstants.TURBO_SPEED;
    }

    public void turboOff() {
        maxSpeed = DriveConstants.NORMAL_SPEED;
    }

    public void slowOn() {
        maxSpeed = DriveConstants.SLOW_SPEED;
    }

    public void slowOff() {
        maxSpeed = DriveConstants.NORMAL_SPEED;
    }

    private double adjustSpeed(double speed) {
        if (speed >= 0) {
            speed = Math.min(speed, maxSpeed);
        } else {
            speed = Math.max(speed, -maxSpeed);
        }

        return speed;
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        leftSpeed = adjustSpeed(leftSpeed);
        rightSpeed = adjustSpeed(rightSpeed);
        differentialDrive.feed();
        differentialDrive.feedWatchdog();
        if (isForward) {
            differentialDrive.tankDrive(leftSpeed, rightSpeed, false);
        } else {
            differentialDrive.tankDrive(leftSpeed * -1, rightSpeed * -1, false);
        }

    }

    public void resetDriveEncoders() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    public void stopDrive() {
        tankDrive(0, 0);
    }

    public double getEncoderPosition() {
        return rightEncoder.getPosition();
    }

    public void invertTankDrive() {
        isForward = !isForward;
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        differentialDrive.arcadeDrive(xSpeed, zRotation);
    }

    @Override
    public void periodic() {
    }
}