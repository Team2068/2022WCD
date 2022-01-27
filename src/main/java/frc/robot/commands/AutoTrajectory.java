// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.TrajectoryConstants;
import frc.robot.subsystems.DriveSubsystem;

public class AutoTrajectory extends CommandBase {

  private static final DifferentialDriveKinematics kDriveKinematics =
  new DifferentialDriveKinematics(TrajectoryConstants.kTrackWidth);

  private DriveSubsystem driveSubsystem;

  /** Creates a new Trajectory. */
  public AutoTrajectory(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriverStation.reportWarning("Trajectory started", false);
    
    var autoVoltageConstraint =
    new DifferentialDriveVoltageConstraint(
      new SimpleMotorFeedforward(TrajectoryConstants.ksVolts, TrajectoryConstants.kvVoltSecondsPerMeter, TrajectoryConstants.kaVoltSecondsSquaredPerMeter),
    kDriveKinematics,
    10);

    TrajectoryConfig config =
    new TrajectoryConfig(TrajectoryConstants.kMaxSpeedMetersPerSecond, TrajectoryConstants.kMaxAccelerationMetersPerSecondSquared)
    .setKinematics(kDriveKinematics)
    .addConstraint(autoVoltageConstraint);

    Trajectory testTrajectory = TrajectoryGenerator.generateTrajectory(
      new Pose2d(0, 0, new Rotation2d(0)),
       List.of(
         new Translation2d(1, 1),
         new Translation2d(2, -1)
       ),
       new Pose2d(3, 0, new Rotation2d(0)),
        config);

      driveSubsystem.resetOdometry(testTrajectory.getInitialPose());

      RamseteCommand ramseteCommand = new RamseteCommand(
        testTrajectory,
        driveSubsystem::getPose,
        new RamseteController(TrajectoryConstants.kRamseteB, TrajectoryConstants.kRamseteZeta),
        new SimpleMotorFeedforward(TrajectoryConstants.ksVolts, 
        TrajectoryConstants.kvVoltSecondsPerMeter,
        TrajectoryConstants.kaVoltSecondsSquaredPerMeter),
        kDriveKinematics,
        driveSubsystem::getSpeeds,
        new PIDController(TrajectoryConstants.kPDriveVel, 0, 0),
        new PIDController(TrajectoryConstants.kPDriveVel, 0, 0),
        driveSubsystem::tankDriveVolts,
        driveSubsystem
      );

    DriverStation.reportWarning("Trajectory blue", false);
      ramseteCommand.andThen(() -> driveSubsystem.tankDriveVolts(0,0));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
