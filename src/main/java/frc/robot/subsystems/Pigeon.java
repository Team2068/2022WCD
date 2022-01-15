// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;

public class Pigeon{
  /** Creates a new Pigeon. */

  // Making this a separate subsystem for now
  // In the future this may be used for more than just the drivesubsystem?

  public Pigeon(int canId) {
    _gyro = new PigeonIMU(canId);
    PigeonIMU.GeneralStatus genStatus = new PigeonIMU.GeneralStatus();
    _gyro.getGeneralStatus(genStatus);
  }

  // Actual pigeon object
  PigeonIMU _gyro;

  public double getHeading(){
    return _gyro.getFusedHeading();
  }

  public double[] getAngle(){
    double[] tiltAngles = new double[3];
    _gyro.getAccelerometerAngles(tiltAngles);
    return tiltAngles;
  }
}
