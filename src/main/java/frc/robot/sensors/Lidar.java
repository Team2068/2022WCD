package frc.robot.sensors;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class Lidar {
    public enum LidarConfiguration {
        DEFAULT, // Default mode, balanced performance
        SHORT_RANGE, // Short range, high speed
        DEFAULT_HIGH_SPEED, // Default range, higher speed short range
        MAXIMUM_RANGE, // Maximum range
        HIGH_SENSITIVE, // High sensitivity detection, high erroneous measurements
        LOW_SENSITIVE // Low sensitivity detection, low erroneous measurements
    }

    private I2C _lidar = new I2C(Port.kMXP, 0x62);

    private LidarConfiguration config = null;

    public Lidar(LidarConfiguration configuration) {
        changeMode(configuration);
    }

    public Lidar() {
        this(LidarConfiguration.DEFAULT);
    }

    public void changeMode(LidarConfiguration configuration) {
        config = configuration;
        switch (configuration) {
            case DEFAULT: // Default mode, balanced performance
                _lidar.write(0x02, 0x80); // Default
                _lidar.write(0x04, 0x08); // Default
                _lidar.write(0x1c, 0x00); // Default
                break;

            case SHORT_RANGE: // Short range, high speed
                _lidar.write(0x02, 0x1d);
                _lidar.write(0x04, 0x08); // Default
                _lidar.write(0x1c, 0x00); // Default
                break;

            case DEFAULT_HIGH_SPEED: // Default range, higher speed short range
                _lidar.write(0x02, 0x80); // Default
                _lidar.write(0x04, 0x00);
                _lidar.write(0x1c, 0x00); // Default
                break;

            case MAXIMUM_RANGE: // Maximum range
                _lidar.write(0x02, 0xff);
                _lidar.write(0x04, 0x08); // Default
                _lidar.write(0x1c, 0x00); // Default
                break;

            case HIGH_SENSITIVE: // High sensitivity detection, high erroneous measurements
                _lidar.write(0x02, 0x80); // Default
                _lidar.write(0x04, 0x08); // Default
                _lidar.write(0x1c, 0x80);
                break;

            case LOW_SENSITIVE: // Low sensitivity detection, low erroneous measurements
                _lidar.write(0x02, 0x80); // Default
                _lidar.write(0x04, 0x08); // Default
                _lidar.write(0x1c, 0xb0);
                break;
        }
    }

    // Returns the distance to the object in centimeters
    public double getDistance(boolean biasCorrection) {
        if (biasCorrection) {
            _lidar.write(0x00, 0x04);
        } else {
            _lidar.write(0x00, 0x03);
        }

        byte[] distanceArray = new byte[2];

        _lidar.read(0x8F, 2, distanceArray);

        double distance = (distanceArray[0] << 8) + distanceArray[1];

        return distance;
    }

    public void reset() {
        _lidar.write(0x00, 0x00);
    }

    public LidarConfiguration getCurrentConfiguration() {
        return config;
    }
}
