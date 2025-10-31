// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.RobotMap;

public class Elevator extends SubsystemBase {

    private TalonSRX elevatorMotor;

    public Elevator() {
        elevatorMotor = new TalonSRX(RobotMap.ELEVATOR_TALON_ID);
        elevatorMotor.configFactoryDefault();
        elevatorMotor.setNeutralMode(NeutralMode.Brake);

    }
    public void

}
