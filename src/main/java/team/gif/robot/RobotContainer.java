// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import team.gif.robot.commands.AlignToAprilTag;
import team.gif.robot.commands.Collector.CollectorCrateFlip;
import team.gif.robot.commands.Collector.CollectorIntake;
import team.gif.robot.commands.Collector.CollectorShoot;
import team.gif.robot.commands.Elevator.SetElevatorPosition;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    private final SendableChooser<Command> autoChooser;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        /**
         * TODO: Change!!! might have to make timed commands as well
         */
        NamedCommands.registerCommand("Run Intake", new CollectorIntake());
        NamedCommands.registerCommand("Run Shooter", new CollectorShoot());
        NamedCommands.registerCommand("Flip", new CollectorCrateFlip());
        NamedCommands.registerCommand("Elevator Stage 1", new SetElevatorPosition(Constants.Elevator.STAGE_1_POSITION_TICKS));
        NamedCommands.registerCommand("Elevator Stage 0", new SetElevatorPosition(Constants.Elevator.STAGE_0_POSITION_TICKS));
        NamedCommands.registerCommand("Align", new AlignToAprilTag());

        // Configure the trigger bindings
        configureBindings();


        autoChooser = AutoBuilder.buildAutoChooser();

        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {}

    public Command getAutonomousCommand(){
        return autoChooser.getSelected();
    }
}
