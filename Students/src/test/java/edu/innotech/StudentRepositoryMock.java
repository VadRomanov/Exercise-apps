package edu.innotech;

public class StudentRepositoryMock implements StudentRepo {
    public int getRaitingForGradeSum(int sum) {
        return 10;
    }
    public long count() {return 0;}
    public void delete(Student entity) {}
    public void deleteAll(Iterable<Student> entities) {}
    public Iterable<Student> findAll() {return null;}
    public Student save(Student entity) {return null;}
    public Iterable<Student> saveAll(Iterable<Student> entities) {return null;}

}
