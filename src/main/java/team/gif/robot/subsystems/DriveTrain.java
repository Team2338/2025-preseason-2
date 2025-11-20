// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.Robot;
import team.gif.robot.RobotMap;

public class DriveTrain extends SubsystemBase {

    private WPI_TalonSRX leftMotor;
    private WPI_TalonSRX rightMotor;
    private DifferentialDrive drive;
    private DifferentialDriveKinematics driveKinematics;
    private ChassisSpeeds chassisSpeeds;
    private DifferentialDriveOdometry odometry;

    public DriveTrain() {
        /**
         * Create motor objects with CAN IDs
         */
        leftMotor = new WPI_TalonSRX(RobotMap.LEFT_DRIVETRAIN_TALON_ID);
        rightMotor = new WPI_TalonSRX(RobotMap.RIGHT_DRIVETRAIN_TALON_ID);

        /**
         * Configure to factory defaults on startup to prevent any issues
         */
        leftMotor.configFactoryDefault();
        rightMotor.configFactoryDefault();

        /**
         * Set the drive motors to brake mode when they are not being run
         */
        leftMotor.setNeutralMode(NeutralMode.Brake);
        rightMotor.setNeutralMode(NeutralMode.Brake);

        /**
         * Configure encoder type
         * CTRE Mag Encoder for both drivetrain motors
         */

        leftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        rightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        /**
         * Zero encoders on robot startup
         */

        leftMotor.setSelectedSensorPosition(Constants.Drivetrain.DIFFERENTIAL_DRIVE_ENCODER_ZERO);
        rightMotor.setSelectedSensorPosition(Constants.Drivetrain.DIFFERENTIAL_DRIVE_ENCODER_ZERO);

        /**
         * Create a kinematics object with a track width
         * defined in Constants.java
         */

        driveKinematics = new DifferentialDriveKinematics(Constants.Drivetrain.DIFFERENTIAL_DRIVE_TRACKWIDTH_METERS);

        /**
         * Create odometry object using pigeon degrees to rotation 2d and the initial
         * position of the wheels
         */
        odometry = new DifferentialDriveOdometry(
                Rotation2d.fromDegrees(Robot.pigeon.get360Heading()),
                getWheelPositionMeters(leftMotor.getSelectedSensorPosition()),
                getWheelPositionMeters(rightMotor.getSelectedSensorPosition())
                );

        /**
         * Create DifferentialDrive object with left and right motors
         */
        drive = new DifferentialDrive(leftMotor, rightMotor);

        /**
         * Pathplanner autobuilder setup, read the docs for more info
         * {@link https://pathplanner.dev/pplib-build-an-auto.html#load-an-auto}
         */

        RobotConfig config = null;
        try{
            config = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
        }

        AutoBuilder.configure(
                this::getPose,
                this::resetPose,
                this::getRobotRelativeSpeeds,
                (relativeSpeeds) -> driveRobotRelative(relativeSpeeds),
                new PPLTVController(0.2),
                config,
                () -> {
                    var alliance = DriverStation.getAlliance();
                    if(alliance.isPresent()){
                        return alliance.get() == DriverStation.Alliance.Red;
                    }
                    return false;
                },
                this
        );

    }

