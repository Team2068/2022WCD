// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class ToggleSlow extends CommandBase {
  DriveSubsystem driveSubsystem;

  public ToggleSlow(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    driveSubsystem.slowOn();
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.slowOff();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
