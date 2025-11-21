// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double COLLECTOR_TALON_PERCENT = 0.5;
    public static final double COLLECTOR_OUTTAKE_FAST_PERCENT = 1;


    public static final class Photon {
        public static final double PHOTON_ALIGN_kP = 0.3;
        public static final double PHOTON_YAW_TOLERANCE = 20;
        public static final int APRIL_TAG_ID_RED = 2;
        public static final int APRIL_TAG_ID_BLUE = 3;
        public static final int APRIL_TAG_ID_MANUAL = 2;
        public static final double CAMERA_OFFSET_X_INCHES = 11;
        public static final double CAMERA_OFFSET_X_METERS = Units.inchesToMeters(CAMERA_OFFSET_X_INCHES);
    }

    public static final class Drivetrain {
        public static final double DIFFERENTIAL_DRIVE_ENCODER_ZERO = 0;
        public static final double DRIVE_ENCODER_TICKS_PER_REVOLUTION = 4096;
        public static final double DRIVE_WHEEL_DIAMETER_INCHES = 5;
        public static final double DRIVE_WHEEL_CIRCUMFERENCE_METERS = Units.inchesToMeters(DRIVE_WHEEL_DIAMETER_INCHES) * Math.PI;
        public static final double DRIVE_MOTOR_GEARING = 5.5;
        public static final double DIFFERENTIAL_DRIVE_TRACKWIDTH_INCHES = 26;
        public static final double DIFFERENTIAL_DRIVE_TRACKWIDTH_METERS = Units.inchesToMeters(DIFFERENTIAL_DRIVE_TRACKWIDTH_INCHES);
        public static final int PRIMARY_CLOSED_LOOP_SLOT = 0;
        public static final double LEFT_kP = 0.25;
        public static final double LEFT_kI = 0.0;
        public static final double LEFT_kD = 0.0;
        public static final double LEFT_kF = 0.08;
        public static final double RIGHT_kP = 0.25;
        public static final double RIGHT_kI = 0.0;
        public static final double RIGHT_kD = 0.0;
        public static final double RIGHT_kF = 0.08;
    }

    public static final class Elevator{
        public static final int PRIMARY_CLOSED_LOOP_SLOT = 0;
        public static final int ENCODER_TIMEOUT_MS = 30;
        public static final double kP_GAIN = 0.045;
        public static final double kI_GAIN = 0.0;
        public static final double kD_GAIN = 0.0;
        public static final double kF_GAIN = 0.030;
        public static final double STAGE_1_POSITION_TICKS = 21500;
        public static final double STAGE_0_POSITION_TICKS = 0;
        public static final double REVERSE_SOFT_LIMIT_THRESHOLD_TICKS = -150;
        public static final double FORWARD_SOFT_LIMIT_THRESHOLD_TICKS = 22000;
        public static final double SETPOINT_TOLERANCE_TICKS = 200;
    }
    public static final class Autos {
        public static final double AUTO_TAGET_METERS = 4.5;
        public static final double AUTO_TAGET_METERS2 = 0.5;
        public static final double AUTO_DRIVE_METER_PERC = 0.5;
    }
    public static final class Drive {
        public static final double SRX_ENCODER_METERS_PER_TICK = (Math.PI*0.127)/4096;
        public static final double SRX_ENCODER_METERS_IN_TICKS = 1/(Math.PI*0.127)%4096;
    }
    }