    /**
     * Drive the robot with percent control (-1 to 1) representing -100% to 100&
     * @param rotation rotation percent
     * @param speed speed percent
     */
    public void arcadeDrivePercent(double rotation, double speed){
        drive.arcadeDrive(rotation, speed);
    public double getLeftDriveEncoder(){
        return leftMotor.getSelectedSensorPosition();
    }

    /**
     *Drive the robot using left and right wheel velocities
     * @param leftVelocity left wheel velocity
     * @param rightVelocity right wheel velocity
     */
    public void arcadeDriveVelocity(double leftVelocity, double rightVelocity) {
        leftMotor.set(ControlMode.Velocity, getEncoderVelocityFromWheelVelocity(leftVelocity));
        rightMotor.set(ControlMode.Velocity, getEncoderVelocityFromWheelVelocity(rightVelocity));
    }
    public double getRightDriveEncoder(){
        return rightMotor.getSelectedSensorPosition();
    }

    /**
     * @param motorPosition the measured position of the encoder in native units
     * @return wheel position in meters
     */
    public double getWheelPositionMeters(double motorPosition){
        double motorRevolutions = (motorPosition/Constants.Drivetrain.DRIVE_ENCODER_TICKS_PER_REVOLUTION);
        double wheelRevolutions = motorRevolutions / Constants.Drivetrain.DRIVE_MOTOR_GEARING;
        return wheelRevolutions * Constants.Drivetrain.DRIVE_WHEEL_CIRCUMFERENCE_METERS;
    }


    /**
     * @param motorVelocity the measured velocity of the encoder in native units per 100ms
     * @return wheel velocity in m/s
     */
    public double getWheelVelocityMeters(double motorVelocity){
        double motorRevolutionsPerSecond = (motorVelocity/Constants.Drivetrain.DRIVE_ENCODER_TICKS_PER_REVOLUTION) * 10;
        double wheelRevolutionsPerSecond = motorRevolutionsPerSecond / Constants.Drivetrain.DRIVE_MOTOR_GEARING;
        return wheelRevolutionsPerSecond * Constants.Drivetrain.DRIVE_WHEEL_CIRCUMFERENCE_METERS;
    }

    /**
     * @param wheelVelocity the velocity of the wheel derived from getWheelVelocityMeters
     * @return the wheel velocity (m/s) in native encoder units
     */
    public double getEncoderVelocityFromWheelVelocity(double wheelVelocity){
        double wheelRevolutionsPerSecond = wheelVelocity / Constants.Drivetrain.DRIVE_WHEEL_CIRCUMFERENCE_METERS;
        double motorRevolutionsPerSecond = wheelRevolutionsPerSecond * Constants.Drivetrain.DRIVE_MOTOR_GEARING;
        return (motorRevolutionsPerSecond * Constants.Drivetrain.DRIVE_ENCODER_TICKS_PER_REVOLUTION) / 10;
    }


    /**
     * Use the driveKinematics object to convert left and right wheel speeds (in m/s)
     * to a ChassisSpeeds object
     * @return a singular chassisSpeeds object, containing linear velocity and angular velocity
     */
    public ChassisSpeeds getRobotRelativeSpeeds(){
        var wheelSpeeds = new DifferentialDriveWheelSpeeds(
                getWheelVelocityMeters(leftMotor.getSelectedSensorVelocity()),
                getWheelVelocityMeters(rightMotor.getSelectedSensorVelocity())
        );

        chassisSpeeds = driveKinematics.toChassisSpeeds(wheelSpeeds);

        return chassisSpeeds;

    }

    /**
     * Convert linear velocity (vx) and angular velocity (omega) to left and right wheel velocities, aka
     * ChassisSpeeds object to a DifferentialDriveWheelSpeeds object
     * Use the wheel velocities to drive the robot with arcadeDriveVelocity
     * @param relativeSpeeds the ChassisSpeeds object from getRobotRelativeSpeeds
     */
    public void driveRobotRelative(ChassisSpeeds relativeSpeeds){
        DifferentialDriveWheelSpeeds wheelSpeeds = driveKinematics.toWheelSpeeds(relativeSpeeds);
        arcadeDriveVelocity(wheelSpeeds.leftMetersPerSecond,  wheelSpeeds.rightMetersPerSecond);
    }

    /**
     * Update the pose of the robot every cycle (in robot periodic)
     */
    public void updatePose(){
        var gyroAngle = Rotation2d.fromDegrees(Robot.pigeon.get360Heading());
        double leftWheelPosition = getWheelPositionMeters(leftMotor.getSelectedSensorPosition());
        double rightWheelPosition = getWheelPositionMeters(rightMotor.getSelectedSensorPosition());
        odometry.update(gyroAngle, leftWheelPosition, rightWheelPosition);
    }

    /**
     * Get the Pose2d object from odometry, representing the robot's
     * x and y position as well as its rotation (Rotation2d)
     */
    public Pose2d getPose(){
        return odometry.getPoseMeters();
    }

    /**
     * Resets the Pose2d object using the odometry object
     */
    public void resetPose(Pose2d pose){
        odometry.resetPosition(
                Rotation2d.fromDegrees(Robot.pigeon.get360Heading()),
                getWheelPositionMeters(leftMotor.getSelectedSensorPosition()),
                getWheelPositionMeters(rightMotor.getSelectedSensorPosition()),
                pose
        );
    }




}
