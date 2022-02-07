// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveSubsystem;

public class AlignClimber extends CommandBase {
  ColorSensor colorSensor;
  DriveSubsystem driveSubsystem;

  /** Creates a new AlignClimber. */
  public AlignClimber(ColorSensor colorSensor, DriveSubsystem driveSubsystem) {
    this.colorSensor = colorSensor;
    this.driveSubsystem = driveSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(colorSensor);
    addRequirements(driveSubsystem);
  }
  private Color tapeBlack = new Color(0.259,0.473,0.268);
  private Color carpetGrey = new Color(0.263,0.478,0.259);

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    colorSensor.color_matcher.addColorMatch(tapeBlack);
    colorSensor.color_matcher.addColorMatch(carpetGrey);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double driveSpeed = -0.15;

    Color detected_Color = colorSensor.getColor();
    ColorMatchResult match = colorSensor.matchColorTo(detected_Color);
    if (match.color == tapeBlack) {
      // indicate or activate the climber
      this.cancel();
    }else{
      driveSubsystem.tankDrive(driveSpeed, -driveSpeed);
    }
  } 

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  public void checkColor(){
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
