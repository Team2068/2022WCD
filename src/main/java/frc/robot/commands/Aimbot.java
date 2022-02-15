// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AimbotConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Limelight.TargetData;

public class Aimbot extends CommandBase {

  DriveSubsystem driveSubsystem;
  Limelight limelight;

  public Aimbot(Limelight limelight, DriveSubsystem driveSubsystem) {
    this.limelight = limelight;
    this.driveSubsystem = driveSubsystem;
    
    addRequirements(limelight);
    addRequirements(driveSubsystem);
  }

  float Kp = -0.02f;
  float min_command = 0.02f;

  @Override
  public void execute() {
    TargetData data = limelight.getTargetData();
    float distance = (float) data.horizontalOffset;
    float heading = -distance;
    float steering_adjust = 0.0f;
    
    if (distance > 1.0) steering_adjust = Kp * heading - min_command;
    else if (distance < 1.0) steering_adjust = Kp * heading + min_command;

    // float to double comparison lol
    if (Math.abs(steering_adjust) < AimbotConstants.MINIMUM_ADJUSTMENT) {
      DriverStation.reportWarning("Steering adjustment too low, Minimum: " + AimbotConstants.MINIMUM_ADJUSTMENT + " Value: " + steering_adjust, false);
      this.cancel(); // Too little adjustment is just straight
    } 
    else {
      SmartDashboard.putNumber("Adjustment", steering_adjust);
      SmartDashboard.putNumber("Distance", distance);
      SmartDashboard.putNumber("Left Speed", driveSubsystem.getLeftSpeed() - steering_adjust);
      SmartDashboard.putNumber("Right Speed", driveSubsystem.getRightSpeed() + steering_adjust);
      driveSubsystem.tankDrive(driveSubsystem.getLeftSpeed() - steering_adjust,
          driveSubsystem.getRightSpeed() + steering_adjust);
    }
  }
}
