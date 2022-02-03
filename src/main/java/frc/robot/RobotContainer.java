// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.LimelightConstants;
import frc.robot.Constants.TrajectoryConstants;
import frc.robot.commands.Aimbot;
import frc.robot.commands.SetShooterPower;
import frc.robot.commands.SlowOff;
import frc.robot.commands.SlowOn;
import frc.robot.commands.SwitchPipeline;
import frc.robot.commands.TankDrive;
import frc.robot.commands.TurboOff;
import frc.robot.commands.TurboOn;
import frc.robot.commands.climberAlign;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.commands.ToggleCameraMode;
import frc.robot.commands.ToggleStreamMode;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Limelight limelight = new Limelight(LimelightConstants.LedMode.DEFAULT,
      LimelightConstants.CamMode.VISION);
  private final ColorSensor color_sensor = new ColorSensor();
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final XboxController driverController = new XboxController(DriveConstants.driverController);
  private final XboxController mechanismController = new XboxController(DriveConstants.mechanismController);
  private final ShooterSubsystem shooter = new ShooterSubsystem();

  private final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(
      TrajectoryConstants.kTrackWidth);

  private SendableChooser<Command> autonomousChooser = new SendableChooser<Command>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    setUpSmartDashboardCommands();
    driveSubsystem.setDefaultCommand(new TankDrive(driveSubsystem, driverController));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    Trigger driverRightTrigger = new Trigger(() -> driverController
        .getRawAxis(ControllerConstants.RIGHT_TRIGGER) > ControllerConstants.TRIGGER_ACTIVATION_THRESHOLD);
    Trigger driverLeftTrigger = new Trigger(() -> driverController
        .getRawAxis(ControllerConstants.LEFT_TRIGGER) > ControllerConstants.TRIGGER_ACTIVATION_THRESHOLD);
    JoystickButton driverRightBumper = new JoystickButton(driverController, Button.kRightBumper.value);
    JoystickButton driverLeftBumper = new JoystickButton(driverController, Button.kLeftBumper.value);

    JoystickButton driverY = new JoystickButton(driverController, Button.kY.value);
    JoystickButton driverX = new JoystickButton(driverController, Button.kX.value);
    JoystickButton driverB = new JoystickButton(driverController, Button.kB.value);
    JoystickButton driverA = new JoystickButton(driverController, Button.kA.value);
    JoystickButton[] drivers = { driverY, driverX, driverB, driverA };

    Trigger mechanismRightTrigger = new Trigger(() -> mechanismController
        .getRawAxis(ControllerConstants.RIGHT_TRIGGER) > ControllerConstants.TRIGGER_ACTIVATION_THRESHOLD);
    Trigger mechanismLeftTrigger = new Trigger(() -> mechanismController
        .getRawAxis(ControllerConstants.LEFT_TRIGGER) > ControllerConstants.TRIGGER_ACTIVATION_THRESHOLD);
    JoystickButton mechanismLeftBumper = new JoystickButton(mechanismController, Button.kRightBumper.value);
    JoystickButton mechanismRightBumper = new JoystickButton(mechanismController, Button.kLeftBumper.value);

    JoystickButton mechanismY = new JoystickButton(mechanismController, Button.kY.value);
    JoystickButton mechanismA = new JoystickButton(mechanismController, Button.kA.value);
    JoystickButton mechanismB = new JoystickButton(mechanismController, Button.kB.value);
    JoystickButton mechanismX = new JoystickButton(mechanismController, Button.kX.value);

    driverRightTrigger.whenActive(new TurboOn(driveSubsystem)).whenInactive(new TurboOff(driveSubsystem));
    driverLeftTrigger.whenActive(new SlowOn(driveSubsystem)).whenInactive(new SlowOff(driveSubsystem));

    // drivers[0] should do actual climbing
    drivers[0].whileHeld(new SetShooterPower(shooter));
    drivers[1].whileHeld(new Aimbot(limelight, driveSubsystem));
    drivers[2].toggleWhenPressed(new climberAlign(color_sensor, driveSubsystem));
    drivers[3].whenPressed(new SwitchPipeline(limelight));
  }

  // SmartDashboard Commands
  private void setUpSmartDashboardCommands() {
    SmartDashboard.putData("Toggle Camera Mode", new ToggleCameraMode(limelight));
    SmartDashboard.putData("Toggle Stream Mode", new ToggleStreamMode(limelight));
    SmartDashboard.putData("Toggle Pipeline", new SwitchPipeline(limelight));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   * 
   * @param List
   *
   * 
   * 
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // AutoTrajectory command = new AutoTrajectory(driveSubsystem);
    // return command;

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
    return ramseteCommand.andThen(() -> driveSubsystem.tankDriveVolts(0,0));

    // An ExampleCommand will run in autonomous
    // return autonomousChooser.getSelected();
  }
}
