// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package team.gif.robot.subsystems;


import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.RobotMap;

public class Collector extends SubsystemBase {

    private TalonSRX collectorMotor;

    public Collector() {
        collectorMotor = new TalonSRX(RobotMap.COLLECTOR_TALON_ID);
        collectorMotor.configFactoryDefault();
        collectorMotor.setNeutralMode(NeutralMode.Brake);
    }


    /**
     * Move the collector using percent control mode
     * @param percentOutput a double from -1.0 to 1.0 indicating applied percent
     */
    public void moveCollector(double percentOutput){
        collectorMotor.set(TalonSRXControlMode.PercentOutput, percentOutput);
    }

    /**
     * Stop the collector when called
     */
    public void stopCollector(){
        collectorMotor.set(TalonSRXControlMode.PercentOutput, 0);
    }
}



