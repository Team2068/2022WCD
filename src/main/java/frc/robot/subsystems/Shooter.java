// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  public Shooter() {}
  private CANSparkMax leftSpinner = new CANSparkMax(Constants.ShooterConstants.leftShooter, MotorType.kBrushed);
  private CANSparkMax rightSpinner = new CANSparkMax(Constants.ShooterConstants.rightShooter, MotorType.kBrushed);

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setPower(double power){
    leftSpinner.set(power);
    rightSpinner.set(power);
  }
}
