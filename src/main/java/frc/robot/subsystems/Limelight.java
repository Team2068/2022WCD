// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {
  /** Creates a new Limelight. */
  public Limelight(int ledMode, int streamMode) {
    setLedMode(ledMode);
    setStreamMode(streamMode);
  }

  // basically a struct that contains all of the targetData we're pulling from the
  // limelight
  public class TargetData {
    public boolean hasTargets = false;
    public double horizontalOffset = 0; // Horizontal Offset From Crosshair To Target -29.8 to 29.8 degrees
    public double verticalOffset = 0; // Vertical Offset From Crosshair To Target -24.85 to 24.85 degrees
    public double targetArea = 0; // 0% of image to 100% of image
    public double skew = 0; // -90 degrees to 0 degree
    public double latency = 0; // The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
    public double shortSideLength = 0; // Sidelength of shortest side of the fitted bounding box (pixels)
    public double longSideLength = 0; // Sidelength of longest side of the fitted bounding box (pixels)
    public double horizontalSideLength = 0; // Horizontal sidelength of the rough bounding box (0 - 320 pixels)
    public double verticalSideLength = 0; // Vertical sidelength of the rough bounding box (0 - 320 pixels)
  }

  // global (sort of) data object with up to date limelight data
  private TargetData targetData = new TargetData();

  @Override
  public void periodic() {

    final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    final NetworkTableEntry tx = table.getEntry("tx");
    final NetworkTableEntry ty = table.getEntry("ty");
    final NetworkTableEntry ta = table.getEntry("ta");

    // read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    final String stream, cam;

    if (getStreamMode() == Constants.LimelightConstants.StreamMode.PIP_MAIN) {
      stream = "Main";
    } else {
      stream = "Secondary";
    }

    if (getCameraMode() == Constants.LimelightConstants.CamMode.VISION) {
      cam = "Vision";
    } else {
      cam = "Driver";
    }

    // post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
    SmartDashboard.putString("Stream Mode", stream);
    SmartDashboard.putString("Camera Mode", cam);
    updateTargetData(table);
  }

  private void updateTargetData(NetworkTable table) {
    targetData.hasTargets = table.getEntry("tv").getBoolean(false);
    targetData.horizontalOffset = table.getEntry("tx").getDouble(0.0);
    targetData.verticalOffset = table.getEntry("ty").getDouble(0.0);
    targetData.targetArea = table.getEntry("ta").getDouble(0.0);
    targetData.skew = table.getEntry("ts").getDouble(0.0);
    targetData.latency = table.getEntry("tl").getDouble(0.0);
    targetData.shortSideLength = table.getEntry("tshort").getDouble(0.0);
    targetData.longSideLength = table.getEntry("tlong").getDouble(0.0);
    targetData.horizontalSideLength = table.getEntry("thor").getDouble(0.0);
    targetData.verticalSideLength = table.getEntry("tvert").getDouble(0.0);
  }

  public TargetData getTargetData() {
    return targetData;
  }

  public void setCameraMode(int newCameraMode) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(newCameraMode);
  }

  public void setLedMode(int newLedMode) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(newLedMode);
  }

  public void setPipeline(int newPipeline) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(newPipeline);
  }

  public void setSnapshotMode(int newSnapshotMode) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("snapshot").setNumber(newSnapshotMode);
  }

  public void setStreamMode(int newStreamMode) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(newStreamMode);
  }

  public int getCameraMode() {
    return (int) NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getDouble(0.0);
  }

  public int getLedMode() {
    return (int) NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").getDouble(0.0);
  }

  public int getPipeline() {
    return (int) NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").getDouble(0.0);
  }

  public int getSnapshotMode() {
    return (int) NetworkTableInstance.getDefault().getTable("limelight").getEntry("snapshot").getDouble(0.0);
  }

  public int getStreamMode() {
    return (int) NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").getDouble(0.0);
  }
}
