# Competition Quick Reference Guide - Team 10413

**CRITICAL:** Print this document and keep it in the pit area during competitions!

---

## Pre-Match Checklist (5 Minutes Before Match)

### Hardware Check
- [ ] **Battery** - Fully charged (12.5V+ before match)
- [ ] **Radio** - Powered and blinking green
- [ ] **RoboRIO** - Status light solid or blinking
- [ ] **Motor Controllers** - All LEDs on
- [ ] **Cameras** - Both front and rear connected (check PhotonVision)
- [ ] **Pneumatics** - Air tank charged (if applicable)
- [ ] **Bumpers** - Correct color installed and secured
- [ ] **Main breaker** - OFF until on field

### Software Check
- [ ] **Driver Station** - Connected to robot (green light)
- [ ] **Code deployed** - Latest version running
- [ ] **Autonomous** - Correct mode selected in SmartDashboard
- [ ] **Field Mode** - Set to `REAL_FIELD` in Constants.java
- [ ] **Controllers** - Both paired and tested
- [ ] **Camera streams** - Both visible in dashboard

### Driver Station Setup
- [ ] **Laptop** - Fully charged or plugged in
- [ ] **Robot connected** - Green "Communications" light
- [ ] **Joysticks** - Both controllers detected (0 and 1)
- [ ] **Team number** - Correct (10413)
- [ ] **Dashboard** - Shuffleboard or SmartDashboard running

---

## Critical Constants Quick Reference

### Current Settings (Check Constants.java before each competition)

**Drivetrain:**
- Speed Multiplier: `0.1` (10% speed) - **INCREASE for competition!**
- Max Angular Rate: `0.75` (75% rotation)
- Deadband: `0.1` (10%)

**Vision:**
- Field Mode: `REAL_FIELD` ← **MUST BE THIS FOR COMPETITION**
- Max Vision Distance: `4.0` meters
- Max Ambiguity: `0.3`

**Safety:**
- Voltage Warning: `11.5V`
- Voltage Critical: `10.5V`
- Current Warning: `200A`
- Current Critical: `250A`

---

## Controller Mappings (Xbox Controller Port 0)

### Normal Driving
| Control | Action |
|---------|--------|
| Left Stick Y | Forward/Backward |
| Left Stick X | Strafe Left/Right |
| Right Stick X | Rotate |
| Left Bumper | Reset Field-Centric Heading |
| A Button | Brake Mode (X-wheels) |
| B Button | Point Wheels to Stick |
| X Button (hold) | Drive to AprilTag 1 |

### SysId Testing (DO NOT USE IN MATCH)
| Control | Action |
|---------|--------|
| Back + Y | SysId Forward |
| Back + X | SysId Reverse |

---

## Autonomous Modes

### Available Modes (Select in SmartDashboard)

1. **Do Nothing**
   - Robot stays still
   - Use if autonomous isn't working

2. **Drive Forward (Default)**
   - Drives forward at 1.0 m/s for 2.0 seconds
   - Safe fallback option

3. **Drive Forward (Short)**
   - Drives forward at 0.5 m/s for 1.0 seconds
   - Minimal movement

4. **Drive Forward (Long)**
   - Drives forward at 1.5 m/s for 3.0 seconds
   - Maximum distance

### Autonomous Selection
**CRITICAL: Check SmartDashboard "Auto Selector" before EVERY match!**

---

## SmartDashboard Monitor Values

### During Match - Watch These Values

**Safety (Critical):**
- `Safety/Battery Voltage` - Should be >11.5V (>10.5V critical)
- `Safety/Total Current` - Should be <200A (<250A critical)
- `Safety/CAN Utilization` - Should be <90%

**Drivetrain:**
- `Drivetrain/Pose X` - Robot X position on field
- `Drivetrain/Pose Y` - Robot Y position on field
- `Drivetrain/Heading` - Robot rotation (degrees)

**Vision (If using AprilTags):**
- `Vision/Front Initialized` - Should be TRUE
- `Vision/Rear Initialized` - Should be TRUE
- `Vision/Front/Connected` - Should be TRUE
- `Vision/Rear/Connected` - Should be TRUE
- `Vision/Front/Has Targets` - TRUE when seeing tags
- `Vision/Front/Target Count` - Number of visible tags

---

## Common Issues and Quick Fixes

### Issue: Robot Won't Move

**Symptoms:** Driver Station connected, but robot doesn't respond to joystick

