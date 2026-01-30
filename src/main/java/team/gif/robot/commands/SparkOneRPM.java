package team.gif.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import team.gif.robot.Constants;
import team.gif.robot.Robot;

public class SparkOneRPM extends Command {

    public SparkOneRPM() {
        super();
        addRequirements(Robot.sparkOne);
        SmartDashboard.putNumber("NEO/SETRPM", Constants.SPARK_MOTOR_INITIAL_RPM);
    }

    double rpm;

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        rpm = SmartDashboard.getNumber("NEO/SETRPM", Constants.SPARK_MOTOR_INITIAL_RPM);
    }

    // Called every time the scheduler runs (~20ms) while the command is scheduled
    @Override
    public void execute() {
        Robot.sparkOne.setRPM(rpm);
    }

    // Return true when the command should end, false if it should continue. Runs every ~20ms.
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called when the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.sparkOne.setRPM(0);
    }
}
