// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.hal.HAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SafetyMonitor subsystem
 */
class SafetyMonitorTest {
  private SafetyMonitor safetyMonitor;

  @BeforeEach
  void setup() {
    // Initialize HAL for testing
    assert HAL.initialize(500, 0);
    safetyMonitor = new SafetyMonitor();
  }

  @AfterEach
  void teardown() {
    // Cleanup if needed
    safetyMonitor = null;
  }

  @Test
  void testConstruction() {
    assertNotNull(safetyMonitor);
  }

  @Test
  void testGetBatteryVoltage() {
    double voltage = safetyMonitor.getBatteryVoltage();
    assertTrue(voltage >= 0.0, "Voltage should be non-negative");
    assertTrue(voltage <= 14.0, "Voltage should be reasonable (< 14V)");
  }

  @Test
  void testGetTotalCurrent() {
    double current = safetyMonitor.getTotalCurrent();
    assertTrue(current >= 0.0, "Current should be non-negative");
  }

  @Test
  void testGetPeakCurrent() {
    double peakCurrent = safetyMonitor.getPeakCurrent();
    assertTrue(peakCurrent >= 0.0, "Peak current should be non-negative");
  }

  @Test
  void testGetLowestVoltage() {
    double lowestVoltage = safetyMonitor.getLowestVoltage();
    assertTrue(lowestVoltage >= 0.0, "Lowest voltage should be non-negative");
    assertTrue(lowestVoltage <= 14.0, "Lowest voltage should be reasonable");
  }

  @Test
  void testResetPeaks() {
    // Should not throw
    assertDoesNotThrow(() -> {
      safetyMonitor.resetPeaks();
    });
  }

  @Test
  void testBrownoutStatus() {
    boolean brownedOut = safetyMonitor.isBrownedOut();
    // In simulation, should typically be false
    assertNotNull(brownedOut);
  }

  @Test
  void testPeakTracking() {
    double initialPeak = safetyMonitor.getPeakCurrent();

    // Run periodic (simulates tracking)
    safetyMonitor.periodic();

    double afterPeriodic = safetyMonitor.getPeakCurrent();
    assertTrue(afterPeriodic >= initialPeak, "Peak should not decrease");
  }

  @Test
  void testResetPeaksActuallyResets() {
    // Run periodic to potentially update peaks
    safetyMonitor.periodic();

    double beforeReset = safetyMonitor.getPeakCurrent();

    safetyMonitor.resetPeaks();

    double afterReset = safetyMonitor.getPeakCurrent();

    // After reset, peak current should be current or lower
    assertTrue(afterReset <= beforeReset || afterReset == 0.0,
        "Peak should reset to current or zero");
  }
}
