// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.AimbotConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AimbotPID extends PIDCommand {
  /** Creates a new AimbotPID. */

  public AimbotPID(DriveSubsystem driveSubsystem, Limelight limelight) {
    super(
        new PIDController(AimbotConstants.Kp, AimbotConstants.Ki, AimbotConstants.Kd),
        // This is how far away we are from the
        () -> -limelight.getTargetData().horizontalOffset,
        // We want the distance from the middle to be 0, therefore we keep it 0
        () -> 0,
        output -> {
          // Use the output here
          double baseSpeed = 0.2;
          double speed = output * baseSpeed;
          driveSubsystem.tankDrive(speed, -speed);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(driveSubsystem);
    addRequirements(limelight);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
