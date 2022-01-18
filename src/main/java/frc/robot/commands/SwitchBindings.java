// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class SwitchBindings extends CommandBase {
  private boolean isShooter = false;
  public JoystickButton[] drivers;

  private ColorSensor color_sensor;
  private Shooter shooter = new Shooter();
  private DriveSubsystem driveSubsystem;
  private Limelight limelight;


  /** Creates a new SwitchBindings. */
  public SwitchBindings(JoystickButton[] drivers, ColorSensor color_sensor, DriveSubsystem driveSubsystem, Limelight limelight) {
    this.drivers = drivers;
    this.color_sensor = color_sensor;
    this.driveSubsystem = driveSubsystem;
    this.limelight = limelight;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(color_sensor, driveSubsystem, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      configureShooterBindings(drivers);
  }

  private void configureClimberBindings(JoystickButton[] drivers){
  }

  private void configureShooterBindings(JoystickButton[] drivers){
    drivers[0].whileHeld(new SetShooterPower(shooter));
    drivers[1].whileHeld(new Aimbot(limelight, driveSubsystem));
    drivers[2].whileHeld(new climberAlign(color_sensor, driveSubsystem));
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
