package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule =
                timetable.computeIfAbsent(day, k -> new TreeMap<>());

        List<TrainingSession> sessionsAtTime =
                daySchedule.computeIfAbsent(time, k -> new ArrayList<>());

        sessionsAtTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, List<TrainingSession>> dailySchedule =
                timetable.get(dayOfWeek);

        if (dailySchedule == null) {
            return null;
        }

        return dailySchedule.getOrDefault(timeOfDay, Collections.emptyList());
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach, Integer> countByCoaches = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> day : timetable.values()) {
            for (List<TrainingSession> sessions : day.values()) {
                for (TrainingSession session : sessions) {
                    if (countByCoaches.containsKey(session.getCoach())) {
                        countByCoaches.put(session.getCoach(), countByCoaches.get(session.getCoach()) + 1);
                    } else {
                        countByCoaches.put(session.getCoach(), 1);
                    }
                }
            }
        }

        List<Map.Entry<Coach, Integer>> sortedResult = new ArrayList<>(countByCoaches.entrySet());

        sortedResult.sort(new CountByCoachesComparator());
        return sortedResult;
    }
}
