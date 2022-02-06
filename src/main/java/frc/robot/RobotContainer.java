// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.LimelightConstants;
import frc.robot.commands.Aimbot;
import frc.robot.commands.SetShooterPower;
import frc.robot.commands.SlowOff;
import frc.robot.commands.SlowOn;
import frc.robot.commands.SwitchLidarMode;
import frc.robot.commands.SwitchPipeline;
import frc.robot.commands.TankDrive;
import frc.robot.commands.TurboOff;
import frc.robot.commands.TurboOn;
import frc.robot.commands.AlignClimber;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LidarSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.commands.ToggleCameraMode;
import frc.robot.commands.ToggleStreamMode;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.LidarSubsystem.LidarConfiguration;
import frc.robot.subsystems.Pigeon;

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
  private final ColorSensor colorSensor = new ColorSensor();
  private final Pigeon pigeon = new Pigeon(0);
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final XboxController driverController = new XboxController(DriveConstants.driverController);
  private final XboxController mechanismController = new XboxController(DriveConstants.mechanismController);
  private final ShooterSubsystem shooter = new ShooterSubsystem();
  private final LidarSubsystem lidar = new LidarSubsystem(LidarConfiguration.DEFAULT);
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

    driverY.whileHeld(new SetShooterPower(shooter));
    driverX.whileHeld(new Aimbot(limelight, driveSubsystem));
    driverB.toggleWhenPressed(new AlignClimber(colorSensor, driveSubsystem));
    driverA.whenPressed(new SwitchPipeline(limelight));
  }
  // driverController

  // SmartDashboard Commands
  private void setUpSmartDashboardCommands() {
    SmartDashboard.putData("Toggle Camera Mode", new ToggleCameraMode(limelight));
    SmartDashboard.putData("Toggle Stream Mode", new ToggleStreamMode(limelight));
    SmartDashboard.putData("Toggle Pipeline", new SwitchPipeline(limelight));
    SmartDashboard.putData("Toggle Lidar Mode", new SwitchLidarMode(lidar));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * 
   * 
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autonomousChooser.getSelected();
  }
}