**Quick Checks:**
1. Is robot enabled? (Check Driver Station)
2. Is E-Stop pressed? (Release it)
3. Is main breaker ON?
4. Are joysticks detected? (Check Driver Station USB tab)
5. Is code running? (Check RoboRIO status light)

**Fix:**
- Disable/Enable robot
- Restart robot code (restart RoboRIO if needed)
- Re-pair controllers

---

### Issue: Vision Not Working

**Symptoms:** `Vision/Front/Connected` shows FALSE

**Quick Checks:**
1. Is camera powered? (USB connected)
2. Is PhotonVision running? (Navigate to camera IP)
3. Is field mode set to `REAL_FIELD`?
4. Is lighting adequate? (AprilTags need good lighting)

**Fix:**
- Power cycle camera (unplug/replug USB)
- Restart PhotonVision
- Check camera name matches Constants (`front_camera`, `rear_camera`)

**Workaround:**
- Robot can operate without vision (just no pose estimation)
- Manual driving still works

---

### Issue: Low Battery Voltage Warning

**Symptoms:** `Safety/Battery Voltage` < 11.5V during match

**During Match:**
- **CONTINUE DRIVING** - Warning is just a heads-up
- Avoid high-current actions if possible
- Drive more conservatively

**After Match:**
- Swap battery immediately
- Mark battery as "needs charging"

**Critical (<10.5V):**
- Robot may brownout (lose power momentarily)
- Return to pit ASAP after match

---

### Issue: High Current Draw

**Symptoms:** `Safety/Total Current` > 200A

**Causes:**
- Pushing against wall/obstacle
- Motor stall
- Mechanical binding

**Fix:**
- Release joysticks (let robot rest)
- Check for mechanical obstructions
- Avoid pushing matches

---

### Issue: Autonomous Not Running

**Symptoms:** Robot sits still during autonomous period

**Quick Checks:**
1. Is autonomous mode selected in SmartDashboard?
2. Is "Do Nothing" selected? (Change it!)
3. Is robot actually enabled?

**Fix:**
- Re-select autonomous mode
- Disable and re-enable robot
- Use "Drive Forward (Default)" as safe fallback

---

### Issue: Robot Driving Wrong Direction

**Symptoms:** Forward joystick makes robot go sideways or backward

**Cause:** Field-centric heading not reset

**Fix:**
- Press **Left Bumper** to reset field-centric heading
- Robot should now drive correctly relative to driver

---

### Issue: CAN Bus Errors

**Symptoms:** Motor controllers not responding, `Safety/CAN Utilization` >90%

**Quick Checks:**
1. Are all CAN connections tight?
2. Is CAN termination correct?
3. Are motor controller IDs unique?

**Fix:**
- Restart robot (disable/enable)
- Check physical CAN wiring
- Use Phoenix Tuner X to diagnose

**Workaround:**
- If only one module failing, can sometimes drive on 3 wheels

---

## Emergency Procedures

### Robot Out of Control

**IMMEDIATE ACTION:**
1. **PRESS E-STOP** (big red button)
2. Shout "E-STOP!" to alert field crew
3. Do not approach robot until fully stopped

**After E-Stop:**
- Wait for field crew instructions
- Check for mechanical damage
- Review code for bugs
- Test thoroughly before next match

---

### Smoke or Burning Smell

**IMMEDIATE ACTION:**
1. **DISABLE ROBOT** via Driver Station
2. **FLIP MAIN BREAKER** to OFF
3. Alert field crew and mentors
4. Do not re-enable until inspected

**Common Causes:**
- Stalled motor
- Short circuit
- Damaged wire

---

### Lost Communication During Match

**Symptoms:** Driver Station shows red "Communications" light

**During Match:**
- Robot will continue last command for ~2 seconds
- Then enter disabled state automatically

**DO NOT:**
- Panic
- Run onto field

**After Match:**
- Check radio connections
- Check ethernet cables
- Check battery voltage
- Restart Driver Station

---

## Match Timeline

### Before Match (T-5 minutes)
- Complete hardware checklist
- Complete software checklist
- Select autonomous mode
- Check battery voltage (>12.0V preferred)
- Stage robot in queue

### Queueing (T-3 minutes)
- Robot in queue line
- Driver Station ready
- Drivers ready with controllers
- Review strategy with drive team

