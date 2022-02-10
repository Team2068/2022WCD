// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShooterSubsystem;


public class CalculatedShoot extends CommandBase {
  
  ShooterSubsystem shooterSubsystem;
  Limelight limelight;
  
  /** Creates a new CalculatedShoot. */
  public CalculatedShoot(ShooterSubsystem shooterSubsystem, Limelight limelight) {
    this.shooterSubsystem = shooterSubsystem;
    this.limelight = limelight;
    addRequirements(shooterSubsystem);
    addRequirements(limelight);
  }

  double setpoint = 3000;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // double distance = Math.round(limelight.getDistance());
    // double shooterPower = ShooterConstants.shooterTable.get(distance);
    //  + 0.9*shooterSubsystem.feedforward.calculate(setpoint)
    shooterSubsystem.rampUpShooter(shooterSubsystem.bangController.calculate(shooterSubsystem.flywheel2.getEncoder().getVelocity(), setpoint));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
