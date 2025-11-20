package team.gif.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import team.gif.robot.Constants;
import team.gif.robot.Robot;

import java.util.Optional;

public class AlignToAprilTag extends Command {

Optional<DriverStation.Alliance> alliance;
private int targetID;

    public AlignToAprilTag() {
        super();
        addRequirements(Robot.driveTrain, Robot.photon);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        alliance = DriverStation.getAlliance();

        if(alliance.equals(DriverStation.Alliance.Red)){
            targetID = Constants.Photon.APRIL_TAG_ID_RED;
        }
        else if(alliance.equals(DriverStation.Alliance.Blue)){
            targetID = Constants.Photon.APRIL_TAG_ID_BLUE;
        }
        else{
            targetID = Constants.Photon.APRIL_TAG_ID_MANUAL;
        }
    }

    // Called every time the scheduler runs (~20ms) while the command is scheduled
    @Override
    public void execute() {
        if(Robot.photon.hasTarget(targetID)){
            Robot.photon.alignToTarget(targetID);
        }
        else{
            Robot.driveTrain.arcadeDrivePercent(0,0);
        }
    }

    // Return true when the command should end, false if it should continue. Runs every ~20ms.
    @Override
    public boolean isFinished() {
        return Robot.photon.yawAligned(targetID);
    }

    // Called when the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.driveTrain.arcadeDrivePercent(0,0);
    }
}
