// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.LidarSubsystem;
import frc.robot.subsystems.LidarSubsystem.LidarConfiguration;

public class SwitchLidarMode extends InstantCommand {
  private LidarSubsystem lidar;
  /** Creates a new SwitchLidarMode. */
  public SwitchLidarMode(LidarSubsystem in) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.lidar = in;
    addRequirements(lidar);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch(lidar.getCurrentConfiguration()) {
      case DEFAULT:
        lidar.changeMode(LidarConfiguration.SHORT_RANGE);
        break;
      case SHORT_RANGE:
        lidar.changeMode(LidarConfiguration.DEFAULT_HIGH_SPEED);
        break;
      case DEFAULT_HIGH_SPEED:
        lidar.changeMode(LidarConfiguration.MAXIMUM_RANGE);
        break;
      case MAXIMUM_RANGE:
        lidar.changeMode(LidarConfiguration.HIGH_SENSITIVE);
        break;
      case HIGH_SENSITIVE:
        lidar.changeMode(LidarConfiguration.LOW_SENSITIVE);
        break;
      case LOW_SENSITIVE:
        lidar.changeMode(LidarConfiguration.DEFAULT);
        break;
    }
  }
}
