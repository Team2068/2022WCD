// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class NavX2 extends SubsystemBase{
  private AHRS ahrs = new AHRS();

  /** Creates a new NavX2. */
  public NavX2() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public double getAngle(){
    return ahrs.getAngle();
  }

  public double getFusedHeading(){
    return ahrs.getFusedHeading();
  }


  @Override
  public void periodic(){
    SmartDashboard.putNumber("Angle", ahrs.getAngle());
    SmartDashboard.putNumber("Fused Angle", getFusedHeading());
  }
}
