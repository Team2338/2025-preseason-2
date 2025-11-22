package team.gif.robot;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import team.gif.robot.commands.Autos.MainAuto;
import team.gif.robot.commands.Autos.RedAuto;

public class UI {
    public SendableChooser<Command> autoChooser = new SendableChooser<>();

    /**
     *  Widgets (e.g. gyro, text, True/False flags),
     *  buttons (e.g. SmartDashboard.putData("Reset", new ResetHeading()); ),
     *  and Chooser options (e.g. auto mode, auto delay)
     *
     *  Placed in SmartDashboard network table
     *  After dashboard loads for the first time, manually move items from network table onto respective dashboard tab
     *  and save file as "YYYY elastic-layout.json"
     */
    public UI() {
        autoChooser.setDefaultOption("No Auto", null);
        autoChooser.addOption("red", new RedAuto());
        autoChooser.addOption("main PICK THIS ONE THIS ONE WORKS, drive forward flip", new MainAuto());
        SmartDashboard.putData("Auto", autoChooser);

    }

    /**
     * Widgets which are updated periodically should be placed here
     *
     * Convenient way to format a number is to use putString w/ format:
     *     SmartDashboard.putString("Elevator", String.format("%11.2f", Elevator.getPosition());
     */

    public void update() {
        SmartDashboard.putNumber("Elevator Position", Robot.elevator.getElevatorPosition());
        SmartDashboard.putString("Left Drive Encoder", String.format("%38.5f", Robot.driveTrain.getLeftDriveEncoder()));
        SmartDashboard.putString("Right Drive Encoder", String.format("%38.5f", Robot.driveTrain.getRightDriveEncoder()));
        SmartDashboard.putNumber("Match Time", Robot.matchTime);
        SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putNumber("Pigeon", Robot.pigeon.getHeading());
        SmartDashboard.putNumber("Detected ID", Robot.photon.getTargetID());
        SmartDashboard.putNumber("Target Yaw", Robot.photon.getTargetYaw());

    }

}
