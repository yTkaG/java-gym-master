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
        TreeMap<TimeOfDay, TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(singleTrainingSession,
                mondaySessions.get(new TimeOfDay(13, 0)));

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
        Assertions.assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());

        Assertions.assertEquals(
                thursdayChildTrainingSession,
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstEntry().getValue());

        Assertions.assertEquals(
                thursdayAdultTrainingSession,
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastEntry().getValue());

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
        Assertions.assertEquals(
                singleTrainingSession,
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0)));

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertNull(
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(14, 0)));
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeNonexistentDay() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession trainingSession = new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession);

        Assertions.assertNull(
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.TUESDAY, new TimeOfDay(13, 0)));
    }

    @Test
    void testAddTrainingSessionSameTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupChild = new Group(
                "Акробатика для детей", Age.CHILD, 60);

        Group groupAdult = new Group(
                "Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession firstTrainingSession = new TrainingSession(
                groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession secondTrainingSession = new TrainingSession(
                groupAdult, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        Assertions.assertEquals(
                1,
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        Assertions.assertEquals(
                secondTrainingSession,
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
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

        Assertions.assertEquals(
                1,
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        Assertions.assertEquals(
                1,
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());

        Assertions.assertEquals(
                mondaySession,
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0)));

        Assertions.assertEquals(
                tuesdaySession,
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.TUESDAY, new TimeOfDay(13, 0)));
    }
}
