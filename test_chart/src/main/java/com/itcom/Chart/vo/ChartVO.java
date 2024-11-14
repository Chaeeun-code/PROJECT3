package com.itcom.Chart.vo;

public class ChartVO {
    private String thing_id;
    private String name;
    private int click_count;

    // Getter와 Setter 메서드들
    public String getThingId() {
        return thing_id;
    }

    public void setThingId(String thing_id) {
        this.thing_id = thing_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClickCount() {
        return click_count;
    }

    public void setClickCount(int click_count) {
    	
    
        this.click_count = click_count;
    }

    // toString 메서드 (디버깅 시 사용하기 좋음)
    @Override
    public String toString() {
        return "ChartVO{" +
                "thingId='" + thing_id + '\'' +
                ", name='" + name + '\'' +
                ", clickCount=" + click_count +
                '}';
    }
}
