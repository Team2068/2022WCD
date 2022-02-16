// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class ToggleTurbo extends CommandBase {

  DriveSubsystem driveSubsystem;

  public ToggleTurbo(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void execute() {
    driveSubsystem.turboOn();
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.turboOff();
  }
}
