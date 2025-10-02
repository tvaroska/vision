// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

/**
 * Simple autonomous command that drives forward for a set duration
 */
public class DriveForwardAuto extends Command {
  private final CommandSwerveDrivetrain m_drivetrain;
  private final SwerveRequest.FieldCentric m_driveRequest;
  private final double m_speed;
  private final double m_duration;
  private double m_startTime;

  /**
   * Creates a new DriveForwardAuto command
   *
   * @param drivetrain The drivetrain subsystem
   * @param speed Speed in meters per second
   * @param duration Duration in seconds
   */
  public DriveForwardAuto(CommandSwerveDrivetrain drivetrain, double speed, double duration) {
    m_drivetrain = drivetrain;
    m_speed = speed;
    m_duration = duration;

    m_driveRequest = new SwerveRequest.FieldCentric()
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage)
        .withDeadband(0.0)
        .withRotationalDeadband(0.0);

    addRequirements(drivetrain);
  }

  /**
   * Creates a DriveForwardAuto with default parameters from Constants
   */
  public DriveForwardAuto(CommandSwerveDrivetrain drivetrain) {
    this(drivetrain, Constants.Auto.DRIVE_FORWARD_SPEED, Constants.Auto.DRIVE_FORWARD_DURATION);
  }

  @Override
  public void initialize() {
    m_startTime = edu.wpi.first.wpilibj.Timer.getFPGATimestamp();
    DataLogManager.log("DriveForwardAuto started: " + m_speed + " m/s for " + m_duration + " seconds");
  }

  @Override
  public void execute() {
    m_drivetrain.setControl(
        m_driveRequest
            .withVelocityX(m_speed)
            .withVelocityY(0.0)
            .withRotationalRate(0.0)
    );
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.setControl(
        m_driveRequest
            .withVelocityX(0.0)
            .withVelocityY(0.0)
            .withRotationalRate(0.0)
    );

    if (interrupted) {
      DataLogManager.log("DriveForwardAuto interrupted");
    } else {
      DataLogManager.log("DriveForwardAuto completed");
    }
  }

  @Override
  public boolean isFinished() {
    return (edu.wpi.first.wpilibj.Timer.getFPGATimestamp() - m_startTime) >= m_duration;
  }
}
