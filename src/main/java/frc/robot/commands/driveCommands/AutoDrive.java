package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class AutoDrive extends Command{
  private double forward, rotation;

  public AutoDrive(double pForward, double pRotation) {
    forward = pForward;
    rotation = pRotation;

    addRequirements(Robot.driveTrain);
  }

  @Override
  public void execute() {
    Robot.driveTrain.Drive(forward, rotation);
  }

  @Override
  public void end(boolean interrupted) {
    Robot.driveTrain.Drive(0.0, 0.0);
  }
}
