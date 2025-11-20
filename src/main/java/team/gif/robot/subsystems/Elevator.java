// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;

public class Elevator extends SubsystemBase {

    private TalonSRX elevatorMotor;

    public Elevator() {
        elevatorMotor = new TalonSRX(RobotMap.ELEVATOR_TALON_ID);

        elevatorMotor.configFactoryDefault();

        elevatorMotor.setNeutralMode(NeutralMode.Brake);

        /**
         * Configure the encoder as type Quadrature
         */
        elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        /**
         * Invert the direction of the elevator motor
         */
        elevatorMotor.setInverted(true);

        /**
         * Set the encoder readings to their opposite values
         */
//        elevatorMotor.setSensorPhase(true);

        /**
         * Enable and set the forward and reverse soft limits of the elevator in encoder ticks
         */
        elevatorMotor.configForwardSoftLimitEnable(true);
        elevatorMotor.configForwardSoftLimitThreshold(Constants.Elevator.FORWARD_SOFT_LIMIT_THRESHOLD_TICKS);
        elevatorMotor.configReverseSoftLimitEnable(true);
        elevatorMotor.configReverseSoftLimitThreshold(Constants.Elevator.REVERSE_SOFT_LIMIT_THRESHOLD_TICKS);

        /**
         * Configure PID for the elevator
         */
        elevatorMotor.config_kP(
                Constants.Elevator.PRIMARY_CLOSED_LOOP_SLOT,
                Constants.Elevator.kP_GAIN,
                Constants.Elevator.ENCODER_TIMEOUT_MS
        );

        elevatorMotor.config_kI(
                Constants.Elevator.PRIMARY_CLOSED_LOOP_SLOT,
                Constants.Elevator.kI_GAIN,
                Constants.Elevator.ENCODER_TIMEOUT_MS
        );

        elevatorMotor.config_kD(
                Constants.Elevator.PRIMARY_CLOSED_LOOP_SLOT,
                Constants.Elevator.kD_GAIN,
                Constants.Elevator.ENCODER_TIMEOUT_MS
        );

        elevatorMotor.config_kF(
                Constants.Elevator.PRIMARY_CLOSED_LOOP_SLOT,
                Constants.Elevator.kF_GAIN,
                Constants.Elevator.ENCODER_TIMEOUT_MS
        );


    }

    /**
     * Move the elevator using percent control mode
     * Positive values for intake, negative for outtake
     * @param percentOutput a double from -1.0 to 1.0 indicating applied percent
     */
    public void moveElevator(double percentOutput) {
        elevatorMotor.set(TalonSRXControlMode.PercentOutput, percentOutput);
    }

    /**
     * Set the position of the elevator
     * @param setpoint desired position in encoder ticks
     */
    public void setSetpoint(double setpoint){
        elevatorMotor.set(TalonSRXControlMode.Position, setpoint);
    }

    /**
     * Get the elevator position
     * @return the elevator position in encoder ticks (4096 is one revolution)
     */
    public double getElevatorPosition(){
        return elevatorMotor.getSelectedSensorPosition();
//        elevatorMotor.getSensorCollection().getPulseWidthPosition();
    }

    /**
     *Set the encoder to zero (Reading not actual position)
     */
    public void setZero(){
        elevatorMotor.setSelectedSensorPosition(0);
    }

    /**
     * Check if the current encoder position meets the tolerance value
     * @param setpoint the desired setpoint
     * @return true if meets tolerance value, false if not
     */
    public boolean atSetpoint(double setpoint){
        double currentSetpoint = getElevatorPosition();
        return Math.abs(currentSetpoint - setpoint) <= Constants.Elevator.SETPOINT_TOLERANCE_TICKS;
    }


}
