package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class StickDrive extends Command {
  private static Joystick driver;

  public StickDrive(Joystick pDriver) {
    driver = pDriver;

    addRequirements(Robot.driveTrain);
  }

  @Override
  public void execute() {
    Robot.driveTrain.Drive(driver.getY(), driver.getX());
  }

  @Override
  public void end(boolean interrupted) {
    Robot.driveTrain.Drive(0.0, 0.0);
  }
}
