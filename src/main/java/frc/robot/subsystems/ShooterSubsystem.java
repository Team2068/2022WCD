// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants;
import frc.robot.Constants.GameElementConstants;
import frc.robot.Constants.LimelightConstants;
import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    /** Creates a new ExampleSubsystem. */
    public CANSparkMax flywheel = new CANSparkMax(ShooterConstants.FLYWHEEL_2, MotorType.kBrushless);
    public SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0.065889, 0.12472);
    public BangBangController bangController = new BangBangController(100);

    public ShooterSubsystem() {
        // flywheel1.restoreFactoryDefaults();
        // flywheel.restoreFactoryDefaults();

        flywheel.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

        //flywheel1.setOpenLoopRampRate(.2);
        //flywheel.setOpenLoopRampRate(.2);

        flywheel.setIdleMode(IdleMode.kCoast);
        flywheel.setInverted(true);
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
        flywheel.set(speed);
    }

    // Will let the motor coast to a stop
    public void rampDownShooter() {
        flywheel.set(0);
    }

    public void setPower(double power) {
        DriverStation.reportWarning("Shooting", true);
        flywheel.setVoltage(power);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Flywheel RPM", flywheel.getEncoder().getVelocity());
        SmartDashboard.putNumber("Flywheel setpoint", bangController.getSetpoint());
        SmartDashboard.putNumber("Flywheel error", bangController.getError());
        // This method will be called once per scheduler run
    }
}
