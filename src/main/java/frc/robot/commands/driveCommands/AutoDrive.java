package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class AutoDrive extends Command{
  private double left, right, distance;

  public AutoDrive(double pLeft, double pRight) {
    left = pLeft;
    right = pRight;
    distance = 0;

    addRequirements(Robot.driveTrain);
  }

  public AutoDrive(double pLeft, double pRight, double pDistance) {
    left = pLeft;
    right = pRight;
    distance = pDistance;

    addRequirements(Robot.driveTrain);
  }

  @Override
  public void initialize() {
    Robot.driveTrain.resetLeftEncoder();
    Robot.driveTrain.resetRightEncoder();
  }

  @Override
  public void execute() {
    Robot.driveTrain.Drive(left, right);
  }

  @Override
  public void end(boolean interrupted) {
    Robot.driveTrain.Drive(0.0, 0.0);
  }

  @Override
  public boolean isFinished() {
    if (Math.abs(distance) > 0) {
      return Robot.driveTrain.getRightEncoder() / distance >= 1;
    }

    return false;
  }
}
