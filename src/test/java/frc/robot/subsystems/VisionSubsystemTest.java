// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.wpi.first.hal.HAL;
import frc.robot.Constants;

/**
 * Unit tests for VisionSubsystem
 */
class VisionSubsystemTest {
  @BeforeEach
  void setup() {
    assert HAL.initialize(500, 0);
  }

  @Test
  void testFrontCameraConstants() {
    // Test front camera name is set
    assertNotNull(Constants.Vision.FRONT_CAMERA_NAME);
    assertFalse(Constants.Vision.FRONT_CAMERA_NAME.isEmpty());

    // Test front camera offset values are reasonable
    assertTrue(Math.abs(Constants.Vision.FRONT_CAMERA_Z_OFFSET) < 2.0,
        "Front camera height should be reasonable (< 2m)");
    assertTrue(Constants.Vision.FRONT_CAMERA_X_OFFSET >= 0,
        "Front camera should be forward or centered");

    // Test front camera angles are reasonable
    assertTrue(Math.abs(Constants.Vision.FRONT_CAMERA_PITCH_DEGREES) < 90,
        "Front camera pitch should be reasonable");
    assertTrue(Math.abs(Constants.Vision.FRONT_CAMERA_ROLL_DEGREES) < 90,
        "Front camera roll should be reasonable");
    assertEquals(0.0, Constants.Vision.FRONT_CAMERA_YAW_DEGREES,
        "Front camera should face forward (0°)");
  }

  @Test
  void testRearCameraConstants() {
    // Test rear camera name is set
    assertNotNull(Constants.Vision.REAR_CAMERA_NAME);
    assertFalse(Constants.Vision.REAR_CAMERA_NAME.isEmpty());

    // Test rear camera offset values are reasonable
    assertTrue(Math.abs(Constants.Vision.REAR_CAMERA_Z_OFFSET) < 2.0,
        "Rear camera height should be reasonable (< 2m)");
    assertTrue(Constants.Vision.REAR_CAMERA_X_OFFSET <= 0,
        "Rear camera should be backward or centered");

    // Test rear camera angles are reasonable
    assertTrue(Math.abs(Constants.Vision.REAR_CAMERA_PITCH_DEGREES) < 90,
        "Rear camera pitch should be reasonable");
    assertTrue(Math.abs(Constants.Vision.REAR_CAMERA_ROLL_DEGREES) < 90,
        "Rear camera roll should be reasonable");
    assertEquals(180.0, Constants.Vision.REAR_CAMERA_YAW_DEGREES,
        "Rear camera should face backward (180°)");
  }

  @Test
  void testVisionMeasurementConstants() {
    // Test standard deviations are positive
    assertTrue(Constants.Vision.VISION_MEASUREMENT_STD_DEVS[0] > 0,
        "X standard deviation should be positive");
    assertTrue(Constants.Vision.VISION_MEASUREMENT_STD_DEVS[1] > 0,
        "Y standard deviation should be positive");
    assertTrue(Constants.Vision.VISION_MEASUREMENT_STD_DEVS[2] > 0,
        "Rotation standard deviation should be positive");

    // Test distance weight is positive
    assertTrue(Constants.Vision.DISTANCE_WEIGHT > 0,
        "Distance weight should be positive");

    // Test max vision distance is reasonable
    assertTrue(Constants.Vision.MAX_VISION_DISTANCE > 0,
        "Max vision distance should be positive");
    assertTrue(Constants.Vision.MAX_VISION_DISTANCE < 20,
        "Max vision distance should be reasonable (< 20m)");

    // Test ambiguity threshold is in valid range
    assertTrue(Constants.Vision.MAX_AMBIGUITY >= 0 && Constants.Vision.MAX_AMBIGUITY <= 1,
        "Ambiguity threshold should be between 0 and 1");
  }

  @Test
  void testVisionStandardDeviationsArray() {
    // Should have exactly 3 values [x, y, rotation]
    assertEquals(3, Constants.Vision.VISION_MEASUREMENT_STD_DEVS.length,
        "Standard deviations should have 3 values");
  }
}
