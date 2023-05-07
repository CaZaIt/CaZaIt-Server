package shop.cazait.domain.cafe.service;

public class DistanceService {

    private static final double EARTH_RADIUS = 6371e3; // 지구의 반지름 (m)

    // 두 좌표 사이의 거리를 구하는 함수
    public static int distance(String lati1, String long1, String lati2, String long2) {
        double lat1 = Double.parseDouble(lati1);
        double lon1 = Double.parseDouble(long1);
        double lat2 = Double.parseDouble(lati2);
        double lon2 = Double.parseDouble(long2);

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance =  EARTH_RADIUS * c;
        return (int)distance;
    }
}
