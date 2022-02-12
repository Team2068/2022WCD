// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pigeon extends SubsystemBase {
  // Making this a separate subsystem for now
  // In the future this may be used for more than just the drivesubsystem?

  public Pigeon(int canId) {
    _gyro = new PigeonIMU(canId);
    PigeonIMU.GeneralStatus genStatus = new PigeonIMU.GeneralStatus();
    _gyro.getGeneralStatus(genStatus);
  }

  // Convert from fixed point notation to G's
  final double fixedConstant = 16384;

  // Actual pigeon object
  PigeonIMU _gyro;

  // Displaying Data to shuffleboard
  ShuffleboardTab tab = Shuffleboard.getTab("Gyro");
  NetworkTableEntry accelYaw = tab.add("Accelerometer Yaw", 0).getEntry();
  NetworkTableEntry accelPitch = tab.add("Accelerometer Pitch", 0).getEntry();
  NetworkTableEntry accelRoll = tab.add("Accelerometer Roll", 0).getEntry();
  NetworkTableEntry gyroYaw = tab.add("Gyro Yaw", 0).getEntry();
  NetworkTableEntry gyroPitch = tab.add("Gyro Pitch", 0).getEntry();
  NetworkTableEntry gyroRoll = tab.add("Gyro Roll", 0).getEntry();
  NetworkTableEntry gyroHeading = tab.add("Gyro heading", 0).getEntry();
  NetworkTableEntry magnometer = tab.add("Magnometer", 0).getEntry();
  
  double prevXAccel, prevYAccel, prevZAccel = 0;

  @Override
  public void periodic() {
  
    // Raw Gyro yaw is basically the same as getting the fused heading, just with slightly more error

    double[] ypr = getGyroYPR();
    short[] accel = getAccelerometerYPR();

    gyroYaw.setDouble(ypr[0]);
    gyroPitch.setDouble(ypr[1]);
    gyroRoll.setDouble(ypr[2]);

    accelYaw.setDouble(accel[0] / fixedConstant);
    accelPitch.setDouble(accel[1] / fixedConstant);
    accelRoll.setDouble(accel[2] / fixedConstant);

    gyroHeading.setDouble(getHeading(false));
    magnometer.setDouble(getAbsoluteHeading());

    collisionDetection(fixedConstant, accel);
  }

  public double getHeading(boolean isInvert){
    if(isInvert){
      return _gyro.getFusedHeading() * -1;
    }
    return _gyro.getFusedHeading();
  }

  public double getAbsoluteHeading(){
    return _gyro.getAbsoluteCompassHeading();
  }

  public short[] getAccelerometerYPR(){
    short[] tiltAngles = new short[3];
    _gyro.getBiasedAccelerometer(tiltAngles);
    return tiltAngles;
  }

  public double[] getGyroYPR(){
    double[] ypr = new double[3];
    _gyro.getYawPitchRoll(ypr);
    return ypr;
  }

  public void collisionDetection (double fixedConstant, short[] accelerations) {
    // Placeholder 0.5 value
    double maxJerk = 1.3;

    double xAccel = accelerations[0] / fixedConstant;
    // double yAccel = accelerations[1] / fixedConstant;
    // double zAccel = accelerations[2] / fixedConstant;

    double xJerk = (xAccel - prevXAccel);
    // double yJerk =  (yAccel - prevYAccel)/.02;
    // double zJerk = (zAccel - prevZAccel)/.02;

    prevXAccel = xAccel;
    // prevYAccel = yAccel;
    // prevZAccel = zAccel;

    if (Math.abs(xJerk) >= maxJerk) {
      DriverStation.reportWarning("Collision", false);
    }
  }
}
