package team.gif.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import team.gif.robot.Robot;

public class AlignToAprilTag extends Command {

    public AlignToAprilTag() {
        super();
        addRequirements(Robot.photon);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
       Robot.photon.setTargetID();
    }

    // Called every time the scheduler runs (~20ms) while the command is scheduled
    @Override
    public void execute() {
        Robot.photon.alignToTarget();
        if(Robot.photon.hasTarget()){
            System.out.println("Target Detected! " + Robot.photon.getTargetID());
        }
        else{
            Robot.driveTrain.arcadeDrivePercent(0,0);
        }
    }

    // Return true when the command should end, false if it should continue. Runs every ~20ms.
    @Override
    public boolean isFinished() {
        return Robot.photon.yawAligned();
    }

    // Called when the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.driveTrain.arcadeDrivePercent(0,0);
    }
}
