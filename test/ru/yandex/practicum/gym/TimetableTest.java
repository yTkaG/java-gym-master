package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(1, mondaySessions.size());

        //Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1,
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        List<TimeOfDay> times = new ArrayList<>(thursdaySessions.keySet());
        Assertions.assertEquals(List.of(new TimeOfDay(13, 0), new TimeOfDay(20, 0)), times);

        // Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> sessionsAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, sessionsAt13.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> sessionsAt14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertTrue(sessionsAt14.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeNonexistentDay() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession trainingSession = new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession);

        //Проверить, что за вторник в 13:00 не вернулось занятий
        Assertions.assertEquals(Collections.emptyList(), timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0)));
    }

    @Test
    void testAddTrainingSessionSameTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession firstTrainingSession = new TrainingSession(
                groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(
                groupAdult, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось два занятия
        List<TrainingSession> sessionsByDayAndTime = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(2, sessionsByDayAndTime.size());
    }

    @Test
    void testAddTrainingSessionSameTimeOnDifferentDays() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession mondaySession = new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession tuesdaySession = new TrainingSession(
                group, coach, DayOfWeek.TUESDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(mondaySession);
        timetable.addNewTrainingSession(tuesdaySession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1,
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        //Проверить, что за вторник вернулось одно занятие
        Assertions.assertEquals(1,
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession firstSession = new TrainingSession(
                groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondSession = new TrainingSession(
                groupAdult, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thirdSession = new TrainingSession(
                groupChild, coach, DayOfWeek.TUESDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(firstSession);
        timetable.addNewTrainingSession(secondSession);
        timetable.addNewTrainingSession(thirdSession);

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();

        //Проверить, что вернулся один тренер
        Assertions.assertEquals(1, result.size());

        //Проверить, что у тренера три тренировки
        Assertions.assertEquals(coach, result.get(0).getKey());
        Assertions.assertEquals(3, result.get(0).getValue());
    }

    @Test
    void testGetCountByCoachesMultipleCoachesSortedByCountDescending() {
        Timetable timetable = new Timetable();

        Coach firstCoach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach secondCoach = new Coach("Петров", "Иван", "Алексеевич");
        Coach thirdCoach = new Coach("Сидоров", "Максим", "Олегович");

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(
                group, firstCoach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(
                group, firstCoach, DayOfWeek.TUESDAY, new TimeOfDay(14, 0)));
        timetable.addNewTrainingSession(new TrainingSession(
                group, firstCoach, DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0)));

        timetable.addNewTrainingSession(new TrainingSession(
                group, secondCoach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(
                group, secondCoach, DayOfWeek.THURSDAY, new TimeOfDay(20, 0)));

        timetable.addNewTrainingSession(new TrainingSession(
                group, thirdCoach, DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();

        //Проверить, что вернулось три тренера
        Assertions.assertEquals(3, result.size());

        //Проверить, что тренеры отсортированы по количеству тренировок по убыванию
        Assertions.assertEquals(firstCoach, result.get(0).getKey());
        Assertions.assertEquals(3, result.get(0).getValue());

        Assertions.assertEquals(secondCoach, result.get(1).getKey());
        Assertions.assertEquals(2, result.get(1).getValue());

        Assertions.assertEquals(thirdCoach, result.get(2).getKey());
        Assertions.assertEquals(1, result.get(2).getValue());
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();

        //Проверить, что для пустого расписания вернулся пустой список
        Assertions.assertTrue(result.isEmpty());
    }
}
