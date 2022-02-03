// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final int CURRENT_LIMIT = 30;
    public final static double ROBOT_HEIGHT = 0.0;// adjust

    public final static class DriveConstants {
        public static final int FRONT_LEFT = 4;
        public static final int BACK_LEFT = 5;
        public static final int FRONT_RIGHT = 2;
        public static final int BACK_RIGHT = 1;

        public static final double NORMAL_SPEED = .5;
        public static final double SLOW_SPEED = 0.25;
        public static final int TURBO_SPEED = 1;

        public static final int driverController = 0;
        public static int mechanismController;
        public static int tankDrive;
    }

    public final static class ControllerConstants {
        public static final int RIGHT_TRIGGER = 3;
        public static final int LEFT_TRIGGER = 2;
        public static final double TRIGGER_ACTIVATION_THRESHOLD = .3;
    }

    public final static class GameElementConstants {
        public final static double UPPER_HUB = 2.64; // meters
        public final static double LOWER_HUB = 1.04; // meters
        public final static double HIGH_HEIGHT = UPPER_HUB - ROBOT_HEIGHT;
        public final static double LOW_HEIGHT = LOWER_HUB - ROBOT_HEIGHT;
    }

    public final static class LimelightConstants {
        public final static class LedMode {
            public final static int DEFAULT = 0;
            public final static int FORCE_OFF = 1;
            public final static int FORCE_BLINK = 2;
            public final static int FORCE_ON = 3;
        }

        public final static class CamMode {
            public final static int VISION = 0;
            public final static int DRIVER = 1;
        }

        public final static class StreamMode {
            public final static int STANDARD = 0;
            public final static int PIP_MAIN = 1;
            public final static int PIP_SECONDARY = 2;
        }

        public final static class SnapshotMode {
            public final static int NO_SNAPSHOT = 0;
            public final static int TWO_SNAPSHOTS = 1;
        }

        public final static class Pipelines {
            public final static int REFLECTIVE_TAPE = 0;
            public final static int RED_BALLS = 1;
            public final static int BLUE_BALLS = 2;
        }

        public final static double LIMELIGHT_HEIGHT = 29.5; // This is for testing, needs to be changed for actual robot
        public final static double LIMELIGHT_ANGLE = 0;
    }

    public final static class ShooterConstants {
        public final static int FLYWHEEL_1 = 8;
        public final static int FLYWHEEL_2 = 9;
    }

    public final static class AimbotConstants {
        public static final double maxSpeed = 0.3;
        public static final double minimumAdjustment = 0.001;
    }

    public final static class TrajectoryConstants {
        public static final double ksVolts = 0.16947;
        public static final double kvVoltSecondsPerMeter = 0.91178;
        public static final double kaVoltSecondsSquaredPerMeter = 0.098652;

        // Note: this is from the old run of trajectory
        public static final double kPDriveVel = 2.2971;

        // Ramset constants
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        public static final double kMaxSpeedMetersPerSecond = .40;
        public static final double kMaxAccelerationMetersPerSecondSquared = .40;

        public static final double kTrackWidth = 0.69;
    }
}
