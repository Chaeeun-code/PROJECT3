package com.itcom.test1;

import java.util.HashMap;
import java.util.Map;

public class RegionCoordinates {

    // 좌표 데이터를 저장하는 Map
    private static final Map<String, double[]> regionCoordinates = new HashMap<>();

    // 정적 초기화 블록을 사용하여 좌표 데이터를 삽입
    static {
        regionCoordinates.put("서울", new double[]{37.5665, 126.9780});
        regionCoordinates.put("부산", new double[]{35.1796, 129.0756});
        regionCoordinates.put("대구", new double[]{35.8722, 128.6014});
        regionCoordinates.put("인천", new double[]{37.4563, 126.7052});
        regionCoordinates.put("광주", new double[]{35.1595, 126.8526});
        regionCoordinates.put("대전", new double[]{36.3504, 127.3845});
        regionCoordinates.put("울산", new double[]{35.5384, 129.3114});
        regionCoordinates.put("세종", new double[]{36.4801, 127.2890});
        regionCoordinates.put("경기", new double[]{37.4138, 127.5183});
        regionCoordinates.put("강원", new double[]{37.8228, 128.1555});
        regionCoordinates.put("충북", new double[]{36.6355, 127.4916});
        regionCoordinates.put("충남", new double[]{36.5184, 126.8006});
        regionCoordinates.put("전북", new double[]{35.7175, 127.1530});
        regionCoordinates.put("전남", new double[]{34.8679, 126.9910});
        regionCoordinates.put("경북", new double[]{36.5760, 128.5056});
        regionCoordinates.put("경남", new double[]{35.2596, 128.6647});
        regionCoordinates.put("제주", new double[]{33.4996, 126.5312});
    }

    // regionCoordinates 맵을 반환하는 메서드
    public static Map<String, double[]> getRegionCoordinates() {
        return regionCoordinates;
    }

    // 특정 지역의 좌표를 가져오는 메서드
    public static double[] getCoordinates(String regionName) {
        return regionCoordinates.get(regionName);
    }
}
