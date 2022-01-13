// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveSubsystem;

public class climberAlign extends CommandBase {
  ColorSensor cSensor;
  DriveSubsystem driveSubsystem;
  
  /** Creates a new climberAlign. */
  public climberAlign() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(cSensor);
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //If censor found
    if(!cSensor.checkSensor()){
      this.end(true);
    }
    cSensor.color_matcher.addColorMatch(Color.kBlack);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double driveSpeed = 0.1;
    Color detected_Color = cSensor.getColor();
    ColorMatchResult match = cSensor.matchColorTo(detected_Color);
    if(match.color == Color.kBlack){
      this.cancel();
    }else{
      driveSubsystem.tankDrive(-driveSpeed, driveSpeed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(interrupted){
      //Display the that the sensor was not found
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
