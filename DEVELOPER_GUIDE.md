# Developer Guide - Team 10413 Robot Code

## Table of Contents
- [Development Environment Setup](#development-environment-setup)
- [Project Architecture](#project-architecture)
- [Coding Standards](#coding-standards)
- [Common Development Tasks](#common-development-tasks)
- [Testing and Debugging](#testing-and-debugging)
- [Git Workflow](#git-workflow)
- [Advanced Topics](#advanced-topics)

---

## Development Environment Setup

### Required Software

1. **Java Development Kit (JDK) 17**
   - Download from: https://adoptium.net/
   - Verify installation: `java -version`

2. **Visual Studio Code**
   - Download from: https://code.visualstudio.com/
   - Required extensions:
     - WPILib Extension (install via WPILib installer)
     - Java Extension Pack

3. **WPILib**
   - Download installer: https://github.com/wpilibsuite/allwpilib/releases
   - Run installer and select VS Code integration
   - Version: 2025.1.1 or later

4. **Git**
   - Download from: https://git-scm.com/
   - Configure: `git config --global user.name "Your Name"`
   - Configure: `git config --global user.email "your.email@example.com"`

5. **CTRE Phoenix Tuner X** (optional but recommended)
   - Download from: https://pro.docs.ctr-electronics.com/en/latest/docs/tuner/index.html
   - Used for hardware configuration and diagnostics

### Initial Project Setup

```bash
# Clone the repository
git clone <repository-url>
cd NewSwerveTestFlux

# Build the project
./gradlew build

# Run tests
./gradlew test

# Deploy to robot (when connected)
./gradlew deploy
```

---

## Project Architecture

### Directory Structure

```
NewSwerveTestFlux/
├── src/main/java/frc/robot/
│   ├── Robot.java              # Main robot class
│   ├── RobotContainer.java     # Subsystem/command configuration
│   ├── Constants.java          # All robot constants
│   ├── commands/               # Command classes
│   │   ├── DriveForwardAuto.java
│   │   ├── DriveToAprilTag.java
│   │   └── AutoSelector.java
│   ├── subsystems/             # Subsystem classes
│   │   ├── CommandSwerveDrivetrain.java
│   │   ├── VisionSubsystem.java
│   │   ├── SafetyMonitor.java
│   │   └── FieldConfiguration.java
│   └── generated/              # CTRE-generated code (DO NOT EDIT)
│       ├── TunerConstants.java
│       └── Telemetry.java
├── src/test/java/frc/robot/    # Unit tests
├── vendordeps/                  # Vendor library dependencies
└── build.gradle                 # Build configuration
```

### Component Relationships

```
Robot.java
  └─> RobotContainer.java
       ├─> CommandSwerveDrivetrain (subsystem)
       ├─> VisionSubsystem (subsystem)
       │    └─> FieldConfiguration (utility)
       ├─> SafetyMonitor (subsystem)
       └─> AutoSelector (command factory)
            ├─> DriveForwardAuto (command)
            └─> DriveToAprilTag (command)
```

---

## Coding Standards

### Java Style Guide

1. **Naming Conventions**
   - Classes: `PascalCase` (e.g., `VisionSubsystem`)
   - Methods: `camelCase` (e.g., `getFrontCamera()`)
   - Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_SPEED`)
   - Member variables: `m_camelCase` (e.g., `m_frontCamera`)
   - Local variables: `camelCase` (e.g., `targetDistance`)

2. **File Organization**
   - One class per file
   - Package statement first
   - Imports second (grouped: Java, WPILib, vendor, local)
   - Class declaration with Javadoc
   - Member variables
   - Constructor(s)
   - Public methods
   - Private methods

3. **Comments and Documentation**
   - All public classes must have Javadoc comments
   - All public methods must have Javadoc comments
   - Use `//` for inline comments
   - Explain *why*, not *what* (code should be self-documenting)

**Example:**
```java
/**
 * Vision subsystem for AprilTag-based robot localization
 * Uses dual PhotonVision cameras for 360-degree coverage
 */
public class VisionSubsystem extends SubsystemBase {
  private final PhotonCamera m_frontCamera;

  /**
   * Creates a new VisionSubsystem with dual cameras
   * @param drivetrain The swerve drivetrain for pose updates
   */
  public VisionSubsystem(CommandSwerveDrivetrain drivetrain) {
    // Constructor implementation
  }
}
```

### Constants Management

**CRITICAL RULE:** All magic numbers go in `Constants.java`

**Bad:**
```java
// DON'T DO THIS
if (voltage < 11.5) {
  DriverStation.reportWarning("Low battery!");
}
```

**Good:**
```java
// DO THIS
if (voltage < Constants.Safety.VOLTAGE_WARNING_THRESHOLD) {
  DriverStation.reportWarning("Low battery!");
}
```

**Constants.java Structure:**
```java
public final class Constants {
  public static final class OI {
    public static final int DRIVER_CONTROLLER_PORT = 0;
  }

  public static final class Drivetrain {
    public static final double SPEED_MULTIPLIER = 0.1;
  }

  // ... more nested classes for organization
}
```

---

## Common Development Tasks

### 1. Adding a New Subsystem

**Step-by-step:**

1. Create new file in `src/main/java/frc/robot/subsystems/`
```java
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MyNewSubsystem extends SubsystemBase {
  public MyNewSubsystem() {
    // Constructor
  }

  @Override
  public void periodic() {
    // Called every robot loop (~20ms)
  }
}
```

2. Add constants to `Constants.java`
```java
public static final class MySubsystem {
  public static final int MOTOR_ID = 10;
  public static final double SPEED = 0.5;
}
```

3. Register in `RobotContainer.java`
```java
public class RobotContainer {
  private final MyNewSubsystem m_mySubsystem = new MyNewSubsystem();

  public RobotContainer() {
    // Configure bindings
  }
}
```

### 2. Adding a New Command

**Step-by-step:**

1. Create new file in `src/main/java/frc/robot/commands/`
```java
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MyNewSubsystem;

public class MyNewCommand extends Command {
  private final MyNewSubsystem m_subsystem;

  public MyNewCommand(MyNewSubsystem subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    // Called once when command starts
  }

  @Override
  public void execute() {
    // Called repeatedly while command is scheduled
  }

  @Override
  public void end(boolean interrupted) {
    // Called once when command ends
  }

  @Override
  public boolean isFinished() {
    // Return true when command should end
    return false;
  }
}
```

2. Bind to controller in `RobotContainer.java`
```java
private void configureBindings() {
  m_driverController.a().onTrue(new MyNewCommand(m_mySubsystem));
}
```

### 3. Modifying Hardware Configuration

**IMPORTANT:** Use CTRE Tuner X for swerve module configuration

**For non-swerve hardware:**

1. Add constants to `Constants.java`
2. Update subsystem constructor
3. Test in simulation first
4. Deploy and test on robot

**For swerve modules:**

1. Open CTRE Tuner X
2. Connect to robot
3. Modify configuration in Tuner X
4. Export to `generated/TunerConstants.java`
5. **DO NOT manually edit TunerConstants.java**

### 4. Adding Autonomous Routines

**Option A: Simple Command-Based Auto**

```java
// In AutoSelector.java
m_autoChooser.addOption("My New Auto",
  new DriveForwardAuto(m_drivetrain, 2.0, 3.0));
```

**Option B: Complex Sequential Auto**

```java
public class ComplexAuto extends SequentialCommandGroup {
  public ComplexAuto(CommandSwerveDrivetrain drivetrain, VisionSubsystem vision) {
    addCommands(
      new DriveForwardAuto(drivetrain, 1.0, 2.0),
      new DriveToAprilTag(drivetrain, vision, 1, 20),
      new WaitCommand(1.0),
      new DriveForwardAuto(drivetrain, -1.0, 1.0)
    );
  }
}
```

### 5. Working with Vision

**Check camera status:**
```java
if (visionSubsystem.isFrontCameraConnected()) {
  // Front camera is working
}
```

**Get AprilTag data:**
```java
Optional<PhotonTrackedTarget> target = visionSubsystem.getFrontTargetById(1);
if (target.isPresent()) {
  double yaw = target.get().getYaw();
  double distance = target.get().getBestCameraToTarget().getTranslation().getNorm();
}
```

**Switch field modes for testing:**
```java
// In Constants.java
public static final FieldConfiguration.FieldMode FIELD_MODE =
  FieldConfiguration.FieldMode.TRAINING_FIELD_1_TAG;
```

---

## Testing and Debugging

### Unit Tests

**Location:** `src/test/java/frc/robot/`

**Run tests:**
```bash
./gradlew test
```

**Example test:**
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MySubsystemTest {
  @Test
  void testInitialization() {
    MySubsystem subsystem = new MySubsystem();
    assertNotNull(subsystem);
  }
}
```

### Simulation

**Run robot simulation:**
```bash
./gradlew simulateJava
```

**Simulation GUI:**
- Shows robot pose on field
- Displays NetworkTables values
- Simulates joystick input
- Test autonomous routines safely

### Logging and Telemetry

**DataLogManager** (automatically enabled):
```java
DataLogManager.log("Important event happened");
```

**SmartDashboard:**
```java
SmartDashboard.putNumber("My Value", 123.45);
SmartDashboard.putBoolean("My Status", true);
SmartDashboard.putString("My State", "Active");
```

**View logs:**
- Download: `./gradlew downloadLogs`
- Analyze: Use AdvantageScope or WPILib Log Viewer

### Common Debugging Techniques

1. **Check Driver Station console**
   - Look for error messages
   - Check for warnings

2. **Use SmartDashboard for live data**
   - Add temporary debug values
   - Monitor subsystem states

3. **Check CAN bus health**
   - Use Phoenix Tuner X
   - Monitor "Safety/CAN Utilization"

4. **Enable verbose logging**
   ```java
   DataLogManager.log("DEBUG: Variable value = " + value);
   ```

---

## Git Workflow

### Branch Strategy

- `main` - Production-ready code
- `develop` - Integration branch (if used)
- `feature/feature-name` - Feature branches
- `bugfix/bug-name` - Bug fix branches

### Typical Workflow

```bash
# Start new feature
git checkout -b feature/new-autonomous-mode

# Make changes and commit
git add .
git commit -m "Add new autonomous mode for scoring"

# Push to remote
git push -u origin feature/new-autonomous-mode

# Create pull request on GitHub
# After review and approval, merge to main
```

### Commit Message Format

```
Short summary (50 chars or less)

Longer explanation if needed. Explain WHY the change
was made, not WHAT was changed (code shows that).

- Bullet points are okay
- Typically a hyphen or asterisk is used

Fixes #123
```

**Good commit messages:**
- "Add vision-based AprilTag alignment command"
- "Fix battery voltage warning threshold"
- "Refactor Constants.java for better organization"

**Bad commit messages:**
- "Fixed stuff"
- "Update"
- "asdf"

---

## Advanced Topics

### Custom PID Tuning

**For swerve drive:** Use Phoenix Tuner X

**For custom controllers:**
```java
PIDController controller = new PIDController(
  Constants.MySubsystem.KP,
  Constants.MySubsystem.KI,
  Constants.MySubsystem.KD
);

// In execute()
double output = controller.calculate(measurement, setpoint);
```

**Tuning process:**
1. Start with kP only (kI=0, kD=0)
2. Increase kP until oscillation
3. Add kD to reduce oscillation
4. Add kI only if steady-state error exists

### Custom Telemetry

**Create custom Field2d widget:**
```java
private final Field2d m_field = new Field2d();

public MySubsystem() {
  SmartDashboard.putData("My Field", m_field);
}

@Override
public void periodic() {
  m_field.setRobotPose(currentPose);
}
```

### PathPlanner Integration (Future)

**To add PathPlanner for complex autos:**

1. Add vendordep:
```bash
./gradlew vendordep --url=https://3015rangerrobotics.github.io/pathplannerlib/PathplannerLib.json
```

2. Create paths in PathPlanner GUI
3. Load in auto command:
```java
PathPlannerPath path = PathPlannerPath.fromPathFile("MyPath");
Command followPath = AutoBuilder.followPath(path);
```

### Custom Dashboard

**AdvantageScope recommended for detailed analysis**
- Download: https://github.com/Mechanical-Advantage/AdvantageScope
- Supports 3D field visualization
- Timeline analysis of logged data
- Excellent for post-match review

---

## Quick Reference

### Build Commands
```bash
./gradlew build           # Build project
./gradlew deploy          # Deploy to robot
./gradlew simulateJava    # Run simulation
./gradlew test            # Run unit tests
./gradlew clean           # Clean build files
```

### Important Files to NEVER Manually Edit
- `generated/TunerConstants.java` - Use Tuner X instead
- `build/` - Auto-generated build artifacts
- `.gradle/` - Gradle cache

### Important Files to Always Edit
- `Constants.java` - All constants
- `RobotContainer.java` - Subsystem wiring
- Individual subsystem/command files

### Key Documentation Links
- WPILib: https://docs.wpilib.org/
- CTRE Phoenix 6: https://pro.docs.ctr-electronics.com/
- PhotonVision: https://docs.photonvision.org/
- Command-Based: https://docs.wpilib.org/en/stable/docs/software/commandbased/

---

## Getting Help

1. **Check CLAUDE.md** - Project-specific guidance
2. **Check this guide** - Developer workflows
3. **Ask senior team members** - They know the robot best
4. **WPILib documentation** - Official reference
5. **Chief Delphi forums** - FRC community

**Remember:** Ask questions early and often. It's better to ask than to break something!
