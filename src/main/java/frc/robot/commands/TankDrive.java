/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class TankDrive extends CommandBase {

  private DriveSubsystem driveSubsystem;
  private XboxController controller;

  public TankDrive(DriveSubsystem driveSubsystem, XboxController controller) {
    this.driveSubsystem = driveSubsystem;
    this.controller = controller;
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftSpeed = controller.getLeftY();
    double rightSpeed = controller.getRightY();

    SmartDashboard.putNumber("Command Left", leftSpeed);
    SmartDashboard.putNumber("Command Right", rightSpeed);

    driveSubsystem.tankDrive(leftSpeed, rightSpeed * -1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}