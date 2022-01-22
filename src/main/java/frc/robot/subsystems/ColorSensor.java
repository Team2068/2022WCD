// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

public class ColorSensor extends SubsystemBase{

  public ColorMatch color_matcher = new ColorMatch();
  public Color currentColor;

  I2C.Port port = I2C.Port.kOnboard; 

  public ColorSensor() {}
  ColorSensorV3 sensor = new ColorSensorV3(port);

  public Color getColor(){
    return sensor.getColor();
  }

  public ColorMatchResult matchColorTo(Color currentColor){
    return color_matcher.matchClosestColor(currentColor);
  }

  public boolean found(){
    return sensor.isConnected();
  }
  
  @Override
  public void periodic(){
    currentColor = getColor();
    Double[] values = {currentColor.red, currentColor.green, currentColor.blue};
    SmartDashboard.putNumber("Current Color (R): ", currentColor.red);
    SmartDashboard.putNumber("Current Color (G): ", currentColor.green);
    SmartDashboard.putNumber("Current Color (B): ", currentColor.blue);
    SmartDashboard.putNumberArray("Color (RGB): ", values);
  }
}
