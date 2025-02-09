// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.driveCommands.StickDrive;

public class RobotContainer {
  public static final XboxController driveController = new XboxController(0);
  private static SendableChooser<Command> autonomous;

  public RobotContainer() {
    autonomous = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Autonomous", autonomous);

    Robot.driveTrain.setDefaultCommand(new StickDrive());

    configureBindings();
  }

  private void configureBindings() {
    final JoystickButton exampleButton = new JoystickButton(driveController, 99); // 99 is a Reasonably Large Number (RLN), meant to not be used

    exampleButton.whileTrue(Commands.none());
  }

  public Command getAutonomousCommand() {
    return autonomous.getSelected();
  }
}
