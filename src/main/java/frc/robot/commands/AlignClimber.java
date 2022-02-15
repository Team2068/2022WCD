package frc.robot.commands;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveSubsystem;

public class AlignClimber extends CommandBase {
  
  ColorSensor colorSensor;
  DriveSubsystem driveSubsystem;

  private Color tapeBlack = new Color(0.259,0.473,0.268);
  private Color carpetGrey = new Color(0.263,0.478,0.259);

  public AlignClimber(ColorSensor colorSensor, DriveSubsystem driveSubsystem) {
    this.colorSensor = colorSensor;
    this.driveSubsystem = driveSubsystem;

    addRequirements(colorSensor);
    addRequirements(driveSubsystem);
  }

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
   
    if (match.color == tapeBlack) this.cancel();
    else driveSubsystem.tankDrive(driveSpeed, -driveSpeed);
  } 
}
