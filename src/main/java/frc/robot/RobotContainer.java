// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Autos;
import frc.robot.commands.driveCommands.StickDrive;

public class RobotContainer {
  public static final Joystick driveController = new Joystick(0);
  private static SendableChooser<Command> autonomous = new SendableChooser<>();

  public RobotContainer() {
    autonomous.setDefaultOption("No Auto", Commands.none());
    autonomous.addOption("Simple Cross Line", Autos.SimpleCrossLine());
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
