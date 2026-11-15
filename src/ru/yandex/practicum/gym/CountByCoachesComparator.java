package ru.yandex.practicum.gym;

import java.util.Comparator;
import java.util.Map;

public class CountByCoachesComparator implements Comparator<Map.Entry<Coach, Integer>>{

    @Override
    public int compare(Map.Entry<Coach, Integer> a, Map.Entry<Coach, Integer> b) {
        // сначала по количеству тренировок — по убыванию
        return Integer.compare(b.getValue(), a.getValue());
    }
}
