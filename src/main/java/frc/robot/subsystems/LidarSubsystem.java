// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sensors.Lidar;

public class LidarSubsystem extends SubsystemBase {
  /** Creates a new LidarSubsystem. */

  Lidar _lidar = new Lidar();

  public LidarSubsystem() {}

  @Override
  public void periodic() {
    double distance = _lidar.getDistance(false);
    SmartDashboard.putNumber("Lidar Sensor Distance",distance);
  }
}
