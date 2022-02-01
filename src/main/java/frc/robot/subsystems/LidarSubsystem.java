// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sensors.Lidar;
import frc.robot.sensors.Lidar.LidarConfiguration;

public class LidarSubsystem extends SubsystemBase {
  /** Creates a new LidarSubsystem. */

  private Lidar _lidar = null;

  public LidarSubsystem() {
    this(LidarConfiguration.DEFAULT);
  }

  public LidarSubsystem(LidarConfiguration configuration) {
    _lidar = new Lidar(configuration);
  }

  @Override
  public void periodic() {
    double distance = _lidar.getDistance(true) / 2.54; // converting to inches here :D
    SmartDashboard.putNumber("Lidar Sensor Distance",distance);
    String currentConfig = "";
    switch(_lidar.getCurrentConfiguration()) {
      case DEFAULT:
        currentConfig = "Default";
        break;
      case SHORT_RANGE:
        currentConfig = "Short Range";
        break;
      case DEFAULT_HIGH_SPEED:
        currentConfig = "Default High Speed";
        break;
      case MAXIMUM_RANGE:
        currentConfig = "Maxmimum Range";
        break;
      case HIGH_SENSITIVE:
        currentConfig = "High sensitivity";
        break;
      case LOW_SENSITIVE:
        currentConfig = "Low sensitivity";
        break;
    }
    SmartDashboard.putString("Lidar Config", currentConfig);
  }
}