### Field Setup (T-1 minute)
- Place robot on field at starting position
- Connect ethernet cable (if required)
- **Turn main breaker ON**
- Verify Driver Station connection (green light)
- **Verify autonomous mode selection**
- Remove all tools/people from field

### Match Start (T-0)
- **Autonomous (15 seconds)**
  - Robot runs selected autonomous
  - DO NOT TOUCH CONTROLLERS
  - Watch for issues

- **Teleoperated (2 minutes 15 seconds)**
  - Normal driving
  - Execute strategy
  - Monitor SmartDashboard for warnings

### Match End
- Robot automatically disables
- **Leave robot on field** until referees signal
- Field crew will signal when to retrieve robot
- Turn off main breaker
- Return to pit

---

## Pit Crew Roles

### Drive Coach
- **Responsibilities:**
  - Strategy decisions
  - Communication with alliance partners
  - Monitor overall match status
- **Location:** Behind driver station glass

### Driver
- **Responsibilities:**
  - Robot driving
  - Execute strategy
  - Communicate robot status
- **Controller:** Port 0 (primary driver)

### Operator (if applicable)
- **Responsibilities:**
  - Manipulator/mechanism control
  - Communicate mechanism status
- **Controller:** Port 1 (secondary)

### Human Player (if applicable)
- **Responsibilities:**
  - Game piece handling
  - Communication with drive team
- **Location:** Human player station

### Software Lead (In Pit)
- **Responsibilities:**
  - Monitor SmartDashboard
  - Watch for errors/warnings
  - Quick code fixes if needed
  - Log analysis between matches

---

## Between Matches

### After Each Match (15-30 minute turnaround)

**Immediate:**
1. Swap battery (even if voltage looks okay)
2. Charge used battery
3. Quick inspection for damage
4. Download logs: `./gradlew downloadLogs`

**Analysis:**
5. Review SmartDashboard data
6. Check for warnings/errors in logs
7. Ask drivers for feedback
8. Note any issues

**Preparation:**
9. Review next match strategy
10. Adjust autonomous if needed
11. Complete pre-match checklist
12. Queue for next match

### Common Adjustments Between Matches

**Speed Adjustment:**
```java
// In Constants.java
public static final double SPEED_MULTIPLIER = 0.5; // Increase/decrease as needed
```

**Autonomous Mode:**
- Change selection in SmartDashboard (no code redeploy needed)

**Vision Tuning:**
```java
// If vision is unreliable, increase trust threshold
public static final double MAX_AMBIGUITY = 0.4; // More permissive
```

**DO NOT:**
- Make major code changes during competition
- Modify TunerConstants.java
- Change field mode from REAL_FIELD

---

## Log Analysis

### Download Logs After Each Match
```bash
./gradlew downloadLogs
```

Logs saved to: `~/wpilib/[YEAR]/logs/`

### Key Things to Look For

**In AdvantageScope or WPILib Log Viewer:**
1. Battery voltage over time (look for brownouts)
2. Current draw spikes (identify high-load moments)
3. Vision target detections (verify AprilTag visibility)
4. Autonomous path accuracy
5. Error messages or warnings

**Red Flags:**
- Battery voltage <11.5V for extended periods
- Current draw >200A sustained
- Vision disconnections
- CAN errors

---

## Code Deployment

### When to Redeploy Code

**You MUST redeploy if:**
- Changed any .java file
- Changed Constants.java
- Updated vendor dependencies
- Changed TunerConstants.java (via Tuner X)

**You DO NOT need to redeploy if:**
- Changed autonomous selection (use SmartDashboard)
- Changed dashboard layout

### Quick Deploy Process

```bash
# Connect laptop to robot (via USB or WiFi)
# Open terminal/command prompt in project folder

./gradlew deploy

# Wait for "Build Successful" message
# Verify code version on Driver Station
```

**Typical deploy time:** 30-60 seconds

### Deploy Troubleshooting

**"Could not find target"**
- Check robot is powered on
- Check network connection
- Verify team number in build.gradle (10413)

**"Build failed"**
- Fix compilation errors first
- Check syntax in changed files
- Ask mentor for help

---

## Competition Day Priorities

### Priority 1: Robot Functionality
- Robot must drive reliably
- Basic autonomous must work
- Safety systems operational

### Priority 2: Autonomous Performance
- Execute simple autonomous successfully
- Align with AprilTags if possible
- Coordinate with alliance partners

### Priority 3: Advanced Features
- Vision-based alignment
- Complex autonomous sequences
- Optimization and tuning

