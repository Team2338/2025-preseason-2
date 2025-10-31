// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.RobotMap;

public class TalonTwo extends SubsystemBase {
    /** Creates a new ExampleSubsystem. */
    private TalonSRX talonTwo;

    public TalonTwo() {
        talonTwo = new TalonSRX(RobotMap.TALON_TWO_ID);
        talonTwo.configFactoryDefault();
        talonTwo.setNeutralMode(NeutralMode.Brake);
    }
    public void moveTalon(double percentOutput){
        talonTwo.set(TalonSRXControlMode.PercentOutput, percentOutput);
    }


    }


