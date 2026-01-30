// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkFlexConfig;import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;

public class SparkOne extends SubsystemBase {
    private SparkFlex sparkOne;
    private SparkFlexConfig oneSparkCpnfig;
    public SparkClosedLoopController neoPID; //PID should ideally be capitalized
    public RelativeEncoder sparkEncoder;
    double kP = Constants.SPARK_MOTOR_P;
    double kI = Constants.SPARK_MOTOR_I;
    double kD = 0;

    public SparkOne(){

        sparkOne = new SparkFlex(RobotMap.SPARK_ONE_ID, SparkLowLevel.MotorType.kBrushless);
        neoPID = sparkOne.getClosedLoopController();
        sparkEncoder = sparkOne.getEncoder();
        oneSparkCpnfig = new SparkFlexConfig();

        oneSparkCpnfig.closedLoop.pid(kP, kI, kD);


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
        neoPID.setReference(-RPM,SparkBase.ControlType.kVelocity);
    }

    public double getRPM(){
        return -sparkEncoder.getVelocity();
    }

    @Override
    public void periodic() {
        super.periodic();
        SmartDashboard.putNumber("NEO/kP", kP);
        SmartDashboard.putNumber("NEO/kI", kI);
        SmartDashboard.putNumber("NEO/kD", kD);

        double kP2 = SmartDashboard.getNumber("NEO/kP", kP);
        double kI2 = SmartDashboard.getNumber("NEO/kI", kI);
        double kD2 = SmartDashboard.getNumber("NEO/kD", kD);

        if(kP2 != kP || kI2 != kI || kD2 != kD){
            kP = kP2; kI = kI2; kD = kD2;
            oneSparkCpnfig.closedLoop.pid(kP, kI, kD);
            sparkOne.configure(oneSparkCpnfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
        }


    }
}
