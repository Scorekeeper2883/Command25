package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  private static SparkMax leftLeaderMotor, leftFollowerMotor, rightLeaderMotor, rightFollowerMotor;
  private static RelativeEncoder leftEncoder, rightEncoder;
  private static SparkMaxConfig leftLeaderConfig, leftFollowerConfig, rightLeaderConfig, rightFollowerConfig;
  private static PIDController drivePID;

  private static final double MAX_RPM = 5676;
  private static final double GEAR_RATIO = 1 / 6.75;
  private static final double WHEEL_CIRCUMFERENCE = 6 * 3.14;

  public DriveTrain() {
    leftLeaderMotor = new SparkMax(0, MotorType.kBrushless);
    leftFollowerMotor = new SparkMax(1, MotorType.kBrushless);
    rightLeaderMotor = new SparkMax(2, MotorType.kBrushless);
    rightFollowerMotor = new SparkMax(3, MotorType.kBrushless);

    leftEncoder = leftLeaderMotor.getEncoder();
    rightEncoder = rightLeaderMotor.getEncoder();
  
    leftLeaderConfig = new SparkMaxConfig();
    leftFollowerConfig = new SparkMaxConfig();
    rightLeaderConfig = new SparkMaxConfig();
    rightFollowerConfig = new SparkMaxConfig();

    drivePID = new PIDController(0, 0, 0);

    setFollow();
    setInverted();
    BrakeMode();
  }

  public void BrakeMode() {
    leftLeaderConfig.idleMode(IdleMode.kBrake);
    leftFollowerConfig.idleMode(IdleMode.kBrake);
    rightLeaderConfig.idleMode(IdleMode.kBrake);
    rightFollowerConfig.idleMode(IdleMode.kBrake);

    setConfig();
  }

  public void CoastMode() {
    leftLeaderConfig.idleMode(IdleMode.kCoast);
    leftFollowerConfig.idleMode(IdleMode.kCoast);
    rightLeaderConfig.idleMode(IdleMode.kCoast);
    rightFollowerConfig.idleMode(IdleMode.kCoast);

    setConfig();
  }

  public void Drive(double left, double right) {
    leftLeaderMotor.set(drivePID.calculate(leftEncoder.getVelocity() / MAX_RPM, left));
    rightLeaderMotor.set(drivePID.calculate(rightEncoder.getVelocity() / MAX_RPM, right));
  }

  public double getLeftEncoder() {
    return leftEncoder.getPosition() * GEAR_RATIO * WHEEL_CIRCUMFERENCE;
  }

  public double getRightEncoder() {
    return rightEncoder.getPosition() * GEAR_RATIO * WHEEL_CIRCUMFERENCE;
  }

  public void resetLeftEncoder() {
    leftEncoder.setPosition(0);
  }

  public void resetRightEncoder() {
    rightEncoder.setPosition(0);
  }

  private void setFollow() {
    leftFollowerConfig.follow(leftLeaderMotor);
    rightFollowerConfig.follow(rightLeaderMotor);
  }

  private void setInverted() {
    leftLeaderConfig.inverted(false);
    leftFollowerConfig.inverted(false);
    rightLeaderConfig.inverted(true);
    rightFollowerConfig.inverted(true);
  }

  private void setConfig() {
    leftLeaderMotor.configure(leftLeaderConfig, null, null);
    leftFollowerMotor.configure(leftFollowerConfig, null, null);
    rightLeaderMotor.configure(rightLeaderConfig, null, null);
    rightFollowerMotor.configure(rightFollowerConfig, null, null);
  }
}
