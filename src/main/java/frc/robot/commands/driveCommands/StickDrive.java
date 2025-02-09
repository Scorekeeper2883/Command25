package frc.robot.commands.driveCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class StickDrive extends Command {
  private static final double deadband = 0.05;
  private static double forward = 0.0;
  private static double rotation = 0.0;

  public StickDrive() {
    addRequirements(Robot.driveTrain);
  }

  @Override
  public void execute() {
    forward = MathUtil.applyDeadband(RobotContainer.driveController.getLeftY(), deadband);  // 0 +/- deadband is still 0
    rotation = MathUtil.applyDeadband(RobotContainer.driveController.getRightX(), deadband);  // 0 +/- deadband is still 0

    Robot.driveTrain.Drive(forward * Math.abs(forward), rotation * Math.abs(rotation));
  }

  @Override
  public void end(boolean interrupted) {
    Robot.driveTrain.Drive(0.0, 0.0);
  }
}
