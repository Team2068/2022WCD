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

  double Ku = 0.011001;
  double Tu = 0.93;
  //1.2*(0.11814)/(0.75)\ 
  // new PIDController(0.6*(0.0017853), 0.00142824, (0.075)*(0.0017853)*(0.75)),

  public CalculatedShootPID(ShooterSubsystem shooterSubsystem) {
    super(
        // The controller that the command will use
        new PIDController(0.6*(0.0017853), .075*(0.0017853)/(0.75), (0.075)*(0.0017853)*(0.75)),
        // new PIDController(0.006, 0, 0.002),
        // This should return the measurement
        () -> shooterSubsystem.flywheel.getEncoder().getVelocity(),
        // This should return the setpoint (can also be a constant)
        () -> 4000,
        // This uses the output
        output -> {
          // Use the output here
          SmartDashboard.putNumber("PID Setpoint", output);
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
