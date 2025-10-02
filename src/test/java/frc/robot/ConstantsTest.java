// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for Constants
 */
class ConstantsTest {

  @Test
  void testOIConstants() {
    assertTrue(Constants.OI.DRIVER_CONTROLLER_PORT >= 0, "Controller port should be non-negative");
    assertTrue(Constants.OI.DRIVER_CONTROLLER_PORT < 6, "Controller port should be valid USB port");
  }

  @Test
  void testDrivetrainConstants() {
    assertTrue(Constants.Drivetrain.SPEED_MULTIPLIER > 0,
        "Speed multiplier should be positive");
    assertTrue(Constants.Drivetrain.SPEED_MULTIPLIER <= 1.0,
        "Speed multiplier should not exceed 1.0");

    assertTrue(Constants.Drivetrain.MAX_ANGULAR_RATE_FRACTION > 0,
        "Angular rate fraction should be positive");
    assertTrue(Constants.Drivetrain.MAX_ANGULAR_RATE_FRACTION <= 1.0,
        "Angular rate fraction should not exceed 1.0");

    assertTrue(Constants.Drivetrain.DEADBAND_PERCENT >= 0,
        "Deadband should be non-negative");
    assertTrue(Constants.Drivetrain.DEADBAND_PERCENT < 0.5,
        "Deadband should be reasonable (< 50%)");
  }

  @Test
  void testSafetyConstants() {
    assertTrue(Constants.Safety.VOLTAGE_CRITICAL_THRESHOLD < Constants.Safety.VOLTAGE_WARNING_THRESHOLD,
        "Critical threshold should be lower than warning threshold");
    assertTrue(Constants.Safety.VOLTAGE_CRITICAL_THRESHOLD > 0,
        "Critical voltage should be positive");

    assertTrue(Constants.Safety.CURRENT_CRITICAL_THRESHOLD > Constants.Safety.CURRENT_WARNING_THRESHOLD,
        "Critical current should be higher than warning current");
    assertTrue(Constants.Safety.CURRENT_WARNING_THRESHOLD > 0,
        "Warning current should be positive");

    assertTrue(Constants.Safety.TEMPERATURE_CRITICAL_THRESHOLD > Constants.Safety.TEMPERATURE_WARNING_THRESHOLD,
        "Critical temp should be higher than warning temp");
    assertTrue(Constants.Safety.TEMPERATURE_WARNING_THRESHOLD > 0,
        "Warning temp should be positive");

    assertTrue(Constants.Safety.MONITORING_PERIOD_SECONDS > 0,
        "Monitoring period should be positive");
  }

  @Test
  void testAutoConstants() {
    assertTrue(Constants.Auto.DRIVE_FORWARD_SPEED > 0,
        "Drive speed should be positive");
    assertTrue(Constants.Auto.DRIVE_FORWARD_SPEED < 10.0,
        "Drive speed should be reasonable");

    assertTrue(Constants.Auto.DRIVE_FORWARD_DURATION > 0,
        "Drive duration should be positive");

    assertTrue(Constants.Auto.BALANCE_SPEED > 0,
        "Balance speed should be positive");
    assertTrue(Constants.Auto.BALANCE_ANGLE_TOLERANCE > 0,
        "Balance tolerance should be positive");
    assertTrue(Constants.Auto.BALANCE_ANGLE_TOLERANCE < 45,
        "Balance tolerance should be reasonable");
  }

  @Test
  void testVisionConstants() {
    assertNotNull(Constants.Vision.FRONT_CAMERA_NAME);
    assertNotNull(Constants.Vision.REAR_CAMERA_NAME);

    assertTrue(Constants.Vision.MAX_VISION_DISTANCE > 0,
        "Max vision distance should be positive");
    assertTrue(Constants.Vision.MAX_AMBIGUITY >= 0 && Constants.Vision.MAX_AMBIGUITY <= 1,
        "Ambiguity should be between 0 and 1");
    assertTrue(Constants.Vision.DISTANCE_WEIGHT > 0,
        "Distance weight should be positive");

    assertEquals(3, Constants.Vision.VISION_MEASUREMENT_STD_DEVS.length,
        "Should have 3 standard deviation values");

    // Verify camera orientations
    assertEquals(0.0, Constants.Vision.FRONT_CAMERA_YAW_DEGREES,
        "Front camera should face forward");
    assertEquals(180.0, Constants.Vision.REAR_CAMERA_YAW_DEGREES,
        "Rear camera should face backward");
  }
}
