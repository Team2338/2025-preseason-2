// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;

public class SparkOne extends SubsystemBase {
    private SparkMax sparkOne;
    private SparkMaxConfig oneSparkCpnfig;
    public SparkClosedLoopController neoPID; //PID should ideally be capitalized
    public RelativeEncoder sparkEncoder;
    /** Creates a new ExampleSubsystem. */
    public SparkOne(){

        sparkOne = new SparkMax(RobotMap.SPARK_ONE_ID, SparkLowLevel.MotorType.kBrushless);
        neoPID = sparkOne.getClosedLoopController();
        sparkEncoder = sparkOne.getEncoder();
        oneSparkCpnfig = new SparkMaxConfig();

       // oneSparkCpnfig.closedLoop.pid(Constants.SPARK_MOTOR_P,Constants.SPARK_MOTOR_I, 0.0);


        oneSparkCpnfig.idleMode(SparkMaxConfig.IdleMode.kBrake); //or replace kBrake with kCoast
        oneSparkCpnfig.inverted(true); //true or false

        sparkOne.configure(oneSparkCpnfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);


    }
    public void set(double percentOutput) { //i think the variable name should just be percent but feel free to ignore this
        sparkOne.set(percentOutput); //This can be a number from -1 (full reverse) to 1 (full forward)

    }

    public void setVoltage(double voltage) {
        sparkOne.setVoltage(voltage); //Our electrical systems run on 12v, so this value can be from -12 to 12
    }
    public void setRPM(double RPM){
        neoPID.setReference(RPM,SparkBase.ControlType.kVelocity);
    }
    public double getRPM(){
        return sparkEncoder.getVelocity();
    }


}
