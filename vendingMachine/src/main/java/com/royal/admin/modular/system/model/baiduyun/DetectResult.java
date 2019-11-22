package com.royal.admin.modular.system.model.baiduyun;

import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Data
public class DetectResult {

    private Integer face_num;

    private List<FaceInfo> face_list;


    @Data
    public static class FaceInfo {
        private Angle angle;

        private Location location;

        private String face_token;

        private Double face_probability;

        private Integer age;

        private Quality quality;

    }

    @Data
    public static class Angle {
        private Double roll;
        private Double pitch;
        private Double yaw;
    }

    @Data
    public static class Location {
        private Double top;
        private Double left;
        private Double width;
        private Double height;
        private Integer rotation;
    }

    @Data
    public static class Quality {
        private Double illumination;
        private Double blur;
        private Integer completeness;
        private Occlusion occlusion;
    }

    @Data
    public static class Occlusion {
        private Double right_eye;
        private Double nose;
        private Double mouth;
        private Double left_eye;
        private Double left_cheek;
        private Double chin_contour;
        private Double right_cheek;
    }

    public static DetectResult toDetectResult(JSONObject res) {
        JSONObject result = res.getJSONObject("result");
        JSONArray faceList = result.getJSONArray("face_list");
        JSONObject faceInfoJson = faceList.getJSONObject(0);
        JSONObject angleJson = faceInfoJson.getJSONObject("angle");
        JSONObject locationJson = faceInfoJson.getJSONObject("location");
        JSONObject qualityJson = faceInfoJson.getJSONObject("quality");
        JSONObject occlusionJson = qualityJson.getJSONObject("occlusion");

        Angle angle = new Angle();
        angle.setPitch(angleJson.getDouble("pitch"));
        angle.setRoll(angleJson.getDouble("roll"));
        angle.setYaw(angleJson.getDouble("yaw"));

        Location location = new Location();
        location.setHeight(locationJson.getDouble("height"));
        location.setLeft(locationJson.getDouble("left"));
        location.setRotation(locationJson.getInt("rotation"));
        location.setTop(locationJson.getDouble("top"));
        location.setWidth(locationJson.getDouble("width"));

        Occlusion occlusion = new Occlusion();
        occlusion.setChin_contour(occlusionJson.getDouble("chin_contour"));
        occlusion.setLeft_cheek(occlusionJson.getDouble("left_cheek"));
        occlusion.setLeft_eye(occlusionJson.getDouble("left_eye"));
        occlusion.setMouth(occlusionJson.getDouble("mouth"));
        occlusion.setNose(occlusionJson.getDouble("nose"));
        occlusion.setRight_cheek(occlusionJson.getDouble("right_cheek"));
        occlusion.setRight_eye(occlusionJson.getDouble("right_eye"));

        Quality quality = new Quality();
        quality.setBlur(qualityJson.getDouble("blur"));
        quality.setCompleteness(qualityJson.getInt("completeness"));
        quality.setIllumination(qualityJson.getDouble("illumination"));
        quality.setOcclusion(occlusion);

        List<FaceInfo> faceInfoList = new ArrayList<>();
        FaceInfo faceInfo = new FaceInfo();
        faceInfo.setAge(faceInfoJson.getInt("age"));
        faceInfo.setFace_probability(faceInfoJson.getDouble("face_probability"));
        faceInfo.setFace_token(faceInfoJson.getString("face_token"));

        faceInfo.setAngle(angle);
        faceInfo.setLocation(location);
        faceInfo.setQuality(quality);
        faceInfoList.add(faceInfo);

        DetectResult detectResult = new DetectResult();
        detectResult.setFace_num(result.getInt("face_num"));
        detectResult.setFace_list(faceInfoList);
        return detectResult;
    }

    public static boolean isQualified(BdyResult bdyResult) {
        if (bdyResult.getError_code() != 0 || !bdyResult.getError_msg().equals("SUCCESS")) {
            return false;
        }
        DetectResult detectResult = (DetectResult) bdyResult.getResult();
        Quality quality = detectResult.getFace_list().get(0).getQuality();
        if (quality.getBlur() >= 0.7 || quality.getCompleteness() != 1 || quality.getIllumination() <= 40) {
            return false;
        }
        Occlusion occlusion = quality.getOcclusion();
        if (occlusion.getChin_contour() >= 0.6 || occlusion.getLeft_cheek() >= 0.8 || occlusion.getLeft_eye() >= 0.6 || occlusion.getMouth() >= 0.7
                || occlusion.getNose() >= 0.7 || occlusion.getRight_cheek() >= 0.8 || occlusion.getRight_eye() >= 0.6) {
            return false;
        }
        Angle angle = detectResult.getFace_list().get(0).getAngle();
        if (angle.getPitch() <= -20 || angle.getPitch() >= 20 || angle.getRoll() <= -20 || angle.getRoll() >= 20 || angle.getYaw() <= -20 || angle.getYaw() >= 20) {
            return false;
        }
        Location location = detectResult.getFace_list().get(0).getLocation();
        if (location.getHeight() <= 100 || location.getWidth() <= 100) {
            return false;
        }
        return true;
    }

}
