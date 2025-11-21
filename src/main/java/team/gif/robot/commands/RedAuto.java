package team.gif.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.Constants;
import team.gif.robot.commands.Collector.CollectorShoot;
import team.gif.robot.commands.Elevator.SetElevatorPosition;

public class RedAuto extends SequentialCommandGroup {
    public RedAuto() {
        addCommands(
                new DriveFoward(),
                new turnleftTo45auto(),
                new DriveFowardTIme2(),
                new SetElevatorPosition(Constants.Elevator.STAGE_1_POSITION_TICKS),
                new CollectorShoot().withTimeout(1)
        );

}}
