// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.lang.invoke.ConstantCallSite;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants;
import frc.robot.Constants.AimbotConstants;
import frc.robot.Constants.GameElementConstants;
import frc.robot.Constants.LimelightConstants;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    /** Creates a new ExampleSubsystem. */
    CANSparkMax flywheel1 = new CANSparkMax(ShooterConstants.FLYWHEEL_1, MotorType.kBrushless);
    CANSparkMax flywheel2 = new CANSparkMax(ShooterConstants.FLYWHEEL_2, MotorType.kBrushless);

    public ShooterSubsystem() {
        // flywheel1.restoreFactoryDefaults();
        // flywheel2.restoreFactoryDefaults();

        flywheel1.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
        flywheel2.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

        flywheel1.setOpenLoopRampRate(.2);
        flywheel2.setOpenLoopRampRate(.2);

        flywheel1.setIdleMode(IdleMode.kCoast);
        flywheel2.setIdleMode(IdleMode.kCoast);
    }

    // distance will have to be a value measured by the limelight
    // or a distance sensor. This will find the speed the flywheel
    // has to start at in order to score.
    // was 11.03 ???
    public double findLowerSpeed(double distance) {
        double vy = (GameElementConstants.LOW_HEIGHT + 11.03) / 1.5;
        double vx = distance / 1.5;
        double speed = Math.tan(vy / vx);
        return speed;
    }

    // returns the desired speed in m/s
    public double findHighSpeed(double distance) {
        double time = 1.5;
        double vy = ((GameElementConstants.HIGH_HEIGHT - Units.inchesToMeters(LimelightConstants.LIMELIGHT_HEIGHT)) + (0.5 * 9.8 * time*time)) / time;
        double vx = distance / time;
        //double speed = Math.tan(vy / vx);
        double speed = Math.sqrt((vy*vy)+(vx*vx));
        return speed;
    }

    // Will spin the motor until it gets up to speed.
    public void rampUpShooter(double speed) {
        flywheel1.set(-speed);
        flywheel2.set(speed);
        // if((flywheel1.get() == speed) && (flywheel2.get() == speed)) {
        // Dashboard.putDebugNumber("Ramped up");
        // }
    }

    // Will let the motor coast to a stop
    public void rampDownShooter() {
        // flywheel1.stopMotor();
        // flywheel2.stopMotor();
        flywheel1.set(0);
        flywheel2.set(0);
    }

    public void setPower(double power) {
        DriverStation.reportWarning("Shooting", true);
        // flywheel1.set(power);
        // flywheel2.set(power);
        flywheel1.setVoltage(power);
        flywheel2.setVoltage(power);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
