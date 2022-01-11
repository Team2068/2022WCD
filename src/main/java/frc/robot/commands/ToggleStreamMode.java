/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;

public class ToggleStreamMode extends InstantCommand {
    /**
     * Creates a new ToggleStreamMode.
     */
    Limelight limelight;

    public ToggleStreamMode(Limelight limelight) {
        this.limelight = limelight;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(limelight);
    }

    @Override
    public void initialize() {
        switch (limelight.getStreamMode()) {
        case Constants.LimelightConstants.StreamMode.PIP_MAIN:
            limelight.setStreamMode(Constants.LimelightConstants.StreamMode.PIP_SECONDARY);
            break;
        case Constants.LimelightConstants.StreamMode.PIP_SECONDARY:
            limelight.setCameraMode(Constants.LimelightConstants.StreamMode.STANDARD);
        default:
            limelight.setStreamMode(Constants.LimelightConstants.StreamMode.PIP_MAIN);
            break;
        }
    }
}