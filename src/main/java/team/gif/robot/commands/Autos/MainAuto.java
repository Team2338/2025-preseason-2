package team.gif.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.commands.Collector.CollectorCrateFlip;

public class MainAuto extends SequentialCommandGroup {
    public MainAuto() {
        addCommands(
                new DriveFoward(),
                new CollectorCrateFlip().withTimeout(2)
        );

}}
