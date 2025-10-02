// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.CommandSwerveDrivetrain;

/**
 * Autonomous selector for choosing between different auto routines
 */
public class AutoSelector {
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();
  private final CommandSwerveDrivetrain m_drivetrain;

  public AutoSelector(CommandSwerveDrivetrain drivetrain) {
    m_drivetrain = drivetrain;

    // Add autonomous options
    m_chooser.setDefaultOption("Do Nothing", Commands.print("No autonomous action"));
    m_chooser.addOption("Drive Forward", new DriveForwardAuto(m_drivetrain));
    m_chooser.addOption("Drive Forward (Short)", new DriveForwardAuto(m_drivetrain, 1.0, 1.0));
    m_chooser.addOption("Drive Forward (Long)", new DriveForwardAuto(m_drivetrain, 1.5, 3.0));

    // Publish to SmartDashboard
    SmartDashboard.putData("Auto Selector", m_chooser);

    DataLogManager.log("AutoSelector initialized with " + getAvailableOptions() + " options");
  }

  /**
   * Get the selected autonomous command
   * @return The selected autonomous command
   */
  public Command getSelected() {
    Command selected = m_chooser.getSelected();
    DataLogManager.log("Selected autonomous: " + m_chooser.getSelected().getName());
    return selected;
  }

  /**
   * Get the number of available auto options
   * @return Number of options
   */
  private int getAvailableOptions() {
    return 4; // Update if more options are added
  }
}
