package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  private static SparkMax leftLeaderMotor = new SparkMax(0, MotorType.kBrushless);
  private static SparkMax leftFollowerMotor = new SparkMax(1, MotorType.kBrushless);
  private static SparkMax rightLeaderMotor = new SparkMax(2, MotorType.kBrushless);
  private static SparkMax rightFollowerMotor = new SparkMax(3, MotorType.kBrushless);

  public DriveTrain() {
    SparkMaxConfig leftLeaderConfig = new SparkMaxConfig();
    SparkMaxConfig leftFollowerConfig = new SparkMaxConfig();
    SparkMaxConfig rightLeaderConfig = new SparkMaxConfig();
    SparkMaxConfig rightFollowerConfig = new SparkMaxConfig();

    /* Change/add to these configurations as necessary (e.g. changing inverted states) */
    leftLeaderConfig
    .idleMode(IdleMode.kBrake)
    .inverted(false);
    leftFollowerConfig
    .idleMode(IdleMode.kBrake)
    .inverted(false)
    .follow(leftLeaderMotor);
    rightLeaderConfig
    .idleMode(IdleMode.kBrake)
    .inverted(true);
    rightFollowerConfig
    .idleMode(IdleMode.kBrake)
    .inverted(true)
    .follow(rightLeaderMotor);

    leftLeaderMotor.configure(leftLeaderConfig, null, null);
    leftFollowerMotor.configure(leftFollowerConfig, null, null);
    rightLeaderMotor.configure(rightLeaderConfig, null, null);
    rightFollowerMotor.configure(rightFollowerConfig, null, null);
  }

  public void Drive(double forward, double rotation) {
    leftLeaderMotor.set(forward + rotation);
    rightLeaderMotor.set(forward - rotation);
  }
}