**Remember:** A working simple robot beats a broken complex robot!

---

## Quick Contact List

**At Competition - Fill in before event:**

| Role | Name | Phone | Location |
|------|------|-------|----------|
| Lead Mentor | __________ | __________ | Pit/Field |
| Programming Lead | __________ | __________ | Pit |
| Drive Coach | __________ | __________ | Field |
| Pit Crew Chief | __________ | __________ | Pit |

**Emergency Contacts:**
- Event Coordinator: Check event schedule
- Field Technical Advisor (FTA): Available at field
- Inspection: Check pit map

---

## Important Competition Rules Reminders

### Robot Rules
- Robot must pass inspection before competing
- Bumpers must be correct alliance color
- No modifications on field (except battery swap)
- Must fit within size constraints

### Software Rules
- No wireless communication except official field
- No external processing (all compute on robot)
- Driver Station must be official FRC software

### Safety Rules
- Lithium batteries must be in fireproof bag when charging
- Safety glasses required in pit
- Closed-toe shoes required
- No running in pits

---

## Pre-Competition Preparation

### One Week Before
- [ ] Full robot test
- [ ] Verify all sensors working
- [ ] Test all autonomous modes
- [ ] Practice driver training
- [ ] Charge all batteries
- [ ] Print this guide

### Night Before
- [ ] Code deployed and tested
- [ ] All batteries charged
- [ ] Tools organized
- [ ] Spare parts packed
- [ ] Laptop charged
- [ ] Review strategy

### Morning Of
- [ ] Arrive early
- [ ] Set up pit
- [ ] Robot inspection
- [ ] Driver Station setup
- [ ] Practice match (if available)
- [ ] Team meeting

---

## Success Checklist

**After Each Match, Ask:**
- [ ] Did robot drive as expected?
- [ ] Did autonomous work?
- [ ] Any errors or warnings?
- [ ] Any mechanical issues?
- [ ] Battery voltage adequate?
- [ ] Alliance partners satisfied?
- [ ] Improvements needed?

**After Competition:**
- [ ] Download all logs
- [ ] Document any issues
- [ ] List improvements for next time
- [ ] Thank the team!

---

## Remember

### Competition Day Mindset

**DO:**
- Stay calm under pressure
- Communicate clearly
- Focus on what works
- Help alliance partners
- Learn from mistakes
- Have fun!

**DON'T:**
- Panic when things go wrong
- Make risky code changes
- Blame teammates
- Give up
- Forget to enjoy the experience

### Gracious Professionalism

**Core Values:**
- Respect competitors
- Help other teams
- Celebrate everyone's success
- Learn and teach
- Compete fiercely but fairly

---

## Quick Reference Cards

**Print and laminate these for quick access:**

### Card 1: Controller Layout
```
        [LB] Reset Heading          [RB] (unused)
         ___                           ___
        /   \                         /   \
       |  Y  |                       |  B  |
    [X]|     |[A]                     Point Wheels
       |_____|                        to Stick
         / \
    Left Stick               Right Stick
    (Drive)                  (Rotate)

    [Back] + Y = SysId (DON'T USE IN MATCH)
    [X Button] (hold) = Drive to AprilTag
    [A Button] = Brake (X-wheels)
```

### Card 2: Critical Monitors
```
WATCH THESE DURING MATCH:

✓ Battery Voltage > 11.5V
✓ Total Current < 200A
✓ Vision Connected = TRUE
✓ Robot Pose = Reasonable

RED FLAGS:
✗ Voltage < 10.5V → Brownout risk
✗ Current > 250A → Check for stall
✗ CAN > 90% → Bus overload
```

### Card 3: Emergency Actions
```
ROBOT OUT OF CONTROL
→ PRESS E-STOP IMMEDIATELY

SMOKE/BURNING
→ DISABLE + MAIN BREAKER OFF

LOST COMMS
→ Wait, robot auto-disables

CODE NOT DEPLOYING
→ Check connection, team #, restart
```

---

## Final Notes

This guide covers the most common scenarios during competition. For detailed technical information, refer to:
- `DEVELOPER_GUIDE.md` - Full development documentation
- `CLAUDE.md` - Quick code reference
- `ONBOARDING.md` - New member training

**Keep this guide accessible in the pit at all times during competition!**

**Good luck, and may your robot drive true!**

---

**Team 10413 - Competition Guide v1.0**
*Last Updated: Match preparation reference*
