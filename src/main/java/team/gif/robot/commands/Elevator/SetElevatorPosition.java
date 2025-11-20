package team.gif.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import team.gif.robot.Robot;

public class SetElevatorPosition extends Command {

    double setpoint = 0;

    public SetElevatorPosition(double inputPosition) {
        super();
        addRequirements(Robot.elevator);

         setpoint = inputPosition;

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Robot.elevator.setSetpoint(setpoint);
    }

    // Called every time the scheduler runs (~20ms) while the command is scheduled
    @Override
    public void execute() {}

    // Return true when the command should end, false if it should continue. Runs every ~20ms.
    @Override
    public boolean isFinished() {
        return Robot.elevator.atSetpoint(setpoint);
    }

    // Called when the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}
}
