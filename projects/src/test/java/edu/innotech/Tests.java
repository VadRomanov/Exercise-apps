package edu.innotech;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class Tests {
    @RepeatedTest(value = 4, name = "Корректные оценки добавляются в список {currentRepetition}")
    public void markInRange(RepetitionInfo repetitionInfo) {
        Student stud = new Student("vasia");
        int num = repetitionInfo.getCurrentRepetition() + 1;
        stud.addMark(num);
        Assertions.assertEquals(stud.getMarks().get(0),num);
    }

    @ParameterizedTest(name="Добавление неверных оценок кидает исключение")
    @MethodSource(value = "edu.innotech.MarksGenerator#ints")
    public void markNotInRange(int x) {
        Student stud = new Student("vasia");
        Assertions.assertThrows(IllegalArgumentException.class, () -> stud.addMark(x));
    }

    @Test
    public void checkIncapsulation() {
        Student stud = new Student("vasia");
        Assertions.assertThrows(Exception.class, () -> stud.getMarks().add(999));
    }

    @Test
    public void checkHashCode() {
        Student stud = new Student("vasia");
        int x = stud.hashCode();
        int x1 = stud.hashCode();
        Assertions.assertEquals(x, x1);
    }

    @Test
    public void checkToString() {
        Student stud = new Student("vasia");
        stud.addMark(5);
        stud.addMark(3);
        String str = "Student(name=vasia, marks=[5, 3])";
        Assertions.assertEquals(str, stud.toString());
    }
}