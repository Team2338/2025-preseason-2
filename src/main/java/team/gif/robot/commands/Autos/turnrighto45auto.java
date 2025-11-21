package team.gif.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.Command;
import team.gif.robot.Robot;

public class turnrighto45auto extends Command {
    public turnrighto45auto() {
        super();
        addRequirements(Robot.driveTrain); // uncomment
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs (~20ms) while the command is scheduled
    @Override
    public void execute() {
            Robot.driveTrain.arcadeDrivePercent(0.4,0);
        }

    // Return true when the command should end, false if it should continue. Runs every ~20ms.
    @Override
    public boolean isFinished(){
        return Robot.pigeon.get360Heading() > (0) && Robot.pigeon.get360Heading() < (36);
    }

    // Called when the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.driveTrain.arcadeDrivePercent(0,0);
        System.out.println("turn finished");
    }}
