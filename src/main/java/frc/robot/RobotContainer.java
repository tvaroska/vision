// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.commands.AutoSelector;
import frc.robot.commands.DriveToAprilTag;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.SafetyMonitor;
import frc.robot.subsystems.VisionSubsystem;

public class RobotContainer {

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    private final SafetyMonitor safetyMonitor = new SafetyMonitor();
    private final VisionSubsystem visionSubsystem = new VisionSubsystem(drivetrain);
    private final AutoSelector m_autoSelector;

    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond) * Constants.Drivetrain.SPEED_MULTIPLIER;
    private double MaxAngularRate = RotationsPerSecond.of(Constants.Drivetrain.MAX_ANGULAR_RATE_FRACTION).in(RadiansPerSecond) * Constants.Drivetrain.SPEED_MULTIPLIER;

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * Constants.Drivetrain.DEADBAND_PERCENT)
            .withRotationalDeadband(MaxAngularRate * Constants.Drivetrain.DEADBAND_PERCENT)
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController joystick = new CommandXboxController(Constants.OI.DRIVER_CONTROLLER_PORT);

    public RobotContainer() {
        m_autoSelector = new AutoSelector(drivetrain);

        // Register SafetyMonitor to ensure periodic() is called
        safetyMonitor.setDefaultCommand(
            safetyMonitor.run(() -> {})
        );

        configureBindings();
    }

    private void configureBindings() {

        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        // Brake mode on A button
        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));

        // Point wheels with B button (resolved conflict)
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Drive to AprilTag 1 at 20 inches using front camera on X button
        // To change: DriveToAprilTag.toTag(drivetrain, visionSubsystem, tagID, distanceInches)
        joystick.x().whileTrue(DriveToAprilTag.toTag(drivetrain, visionSubsystem, 1, 20));

        // Run SysId routines when holding back and X/Y
        // Note that each routine should be run exactly once in a single log
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press
        joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        return m_autoSelector.getSelected();
    }
}
