// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.subsystems.drivers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import team.gif.robot.Constants;
import team.gif.robot.Robot;

import java.util.Optional;

public class Photon extends SubsystemBase {

    private PhotonCamera camera;
    public int targetID;

    public Photon(String camName) {
        camera = new PhotonCamera(camName);
    }

    public void setTargetID() {
        var alliance = DriverStation.getAlliance();

        if (alliance.isPresent()) {
            if (alliance.get() == DriverStation.Alliance.Red) {
                targetID = Constants.Photon.APRIL_TAG_ID_RED;
            } else if (alliance.get() == DriverStation.Alliance.Blue) {
                targetID = Constants.Photon.APRIL_TAG_ID_BLUE;
            }
        } else {
            targetID = Constants.Photon.APRIL_TAG_ID_MANUAL;
        }
    }

    /**
     * @return true if the target is detected, false if not
     */
    public boolean hasTarget() {
        var results = camera.getAllUnreadResults();
        if(!results.isEmpty()){
            var latest = results.get(results.size() - 1);
            if(latest.hasTargets()){
                for(var target : latest.getTargets()){
                    if (target.getFiducialId() == targetID) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * @return the desired target's yaw
     */
    public double getTargetYaw() {
        double targetYaw = 0.0;
        var results = camera.getAllUnreadResults();
        if(!results.isEmpty()){
            var latest = results.get(results.size() - 1);
            if(latest.hasTargets()){
                for(var target : latest.getTargets()){
                    if (target.getFiducialId() == targetID) {
                        targetYaw = target.getYaw();
                    }
                }
            }
        }
        return targetYaw;
    }

    /**
     * Aligns to target
     */
    public void alignToTarget() {
        double rotation = 0.0;
        if(hasTarget()){
            rotation = -1.0 * getTargetYaw() * Constants.Photon.PHOTON_ALIGN_kP;
        }
        Robot.driveTrain.arcadeDrivePercent(rotation, 0.0);
    }

    /**
     * @return true if the yaw is below the tolerance, false if not
     */
    public boolean yawAligned() {
        double checkYaw;

        if (hasTarget()){
            checkYaw = getTargetYaw();
            return Math.abs(checkYaw) <= Constants.Photon.PHOTON_YAW_TOLERANCE;
        }
        return false;
    }

    public int getTargetID() {
        return targetID;
    }

    /**
    public double getDistance(){
        var results = camera.getAllUnreadResults();
        if(!results.isEmpty()){
            var latest = results.get(results.size() - 1);
            if(latest.hasTargets()){
                for(var target : latest.getTargets()){
                    if (target.getFiducialId() == targetID) {
                        return target.getBestCameraToTarget().getTranslation().getNorm();
                    }
                }
            }
        }
        return 0;
    }
     **/
}
