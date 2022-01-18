// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.sql.Driver;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveSubsystem;

public class climberAlign extends CommandBase {
  ColorSensor color_sensor;
  DriveSubsystem driveSubsystem;

  /** Creates a new climberAlign. */
  public climberAlign(ColorSensor color_sensor, DriveSubsystem driveSubsystem) {
    this.color_sensor = color_sensor;
    this.driveSubsystem = driveSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(color_sensor);
    addRequirements(driveSubsystem);
  }
  private Color tapeBlack = new Color(0.266,0.479,0.253);
  private Color carpetGrey = new Color(0.2697,0.48242,0.248);

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // If censor found
    if (!color_sensor.found()) {
      this.end(true);
    }
    color_sensor.color_matcher.addColorMatch(tapeBlack);
    color_sensor.color_matcher.addColorMatch(carpetGrey);
    color_sensor.color_matcher.addColorMatch(Color.kWhite);
    color_sensor.color_matcher.addColorMatch(Color.kRed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double driveSpeed = 1;

    Color detected_Color = color_sensor.getColor();
    ColorMatchResult match = color_sensor.matchColorTo(detected_Color);

    DriverStation.reportWarning("Detected: " + match.color.red + "," + match.color.green + "," + match.color.blue
        + " with confidence: " + match.confidence, false);

    if (match.color == tapeBlack && match.confidence >= 0.85) {
      // indicate or activate the climber
      DriverStation.reportWarning("Black", false);
      this.end(false);
    }
      DriverStation.reportWarning("Driving", false);
      driveSubsystem.tankDrive(0.3, -0.3);
    return;
  } 

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      DriverStation.reportWarning("Color Sensor not found.", true);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
