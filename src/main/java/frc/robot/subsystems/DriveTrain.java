package frc.robot.subsystems;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  private static SparkMax leftLeaderMotor = new SparkMax(0, MotorType.kBrushless);
  private static SparkMax leftFollowerMotor = new SparkMax(1, MotorType.kBrushless);
  private static SparkMax rightLeaderMotor = new SparkMax(2, MotorType.kBrushless);
  private static SparkMax rightFollowerMotor = new SparkMax(3, MotorType.kBrushless);

  RelativeEncoder leftEncoder;
  RelativeEncoder rightEncoder;

  public DriveTrain() {
    leftEncoder = leftLeaderMotor.getEncoder();
    rightEncoder = rightLeaderMotor.getEncoder();

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

    // Load the RobotConfig from the GUI settings. You should probably
    // store this in your Constants file
    RobotConfig config = null;
    try{
      config = RobotConfig.fromGUISettings();
    } catch (Exception e) {
      // Handle exception as needed
      e.printStackTrace();
    }

    // Configure AutoBuilder last
    AutoBuilder.configure(
            this::getPose, // Robot pose supplier
            this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
            this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
            (speeds, feedforwards) -> driveRobotRelative(speeds), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
            new PPLTVController(0.02), // PPLTVController is the built in path following controller for differential drive trains
            config, // The robot configuration
            () -> {
              // Boolean supplier that controls when the path will be mirrored for the red alliance
              // This will flip the path being followed to the red side of the field.
              // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

              var alliance = DriverStation.getAlliance();
              if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
              }
              return false;
            },
            this // Reference to this subsystem to set requirements
    );
  }

  public void Drive(double forward, double rotation) {  // VelocityPercentage!
    leftLeaderMotor.set(forward + rotation);
    rightLeaderMotor.set(forward - rotation);
  }

  private void driveRobotRelative(ChassisSpeeds speeds) { // MetersPerSecond!
    Drive(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond);
  }

  private Pose2d getPose() {
    return new Pose2d(
      leftEncoder.getPosition(),
      rightEncoder.getPosition(),
      new Rotation2d(leftEncoder.getPosition(), rightEncoder.getPosition()));
  }

  private ChassisSpeeds getRobotRelativeSpeeds() {  // MetersPerSecod!
    return new ChassisSpeeds(
      (leftLeaderMotor.get() + rightLeaderMotor.get()) / 2,
      0,
      leftLeaderMotor.get() - rightLeaderMotor.get());
  }

  private void resetPose(Pose2d temp) {
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }
}
