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
  /** Creates a new Pigeon. */

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
  private ShuffleboardTab tab = Shuffleboard.getTab("Sensors");
  private NetworkTableEntry accelYaw = tab.add("Accelerometer Yaw", 0).getEntry();
  private NetworkTableEntry accelPitch = tab.add("Accelerometer Pitch", 0).getEntry();
  private NetworkTableEntry accelRoll = tab.add("Accelerometer Roll", 0).getEntry();
  private NetworkTableEntry gyroYaw = tab.add("Gyro Yaw", 0).getEntry();
  private NetworkTableEntry gyroPitch = tab.add("Gyro Pitch", 0).getEntry();
  private NetworkTableEntry gyroRoll = tab.add("Gyro Roll", 0).getEntry();
  private NetworkTableEntry gyroHeading = tab.add("Gyro heading", 0).getEntry();
  private NetworkTableEntry magnometer = tab.add("Magnometer", 0).getEntry();
  
  double prevXAccel, prevYAccel = 0;

  @Override
  public void periodic() {
  
    // Raw Gyro yaw is basically the same as getting the fused heading, just with slightly more error

    double[] ypr = new double[3];
    short[] accel = new short[3];

    _gyro.getYawPitchRoll(ypr);
    gyroYaw.setDouble(ypr[0]);
    gyroPitch.setDouble(ypr[1]);
    gyroRoll.setDouble(ypr[2]);

    _gyro.getBiasedAccelerometer(accel);
    accelYaw.setDouble(accel[0] / fixedConstant);
    accelPitch.setDouble(accel[1] / fixedConstant);
    accelRoll.setDouble(accel[2] / fixedConstant);

    gyroHeading.setDouble(_gyro.getFusedHeading());
    magnometer.setDouble(_gyro.getAbsoluteCompassHeading());

    collisionDetection(fixedConstant);
  }

  public void collisionDetection (double fixedConstant) {
    short[] accelerations = new short[3];
    _gyro.getBiasedAccelerometer(accelerations);

    double xAccel = accelerations[0] / fixedConstant;
    double yAccel = accelerations[1] / fixedConstant;

    double xJerk = (xAccel - prevXAccel)/.02;
    double yJerk = (yAccel - prevYAccel)/.02;

    prevXAccel = xAccel;
    prevYAccel = yAccel;

    if (xJerk >= 0.5 || yJerk >= 0.5) { //0.5 is just a placeholder number for now
      DriverStation.reportWarning("Collision", false);
    }
  }
}
