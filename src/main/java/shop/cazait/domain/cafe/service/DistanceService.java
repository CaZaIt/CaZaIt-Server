package shop.cazait.domain.cafe.service;

public class DistanceService {
    // 두 좌표 사이의 거리를 구하는 함수
    // distance(첫번쨰 좌표의 위도, 첫번째 좌표의 경도, 두번째 좌표의 위도, 두번째 좌표의 경도)
    public static int distance(String lati1, String long1, String lati2, String long2){
        double lat1 = Double.parseDouble(lati1);
        double lon1 = Double.parseDouble(long1);
        double lat2 = Double.parseDouble(lati2);
        double lon2 = Double.parseDouble(long2);

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515;

        int res = (int) (dist);

        return res; //단위 meter
    }

    //10진수를 radian(라디안)으로 변환
    private static double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }
    //radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }
}
