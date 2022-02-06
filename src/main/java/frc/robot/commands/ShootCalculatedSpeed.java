// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCalculatedSpeed extends CommandBase {
  /** Creates a new ShootCalculatedSpeed. */
  Limelight limelight;
  ShooterSubsystem shooterSubsystem;
  public ShootCalculatedSpeed(Limelight limelight, ShooterSubsystem shooter) {
    this.limelight = limelight;
    this.shooterSubsystem = shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(limelight);
    addRequirements(shooterSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double distance = limelight.getDistance();
    double speed = Math.abs(shooterSubsystem.findHighSpeed(Units.inchesToMeters(distance)));
    double rpm = speed * (60/(Math.PI * Units.inchesToMeters(0.625)));
    double power = rpm / 11000;

    shooterSubsystem.rampUpShooter(power);
    SmartDashboard.putNumber("shooter power", power);
    SmartDashboard.putNumber("shooter rpm", rpm);
    SmartDashboard.putNumber("shooter speed", speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterSubsystem.rampDownShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
