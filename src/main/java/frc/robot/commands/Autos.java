package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.driveCommands.AutoDrive;

public class Autos extends Command {
  public Autos() {
    throw new UnsupportedOperationException("This is a Utility Class!");
  }

  /**
   * Crosses the Line for easy Autonomous points (12.5s execution)
   */
  public static Command SimpleCrossLine() {
    return new SequentialCommandGroup(
      new WaitCommand(10.0),
      new AutoDrive(0.25, 0.0).withTimeout(2.5));
  }
}
