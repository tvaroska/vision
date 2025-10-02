// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  /**
   * Operator Interface (OI) constants
   */
  public static final class OI {
    public static final int DRIVER_CONTROLLER_PORT = 0;
  }

  /**
   * Drivetrain constants
   */
  public static final class Drivetrain {
    // Speed multiplier (1.0 = full speed, 0.1 = 10% speed)
    public static final double SPEED_MULTIPLIER = 0.1;

    // Maximum angular velocity as a fraction of theoretical max
    public static final double MAX_ANGULAR_RATE_FRACTION = 0.75; // 3/4 rotation per second

    // Deadband percentage for joystick inputs
    public static final double DEADBAND_PERCENT = 0.1; // 10% deadband
  }

  /**
   * Safety monitoring constants
   */
  public static final class Safety {
    // Voltage thresholds
    public static final double VOLTAGE_WARNING_THRESHOLD = 11.5; // Volts
    public static final double VOLTAGE_CRITICAL_THRESHOLD = 10.5; // Volts

    // Current thresholds
    public static final double CURRENT_WARNING_THRESHOLD = 200.0; // Amps
    public static final double CURRENT_CRITICAL_THRESHOLD = 250.0; // Amps

    // Temperature thresholds (Celsius)
    public static final double TEMPERATURE_WARNING_THRESHOLD = 70.0;
    public static final double TEMPERATURE_CRITICAL_THRESHOLD = 85.0;

    // Monitoring period
    public static final double MONITORING_PERIOD_SECONDS = 1.0;
  }

  /**
   * Autonomous constants
   */
  public static final class Auto {
    // Basic auto drive parameters
    public static final double DRIVE_FORWARD_SPEED = 1.0; // m/s
    public static final double DRIVE_FORWARD_DURATION = 2.0; // seconds

    // Auto balance parameters
    public static final double BALANCE_SPEED = 0.5; // m/s
    public static final double BALANCE_ANGLE_TOLERANCE = 2.5; // degrees

    // AprilTag alignment parameters (vision-based)
    public static final double APRILTAG_DISTANCE_METERS = 0.508; // 20 inches = 0.508 meters
    public static final double APRILTAG_POSITION_TOLERANCE = 0.05; // 5cm distance tolerance
    public static final double APRILTAG_ROTATION_TOLERANCE = 5.0; // 5 degrees yaw tolerance
    public static final double APRILTAG_MAX_SPEED = 1.0; // m/s max approach speed
    public static final double APRILTAG_MAX_ROTATION_SPEED = 1.0; // rad/s max rotation speed

    // AprilTag PID tuning constants [kP, kI, kD]
    public static final double[] APRILTAG_FORWARD_PID = {1.5, 0.0, 0.1}; // Forward/backward control
    public static final double[] APRILTAG_STRAFE_PID = {0.05, 0.0, 0.005}; // Left/right centering
    public static final double[] APRILTAG_ROTATION_PID = {0.08, 0.0, 0.01}; // Rotation to face tag
    public static final double APRILTAG_STRAFE_TOLERANCE = 2.0; // degrees yaw tolerance for centering
  }

  /**
   * Vision/AprilTag constants
   */
  public static final class Vision {
    // Field Configuration Mode
    // REAL_FIELD = Official 2025 Reefscape field with all 16 AprilTags
    // TRAINING_FIELD_1_TAG = Single AprilTag for basic testing
    // TRAINING_FIELD_2_TAGS = Two AprilTags for alignment testing
    // TRAINING_FIELD_3_TAGS = Three AprilTags for full pose estimation testing
    public static final frc.robot.subsystems.FieldConfiguration.FieldMode FIELD_MODE =
        frc.robot.subsystems.FieldConfiguration.FieldMode.REAL_FIELD;

    // Front Camera Configuration
    public static final String FRONT_CAMERA_NAME = "front_camera";
    public static final double FRONT_CAMERA_X_OFFSET = 0.3; // 0.3m forward from center
    public static final double FRONT_CAMERA_Y_OFFSET = 0.0; // Centered left/right
    public static final double FRONT_CAMERA_Z_OFFSET = 0.5; // 0.5m height above ground
    public static final double FRONT_CAMERA_ROLL_DEGREES = 0.0;
    public static final double FRONT_CAMERA_PITCH_DEGREES = 0.0;
    public static final double FRONT_CAMERA_YAW_DEGREES = 0.0; // Facing forward

    // Rear Camera Configuration
    public static final String REAR_CAMERA_NAME = "rear_camera";
    public static final double REAR_CAMERA_X_OFFSET = -0.3; // 0.3m behind center
    public static final double REAR_CAMERA_Y_OFFSET = 0.0; // Centered left/right
    public static final double REAR_CAMERA_Z_OFFSET = 0.5; // 0.5m height above ground
    public static final double REAR_CAMERA_ROLL_DEGREES = 0.0;
    public static final double REAR_CAMERA_PITCH_DEGREES = 0.0;
    public static final double REAR_CAMERA_YAW_DEGREES = 180.0; // Facing backward

    // Vision measurement standard deviations (trust levels)
    // Lower values = more trust in vision
    // Higher values = less trust in vision
    // [x, y, rotation] in meters and radians
    public static final double[] VISION_MEASUREMENT_STD_DEVS = {0.5, 0.5, 0.5};

    // Distance-based standard deviation scaling
    // Vision gets less trustworthy at longer distances
    public static final double DISTANCE_WEIGHT = 2.0; // Multiplier for distance effect

    // Maximum distance to trust vision measurements (meters)
    public static final double MAX_VISION_DISTANCE = 4.0;

    // Ambiguity threshold (0-1, lower is better)
    // Reject detections with ambiguity above this value
    public static final double MAX_AMBIGUITY = 0.3;

    // Training Field Configuration
    // Training field dimensions (smaller practice area)
    public static final double TRAINING_FIELD_LENGTH = 8.0; // meters (smaller than real field)
    public static final double TRAINING_FIELD_WIDTH = 6.0; // meters

    // Tag 1 - Center position (always used in 1, 2, and 3 tag modes)
    public static final int TRAINING_TAG_1_ID = 1;
    public static final double TRAINING_TAG_1_X = 4.0; // Center of field wall
    public static final double TRAINING_TAG_1_Y = 3.0; // Center laterally
    public static final double TRAINING_TAG_1_Z = 1.45; // Standard AprilTag mounting height
    public static final double TRAINING_TAG_1_ROLL_DEGREES = 0.0;
    public static final double TRAINING_TAG_1_PITCH_DEGREES = 0.0;
    public static final double TRAINING_TAG_1_YAW_DEGREES = 180.0; // Facing toward robot start

    // Tag 2 - Left position (used in 2 and 3 tag modes)
    public static final int TRAINING_TAG_2_ID = 2;
    public static final double TRAINING_TAG_2_X = 4.0; // Same depth as Tag 1
    public static final double TRAINING_TAG_2_Y = 1.0; // Left side
    public static final double TRAINING_TAG_2_Z = 1.45; // Same height
    public static final double TRAINING_TAG_2_ROLL_DEGREES = 0.0;
    public static final double TRAINING_TAG_2_PITCH_DEGREES = 0.0;
    public static final double TRAINING_TAG_2_YAW_DEGREES = 180.0; // Facing toward robot start

    // Tag 3 - Right position (used in 3 tag mode only)
    public static final int TRAINING_TAG_3_ID = 3;
    public static final double TRAINING_TAG_3_X = 4.0; // Same depth as Tags 1 and 2
    public static final double TRAINING_TAG_3_Y = 5.0; // Right side
    public static final double TRAINING_TAG_3_Z = 1.45; // Same height
    public static final double TRAINING_TAG_3_ROLL_DEGREES = 0.0;
    public static final double TRAINING_TAG_3_PITCH_DEGREES = 0.0;
    public static final double TRAINING_TAG_3_YAW_DEGREES = 180.0; // Facing toward robot start
  }
}
