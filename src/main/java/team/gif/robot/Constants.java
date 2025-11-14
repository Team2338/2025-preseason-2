// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot;

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
    public static final double COLLECTOR_OUTTAKE_FAST_PERCENT = 0.8;

    public static final class Elevator{
        public static final int PRIMARY_CLOSED_LOOP_SLOT = 0;
        public static final int ENCODER_TIMEOUT_MS = 20;
        public static final double kP_GAIN = 0;
        public static final double kI_GAIN = 0;
        public static final double kD_GAIN = 0;
        public static final double kF_GAIN = 0;
        public static final double ZERO_POSITION_TICKS = 0;
        public static final double STAGE_1_POSITION_TICKS = 0;
    }
}
