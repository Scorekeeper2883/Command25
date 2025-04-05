package frc.robot.commands.driveCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class StickDrive extends Command {
  private static final double deadband = 0.05;
  private static double left, right;

  public StickDrive() {
    addRequirements(Robot.driveTrain);
  }

  @Override
  public void execute() {
    left = MathUtil.applyDeadband(RobotContainer.driveController.getLeftY()
                                  + RobotContainer.driveController.getRightX(), deadband);
    right = MathUtil.applyDeadband(RobotContainer.driveController.getLeftY()
                                  - RobotContainer.driveController.getRightX(), deadband);

    if(Math.abs(left) > 1) {
      left = left / Math.abs(left);
    }
    if(Math.abs(right) > 1) {
      right = right / Math.abs(left);
    }

    Robot.driveTrain.Drive(left * Math.abs(left), right * Math.abs(right));
  }

  @Override
  public void end(boolean interrupted) {
    Robot.driveTrain.Drive(0.0, 0.0);
  }
}
