# Team 10413 Onboarding Guide

Welcome to Team 10413! This guide will help you get started with robot programming, regardless of your experience level.

## Table of Contents
- [Welcome](#welcome)
- [Week 1: Getting Started](#week-1-getting-started)
- [Week 2: Learning the Basics](#week-2-learning-the-basics)
- [Week 3: Understanding Our Robot](#week-3-understanding-our-robot)
- [Week 4: Contributing Code](#week-4-contributing-code)
- [Ongoing Learning](#ongoing-learning)
- [Resources](#resources)

---

## Welcome

### What You'll Learn
- Java programming basics
- FRC robot programming using WPILib
- Command-based programming pattern
- Git version control
- Team collaboration

### Prerequisites
- A computer (Windows, Mac, or Linux)
- Willingness to learn
- Patience (programming takes practice!)

### Team Culture
- **Ask questions** - There are no dumb questions
- **Make mistakes** - That's how we learn
- **Help others** - Teaching reinforces learning
- **Have fun** - Robotics should be enjoyable!

---

## Week 1: Getting Started

### Day 1-2: Install Software

Follow these steps in order:

**1. Install Java Development Kit (JDK) 17**
   - Download: https://adoptium.net/
   - Choose "Temurin 17 (LTS)"
   - Download the installer for your operating system
   - Run installer with default settings
   - Verify: Open terminal/command prompt and type: `java -version`
   - You should see "openjdk version 17" or similar

**2. Install Git**
   - Download: https://git-scm.com/downloads
   - Run installer with default settings
   - Verify: `git --version`

**3. Install WPILib**
   - Download: https://github.com/wpilibsuite/allwpilib/releases
   - Get the latest 2025 release (look for "WPILib_[OS]-2025.x.x.iso/tar.gz")
   - Run the installer
   - **Important:** Check "Download VS Code for Single Install"
   - Installation takes 10-15 minutes
   - When complete, you'll have "WPILib VS Code 2025" installed

**4. Clone the Repository**
   ```bash
   # Open terminal/command prompt
   # Navigate to where you want the code
   cd Documents  # or wherever you prefer

   # Clone the repository (get URL from team lead)
   git clone <repository-url>
   cd NewSwerveTestFlux
   ```

**5. Open Project in VS Code**
   - Open "WPILib VS Code 2025" (not regular VS Code!)
   - File → Open Folder → Select `NewSwerveTestFlux` folder
   - Wait for Java extension to load (bottom right corner)
   - Press Ctrl+Shift+P (Cmd+Shift+P on Mac)
   - Type "WPILib: Build Robot Code"
   - If build succeeds, you're ready!

### Day 3-4: Learn Java Basics

If you're new to Java, complete these tutorials:

**1. Java Syntax Basics**
   - Variables and data types
   - If statements and loops
   - Methods (functions)
   - Classes and objects

**Recommended:** https://www.codecademy.com/learn/learn-java (first 3 sections)

**Quick Java Cheat Sheet:**
```java
// Variables
int number = 42;
double decimal = 3.14;
boolean isTrue = true;
String text = "Hello";

// If statement
if (number > 40) {
  System.out.println("Greater than 40");
}

// For loop
for (int i = 0; i < 5; i++) {
  System.out.println(i);
}

// Method
public void myMethod(int parameter) {
  // Do something
}

// Class
public class MyClass {
  private int myVariable;

  public MyClass() {
    // Constructor
  }

  public void myMethod() {
    // Method
  }
}
```

### Day 5-7: Explore the Codebase

**Task: Read and understand these files in order:**

1. **Constants.java** - Look at how constants are organized
   - Find: `DRIVER_CONTROLLER_PORT`
   - Find: `SPEED_MULTIPLIER`
   - Find: `FRONT_CAMERA_NAME`

2. **Robot.java** - The main robot class
   - Find: `robotInit()` method
   - Find: `autonomousInit()` method
   - Understanding: This runs when robot starts

3. **RobotContainer.java** - Where everything connects
   - Find: Where subsystems are created
   - Find: Where controller buttons are mapped
   - Find: Where autonomous modes are configured

**Exercise:**
Write down answers to these questions:
- What is the driver controller port number?
- How many cameras does the robot have?
- What button makes the robot drive to an AprilTag?

---

## Week 2: Learning the Basics

### Command-Based Programming Concepts

**The Robot Programming Pattern:**

```
Robot
  └─ Has Subsystems (physical parts of robot)
       └─ Run Commands (actions the robot takes)
```

**Example:**
- **Subsystem:** Drivetrain (the wheels and motors)
- **Command:** DriveForward (makes the drivetrain move forward)

### Subsystems

**What is a subsystem?**
A subsystem represents a physical part of the robot.

**Our subsystems:**
- `CommandSwerveDrivetrain` - The drivetrain (wheels/motors)
- `VisionSubsystem` - Cameras for AprilTag detection
- `SafetyMonitor` - Monitors battery and safety

**Subsystem structure:**
```java
public class MySubsystem extends SubsystemBase {
  // Member variables (hardware devices)
  private final Motor m_motor = new Motor(1);

  // Constructor
  public MySubsystem() {
    // Initialize hardware
  }

  // Called every 20ms
  @Override
  public void periodic() {
    // Update telemetry, check status, etc.
  }

  // Public methods to control the subsystem
  public void doSomething() {
    m_motor.set(0.5);
  }
}
```

### Commands

**What is a command?**
A command is an action that uses one or more subsystems.

**Our commands:**
- `DriveForwardAuto` - Drives forward for a duration
- `DriveToAprilTag` - Aligns to an AprilTag using vision

**Command structure:**
```java
public class MyCommand extends Command {
  private final MySubsystem m_subsystem;

  public MyCommand(MySubsystem subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem); // Prevents conflicts
  }

  @Override
  public void initialize() {
    // Called once when command starts
  }

  @Override
  public void execute() {
    // Called repeatedly (every 20ms) while running
    m_subsystem.doSomething();
  }

  @Override
  public void end(boolean interrupted) {
    // Called once when command ends
    m_subsystem.stop();
  }

  @Override
  public boolean isFinished() {
    // Return true to end the command
    return false; // This command never ends on its own
  }
}
```

### Hands-On Exercise: Read Real Commands

**1. Open DriveForwardAuto.java**
   - Find: `initialize()` method
   - Question: What does it do when the command starts?
   - Find: `execute()` method
   - Question: What happens every loop?
   - Find: `isFinished()` method
   - Question: When does this command end?

**2. Open VisionSubsystem.java**
   - Find: `periodic()` method
   - Question: What cameras does it check?
   - Find: `getFrontCameraResult()` method
   - Question: What does this return?

---

## Week 3: Understanding Our Robot

### Hardware Overview

**Drivetrain:**
- **Type:** Swerve drive (4 wheels, each can rotate and drive independently)
- **Motors:** 8 Kraken x60 motors (4 drive, 4 steer)
- **Encoders:** 4 CANcoders (absolute position sensors)
- **IMU:** Pigeon 2 (tells us robot orientation)

**Vision System:**
- **Front camera:** Sees forward, mounted 0.3m ahead of center
- **Rear camera:** Sees backward, mounted 0.3m behind center
- **Purpose:** Detects AprilTags for localization

**Controller:**
- **Type:** Xbox controller on port 0
- **Controls:** See COMPETITION_GUIDE.md for button mappings

### Software Architecture

**How the code flows:**

1. **Robot starts** → `Robot.java` runs `robotInit()`
2. **RobotContainer constructor** runs → Creates all subsystems
3. **Every 20ms** → `periodic()` methods run on all subsystems
4. **Driver presses button** → Command is scheduled
5. **Command runs** → Uses subsystems to do work
6. **Command ends** → Subsystems return to default state

**Important files:**
- `Constants.java` - ALL constant values (never hardcode!)
- `RobotContainer.java` - Configuration hub
- `subsystems/` folder - Physical robot parts
- `commands/` folder - Robot actions
- `generated/` folder - **NEVER EDIT** (auto-generated)

### Field Coordinate System

**Understanding positions:**
- **X axis:** Along field length (0 = blue alliance wall)
- **Y axis:** Along field width (0 = right side from blue perspective)
- **Rotation:** 0° = facing away from blue alliance wall

**Example positions:**
- (0, 0) = Blue alliance right corner
- (8.21, 4.1) = Center of field
- (16.54, 8.21) = Red alliance left corner

**AprilTags:**
- Tags 1-8: Blue alliance side
- Tags 9-16: Red alliance side
- Used for robot localization (knowing where we are)

### Training Field Modes

We have 4 field configurations (switch in `Constants.java`):

1. **REAL_FIELD** - Full competition field (16 tags)
2. **TRAINING_FIELD_1_TAG** - One tag at center (easiest)
3. **TRAINING_FIELD_2_TAGS** - Two tags (medium)
4. **TRAINING_FIELD_3_TAGS** - Three tags (advanced)

**To switch modes:**
```java
// In Constants.java, line ~93
public static final FieldConfiguration.FieldMode FIELD_MODE =
  FieldConfiguration.FieldMode.TRAINING_FIELD_1_TAG; // Change this
```

---

## Week 4: Contributing Code

### Your First Contribution

**Project: Add a Telemetry Value**

1. **Choose a subsystem** (let's use SafetyMonitor)

2. **Find the periodic() method**
   ```java
   @Override
   public void periodic() {
     // Existing code...
   }
   ```

3. **Add a SmartDashboard value**
   ```java
   @Override
   public void periodic() {
     // Existing code...

     // YOUR NEW CODE:
     SmartDashboard.putString("Safety/Status", "All systems nominal");
   }
   ```

4. **Build and test**
   ```bash
   ./gradlew build
   ./gradlew simulateJava
   ```

5. **Check SmartDashboard**
   - Open Shuffleboard (comes with WPILib)
   - Look for "Safety/Status"
   - You should see your message!

6. **Commit your change**
   ```bash
   git add .
   git commit -m "Add safety status telemetry"
   git push
   ```

### Your Second Contribution

**Project: Add a Constant**

1. **Open Constants.java**

2. **Find the appropriate section** (e.g., `Vision` class)

3. **Add a new constant**
   ```java
   public static final class Vision {
     // Existing constants...

     // YOUR NEW CONSTANT:
     public static final boolean ENABLE_DEBUG_LOGGING = false;
   }
   ```

4. **Use it somewhere**
   ```java
   // In VisionSubsystem.java
   if (Constants.Vision.ENABLE_DEBUG_LOGGING) {
     DataLogManager.log("Front camera detected " + targetCount + " targets");
   }
   ```

5. **Test, commit, push**

### Git Workflow for Contributions

**Always use branches for your work:**

```bash
# Make sure you're on main and up to date
git checkout main
git pull

# Create a new branch for your feature
git checkout -b feature/my-new-feature

# Make your changes...
# (edit files in VS Code)

# Check what changed
git status
git diff

# Stage your changes
git add .

# Commit with a good message
git commit -m "Add debug logging option to vision subsystem"

# Push to remote
git push -u origin feature/my-new-feature

# Create a Pull Request on GitHub
# Ask a senior member to review
```

### Code Review Process

**When you submit a Pull Request:**

1. **Describe what you changed** and why
2. **Wait for review** from senior members
3. **Address feedback** if requested
4. **Get approval** from at least one reviewer
5. **Merge** to main (usually done by senior member)

**Being reviewed is normal and helpful!** Everyone's code gets reviewed.

---

## Ongoing Learning

### Weekly Learning Goals

**Week 5+: Pick one area to focus on each week**

- **Week 5:** Understand the swerve drivetrain code
- **Week 6:** Learn about PID controllers
- **Week 7:** Understand vision processing
- **Week 8:** Study autonomous routines
- **Week 9:** Learn about state machines
- **Week 10:** Explore advanced commands

### Practice Projects

**Beginner Projects:**
1. Add a new telemetry value to SmartDashboard
2. Change a constant and observe the effect
3. Add a comment explaining a complex method
4. Write a simple unit test

**Intermediate Projects:**
1. Create a new simple command (e.g., "DriveDistance")
2. Add a new controller button binding
3. Modify an existing autonomous routine
4. Add error checking to a subsystem

**Advanced Projects:**
1. Create a new subsystem (with mentor guidance)
2. Implement a complex autonomous sequence
3. Add a new vision feature
4. Optimize a PID controller

### Skills Development Path

```
Level 1: Reader
  ↓ Can read and understand existing code
Level 2: Modifier
  ↓ Can modify existing code safely
Level 3: Contributor
  ↓ Can write new code with guidance
Level 4: Developer
  ↓ Can independently develop features
Level 5: Architect
  ↓ Can design system architecture
```

**You'll start at Level 1, and that's perfect!**

---

## Resources

### Official Documentation

**Must-read:**
- WPILib Docs: https://docs.wpilib.org/
  - Start with "Zero to Robot"
  - Read "Command-Based Programming"

**Hardware-specific:**
- CTRE Phoenix 6: https://pro.docs.ctr-electronics.com/
- PhotonVision: https://docs.photonvision.org/
- REV Robotics: https://docs.revrobotics.com/

### Learning Platforms

**Java:**
- Codecademy: https://www.codecademy.com/learn/learn-java
- Oracle Java Tutorials: https://docs.oracle.com/javase/tutorial/

**FRC-Specific:**
- FRC Programming Done Right: https://frc-pdr.readthedocs.io/
- Team 3847 Programming Guide: https://github.com/Spectrum3847/SpectrumCommon

**Video Tutorials:**
- FRC 0 to Autonomous (YouTube series)
- WPILib Official YouTube Channel

### Community Resources

**Forums:**
- Chief Delphi: https://www.chiefdelphi.com/
  - Search before asking
  - Very helpful community
  - Lots of example code

**Discord:**
- FRC Discord: https://discord.gg/frc
  - #programming channel
  - Real-time help

### Team Resources

**Team-specific docs:**
- `CLAUDE.md` - Quick reference for AI assistant
- `DEVELOPER_GUIDE.md` - Detailed technical guide
- `COMPETITION_GUIDE.md` - Competition day reference

**Team contacts:**
- Programming Lead: [Name/Contact]
- Programming Mentor: [Name/Contact]
- Team Slack/Discord: [Link]

### Practice Resources

**GitHub Repositories with Example Code:**
- Team 254: https://github.com/Team254
- Team 1678: https://github.com/frc1678
- Team 5712: https://github.com/FRC5712

**Note:** Other teams may use different patterns. Learn from them, but follow our team's style.

---

## Mentorship and Support

### Getting Help

**Order of escalation:**
1. **Search existing documentation** (CLAUDE.md, DEVELOPER_GUIDE.md)
2. **Search WPILib docs** (often has the answer)
3. **Ask a peer** (teammate with more experience)
4. **Ask programming lead** (in person or via Slack)
5. **Ask mentor** (for complex issues)
6. **Post on Chief Delphi** (for really tricky problems)

### Meeting Schedule

**Regular programming meetings:**
- Day/Time: [Fill in team schedule]
- Location: [Fill in location]
- What to bring: Laptop, questions, enthusiasm

**Online communication:**
- Slack/Discord: [Link]
- Response time: Usually within 24 hours
- Emergency contact: [Phone/Email]

### Pairing and Mentorship

**Buddy System:**
When you join, you'll be paired with an experienced member who will:
- Answer your questions
- Review your code
- Help you get unstuck
- Teach you best practices

**Don't be afraid to ask your buddy anything!**

---

## First Month Checklist

Use this to track your progress:

**Week 1:**
- [ ] Software installed and working
- [ ] Repository cloned
- [ ] Code builds successfully
- [ ] Java basics understood
- [ ] Read Constants.java, Robot.java, RobotContainer.java

**Week 2:**
- [ ] Understand subsystems vs commands
- [ ] Read through DriveForwardAuto.java
- [ ] Read through VisionSubsystem.java
- [ ] Understand command lifecycle (init, execute, end, isFinished)

**Week 3:**
- [ ] Understand robot hardware layout
- [ ] Understand field coordinate system
- [ ] Know what each subsystem does
- [ ] Know all controller mappings

**Week 4:**
- [ ] Made first contribution (telemetry or constant)
- [ ] Successfully built and tested code
- [ ] Created a branch and pull request
- [ ] Got code reviewed and merged

**Ongoing:**
- [ ] Attend all programming meetings
- [ ] Complete one practice project
- [ ] Help another new member
- [ ] Ask at least one question per meeting (seriously!)

---

## Welcome Message from the Team

Welcome to Team 10413 programming! You're joining a team of passionate, creative problem-solvers who build amazing robots together.

**Remember:**
- Everyone started where you are now
- Mistakes are learning opportunities
- Questions are encouraged
- Your contributions matter
- Have fun!

**We're excited to work with you. Let's build something awesome!**

---

## Quick Reference

### Important Commands
```bash
./gradlew build          # Build the code
./gradlew deploy         # Deploy to robot
./gradlew simulateJava   # Run simulation
./gradlew test           # Run tests
git status               # Check what changed
git add .                # Stage changes
git commit -m "message"  # Commit changes
git push                 # Push to remote
```

### Important Keyboard Shortcuts (VS Code)
- `Ctrl+Shift+P` - Command palette (access WPILib commands)
- `Ctrl+S` - Save file
- `Ctrl+/` - Comment/uncomment line
- `Ctrl+F` - Find in file
- `Ctrl+Shift+F` - Find in all files
- `F5` - Start debugging

### Need Help Right Now?

1. Check `DEVELOPER_GUIDE.md` for technical details
2. Check `CLAUDE.md` for quick reference
3. Message programming lead on Slack
4. Ask in team Discord/Slack channel

**You've got this! Welcome to the team!**
