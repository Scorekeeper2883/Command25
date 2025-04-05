package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  private static SparkMax leftLeaderMotor, leftFollowerMotor, rightLeaderMotor, rightFollowerMotor;
  private static SparkMaxConfig leftLeaderConfig, leftFollowerConfig, rightLeaderConfig, rightFollowerConfig;

  public DriveTrain() {
    leftLeaderMotor = new SparkMax(0, MotorType.kBrushless);
    leftFollowerMotor = new SparkMax(1, MotorType.kBrushless);
    rightLeaderMotor = new SparkMax(2, MotorType.kBrushless);
    rightFollowerMotor = new SparkMax(3, MotorType.kBrushless);
  
    leftLeaderConfig = new SparkMaxConfig();
    leftFollowerConfig = new SparkMaxConfig();
    rightLeaderConfig = new SparkMaxConfig();
    rightFollowerConfig = new SparkMaxConfig();

    setFollow();
    setInverted();
    BrakeMode();
  }

  public void Drive(double forward, double rotation) {
    leftLeaderMotor.set(forward + rotation);
    rightLeaderMotor.set(forward - rotation);
  }

  public void CoastMode() {
    leftLeaderConfig.idleMode(IdleMode.kCoast);
    leftFollowerConfig.idleMode(IdleMode.kCoast);
    rightLeaderConfig.idleMode(IdleMode.kCoast);
    rightFollowerConfig.idleMode(IdleMode.kCoast);

    setConfig();
  }

  public void BrakeMode() {
    leftLeaderConfig.idleMode(IdleMode.kBrake);
    leftFollowerConfig.idleMode(IdleMode.kBrake);
    rightLeaderConfig.idleMode(IdleMode.kBrake);
    rightFollowerConfig.idleMode(IdleMode.kBrake);

    setConfig();
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
