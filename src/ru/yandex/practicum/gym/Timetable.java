package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();;

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<>());
        }

        timetable.get(day).put(time, trainingSession);
    }

    public TreeMap<TimeOfDay, TrainingSession> getTrainingSessionsForDay(
            DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public TrainingSession getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, TrainingSession> dailySchedule =
                timetable.get(dayOfWeek);

        if (dailySchedule == null) {
            return null;
        }

        return dailySchedule.get(timeOfDay);
    }
}
