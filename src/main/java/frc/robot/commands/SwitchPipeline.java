// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;
 

public class SwitchPipeline extends InstantCommand {
  Limelight limelight;

  public SwitchPipeline(Limelight in) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.limelight = in;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch (limelight.getPipeline()) {
      case Constants.LimelightConstants.Pipelines.BLUE_BALLS:
          limelight.setPipeline(Constants.LimelightConstants.Pipelines.REFLECTIVE_TAPE);
          break;
      case Constants.LimelightConstants.Pipelines.REFLECTIVE_TAPE:
          limelight.setPipeline(Constants.LimelightConstants.Pipelines.RED_BALLS);
          break;
      case Constants.LimelightConstants.Pipelines.RED_BALLS:
          limelight.setPipeline(Constants.LimelightConstants.Pipelines.BLUE_BALLS);
          break;
      }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}
}
