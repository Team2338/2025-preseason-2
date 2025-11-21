package team.gif.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import team.gif.robot.Constants;
import team.gif.robot.Robot;

public class DriveFoward extends Command {

    private double setpoint = 0.0;

    public DriveFoward() {
        super();
        addRequirements(Robot.driveTrain); // uncomment
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        setpoint = Robot.driveTrain.driveMeters2Ticks(Constants.Autos.AUTO_TAGET_METERS);
    }

    // Called every time the scheduler runs (~20ms) while the command is scheduled
    @Override
    public void execute() {
        Robot.driveTrain.arcadeDrivePercent(0,Constants.Autos.AUTO_DRIVE_METER_PERC);
    }

    // Return true when the command should end, false if it should continue. Runs every ~20ms.
    @Override
    public boolean isFinished() {
        return Robot.driveTrain.atTarget(setpoint);
    }

    // Called when the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.driveTrain.arcadeDrivePercent(0,0);
    }
}
