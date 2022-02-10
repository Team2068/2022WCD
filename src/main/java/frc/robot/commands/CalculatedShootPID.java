// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class CalculatedShootPID extends PIDCommand {
  /** Creates a new CalculatedShootPID. */
  public CalculatedShootPID(ShooterSubsystem shooterSubsystem) {
    super(
        // The controller that the command will use
        new PIDController(0.098005, 0, 0),
        // This should return the measurement
        () -> shooterSubsystem.flywheel1.getEncoder().getVelocity(),
        // This should return the setpoint (can also be a constant)
        () -> 3000,
        // This uses the output
        output -> {
          // Use the output here
          SmartDashboard.putNumber("pid setpoint", output);
          shooterSubsystem.rampUpShooter(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
