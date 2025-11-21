package team.gif.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.Constants;
import team.gif.robot.commands.Collector.CollectorCrateFlip;
import team.gif.robot.commands.Collector.CollectorShoot;
import team.gif.robot.commands.Elevator.SetElevatorPosition;

public class MainAuto extends SequentialCommandGroup {
    public MainAuto() {
        addCommands(
                new DriveFoward(),
                new CollectorCrateFlip().withTimeout(2)
        );

}}
