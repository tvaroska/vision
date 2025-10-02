# FRC Team 10413 - 2025 Robot Code

Competition robot code for the 2025 FIRST Robotics Competition season (Reefscape), featuring swerve drive with dual-camera vision system.

## Features

- **Swerve Drivetrain** - X1 12T modules with CTRE Kraken x60 motors
- **Dual Camera Vision** - 360° AprilTag detection using PhotonVision
- **Safety Monitoring** - Real-time voltage, current, and CAN bus tracking
- **Autonomous Modes** - Multiple auto routines with dashboard selection
- **Vision-Based Alignment** - Automatic driving to AprilTags
- **Comprehensive Logging** - DataLogManager for match analysis

## Hardware

### Drivetrain
- **Motors:** CTRE Kraken x60 (TalonFX)
- **Encoders:** CTRE CANcoders
- **IMU:** Pigeon 2 (CAN ID 20)
- **Max Speed:** 4.99 m/s
- **Track Width:** 23" x 23"

### Vision System
- **Front Camera:** PhotonVision (forward-facing)
- **Rear Camera:** PhotonVision (backward-facing)
- **Field Layout:** 2025 Reefscape (16 AprilTags)

## Build Commands

```bash
./gradlew build          # Build the project
./gradlew deploy         # Deploy to RoboRIO
./gradlew simulateJava   # Run robot simulation
./gradlew test           # Run unit tests
```

## Controls (Xbox Controller)

### Driving
- **Left Stick:** Translation (forward/backward, left/right)
- **Right Stick X:** Rotation
- **Left Bumper:** Reset field-centric heading
- **A Button:** Brake mode (X-pattern)
- **X Button (hold):** Drive to AprilTag 1

### SysId Characterization
- **Back + Y:** Dynamic forward
- **Back + X:** Dynamic reverse

## Project Structure

```
src/main/java/frc/robot/
├── Robot.java              # Main robot class
├── RobotContainer.java     # Subsystems and bindings
├── Constants.java          # All robot constants
├── subsystems/
│   ├── CommandSwerveDrivetrain.java
│   ├── VisionSubsystem.java
│   ├── SafetyMonitor.java
│   └── FieldConfiguration.java
├── commands/
│   ├── DriveForwardAuto.java
│   ├── DriveToAprilTag.java
│   └── AutoSelector.java
└── generated/
    ├── TunerConstants.java  # Auto-generated (DO NOT edit)
    └── Telemetry.java
```

## Configuration

All robot constants are centralized in `Constants.java`:

- **OI:** Controller mappings and driver preferences
- **Drivetrain:** Speed limits and control parameters
- **Safety:** Voltage/current thresholds
- **Auto:** Autonomous parameters
- **Vision:** Camera positions, trust levels, field modes

## Vision Setup

### Field Modes
Switch between layouts in `Constants.Vision.FIELD_MODE`:
- `REAL_FIELD` - Competition field (16 tags)
- `TRAINING_FIELD_1_TAG` - Single tag for testing
- `TRAINING_FIELD_2_TAGS` - Two tags for alignment
- `TRAINING_FIELD_3_TAGS` - Three tags for full pose estimation

### Camera Configuration
1. Install PhotonVision on coprocessors
2. Name cameras: `front_camera` and `rear_camera`
3. Calibrate both cameras using PhotonVision tools
4. Update camera positions in `Constants.Vision`
5. Monitor telemetry under `Vision/Front/` and `Vision/Rear/`

## Autonomous

Select autonomous mode using SmartDashboard's "Auto Selector":
- **Do Nothing** - No autonomous action
- **Drive Forward (Short)** - 1 second forward
- **Drive Forward** - 2 seconds forward (default)
- **Drive Forward (Long)** - 3 seconds forward

## Safety Monitoring

The `SafetyMonitor` subsystem tracks:
- Battery voltage (warnings < 11.5V)
- Current draw (warnings > 100A)
- CAN bus utilization (warnings > 70%)
- Peak current and minimum voltage

View metrics on SmartDashboard under `Safety/`.

## Development

### Adding New Constants
Always add constants to `Constants.java`, organized by category.

### Modifying Swerve Configuration
Use CTRE Tuner X to modify settings, then regenerate `TunerConstants.java`. Never manually edit generated files.

### Testing
Unit tests are in `src/test/java/frc/robot/`. Run with:
```bash
./gradlew test
```

### Logging
Logs are automatically saved to the RoboRIO. Download post-match for analysis using AdvantageScope or WPILib DataLog Tool.

## Dependencies

- WPILib 2025.1.1
- CTRE Phoenix 6 (2025.1.0)
- REVLib (2025.0.0)
- PhotonLib (2025.0.0)

See `vendordeps/` for full vendor dependency list.

## Team

**Team 10413**
2025 FIRST Robotics Competition - Reefscape

---

For detailed development guidance, see `CLAUDE.md`.
