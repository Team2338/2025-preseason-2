// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team.gif.robot.subsystems.drivers;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import team.gif.robot.Constants;
import team.gif.robot.Robot;

public class Photon extends SubsystemBase {

    private PhotonCamera camera;

    public Photon(String camName) {
        camera = new PhotonCamera(camName);
    }

    /**
     * @param targetID the target to be identified
     * @return true if the target is detected, false if not
     */
    public boolean hasTarget(int targetID) {
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

    public double getDistance(int targetID){
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
    /**
     * @param targetID the ID of the desired target's yaw
     * @return the desired target's yaw
     */
    public double getTargetYaw(int targetID) {
        double targetYaw;
        double distance;
        double offsetAngle;
        double realYaw = 0.0;
        var results = camera.getAllUnreadResults();
        if(!results.isEmpty()){
            var latest = results.get(results.size() - 1);
            if(latest.hasTargets()){
                for(var target : latest.getTargets()){
                    if (target.getFiducialId() == targetID) {
                        targetYaw = target.getYaw();
                        distance = getDistance(targetID);
                        offsetAngle = Math.toDegrees(Math.atan2(Constants.Photon.CAMERA_OFFSET_X_METERS, distance));
                        realYaw = targetYaw + offsetAngle;
                    }
                }
            }
        }
        return realYaw;
    }

    /**
     * Aligns to target
     * @param targetID the id of the target to be aligned to
     */
    public void alignToTarget(int targetID) {
        double rotation = 0.0;
        if(hasTarget(targetID)){
            rotation = -1.0 * getTargetYaw(targetID) * Constants.Photon.PHOTON_ALIGN_kP;
        }
        Robot.driveTrain.arcadeDrivePercent(rotation, 0.0);
    }

    /**
     * @param targetID the ID of the desired target
     * @return true if the yaw is below the tolerance, false if not
     */
    public boolean yawAligned(int targetID) {
        double checkYaw;
        if (hasTarget(targetID)){
            checkYaw = getTargetYaw(targetID);
            return Math.abs(checkYaw) <= Constants.Photon.PHOTON_YAW_TOLERANCE;
        }
        return false;
    }
}
