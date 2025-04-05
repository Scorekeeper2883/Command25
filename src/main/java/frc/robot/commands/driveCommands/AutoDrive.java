package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class AutoDrive extends Command{
  private double left, right;

  public AutoDrive(double pLeft, double pRight) {
    left = pLeft;
    right = pRight;

    addRequirements(Robot.driveTrain);
  }

  @Override
  public void execute() {
    Robot.driveTrain.Drive(left, right);
  }

  @Override
  public void end(boolean interrupted) {
    Robot.driveTrain.Drive(0.0, 0.0);
  }
}
