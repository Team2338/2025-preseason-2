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


        elevatorMotor.configSelectedFeedbackSensor(
                FeedbackDevice.QuadEncoder,
                Constants.Elevator.PRIMARY_CLOSED_LOOP_SLOT,
                Constants.Elevator.ENCODER_TIMEOUT_MS
        );

//        elevatorMotor.setSensorPhase(true);

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

        elevatorMotor.setSelectedSensorPosition(Constants.Elevator.ZERO_POSITION_TICKS);

    }

    public void moveElevator(double percentOutput) {
        elevatorMotor.set(TalonSRXControlMode.PercentOutput, percentOutput);
    }

    public void setSetpoint(double setpoint){
        elevatorMotor.set(TalonSRXControlMode.Position, setpoint);
    }

    public double getElevatorPosition(){
        return elevatorMotor.getSelectedSensorPosition(Constants.Elevator.PRIMARY_CLOSED_LOOP_SLOT);
    }

}
