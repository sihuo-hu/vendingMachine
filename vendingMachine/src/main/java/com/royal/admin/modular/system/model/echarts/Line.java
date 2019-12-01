package com.royal.admin.modular.system.model.echarts;

import com.royal.admin.core.util.DateUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Line {
    private List<String> date = new ArrayList<>();

    private List<Long> number = new ArrayList<>();

    private String startValue;

    public Line(List<Map<String, Object>> maps) {
        int i = 0;
        for (Map<String, Object> map : maps) {
            date.add((String) map.get("date"));
            number.add((Long) map.get("number"));
            i++;
            if (maps.size() > 100) {
                if (i == maps.size()-100) {
                    startValue = (String) map.get("date");
                }
            } else {
                if (i == 1) {
                    startValue = (String) map.get("date");
                }
            }
        }

    }
}
