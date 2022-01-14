// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

public class ColorSensor extends SubsystemBase{

  I2C.Port port = I2C.Port.kOnboard;  
  /** Creates a new ColorSensor. */
  public ColorSensor() {}

  ColorSensorV3 sensor = new ColorSensorV3(port);
  public ColorMatch color_matcher = new ColorMatch();


  public Color getColor(){
    return sensor.getColor();
  }

  public ColorMatchResult matchColorTo(Color currentColor){
    return color_matcher.matchClosestColor(currentColor);
  }

  public boolean isConnected(){
    return sensor.isConnected();
  }
}
