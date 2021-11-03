/*
 *@author Tevfik Kesici
 *@since 26.05.2021
 */

import java.util.ArrayList;

public class CourseGrade {
    public static void main(String[] args) {
    Department d = new Department("CSE","Computer Science");
    Teacher t = new Teacher("Tevfik Kesici","tkesici@windowslive.com",123L,d,5);
    Course c = new Course(d,101,"Computer Programming",4,t);
    Student s = new Student("Tevfik Kesici","tkesici@windowslive.com",123L,d);
    GradStudent gs = new GradStudent("Tevfik Kesici","tkesici@windowslive.com",123L,d,"thesis");
    Semester se = new Semester(1,2020);
    s.addCourse(c,se,0);
        System.out.println(s.listGrades(c));

    }
}

class Department {
    private String ID;
    private String name;
    private Teacher chair;

    public Department(String ID, String name) {
        if (ID.length() != 3 && ID.length() != 4) {
            throw new InvalidIDException(ID);
        }
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        if (ID.length() != 3 && ID.length() != 4) {
            throw new InvalidIDException(ID);
        }
        this.ID = ID;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getChair() {
        return this.chair;
    }

    public void setChair(Teacher chair) {
        Department department = new Department("CSE", "Engineering");
        Teacher x = new Teacher("Tevfi", "" +
                "tkesici@windowslive.com", 5001, department, 4);
        this.chair = null;
    }
}

class Course {
    private Department department;
    private Teacher teacher;
    private int number;
    private String title;
    private int AKTS;

    public Course(Department department,
                  int number, String title, int AKTS, Teacher teacher) {
        if (teacher.getDepartment() != department) {
            throw new DepartmentMismatchException(this, teacher);
        }
        if (!((number > 100 && number < 500) ||
                (number > 5000 && number < 6000) ||
                (number > 7000 && number < 8000))) {
            throw new InvalidNumberException(number);
        }
        if (AKTS <= 0) {
            throw new InvalidAKTSException(AKTS);
        }

        this.department = department;
        this.teacher = teacher;
        this.number = number;
        this.title = title;
        this.AKTS = AKTS;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public void setTeacher(Teacher teacher) {
        if (teacher.getDepartment() != this.department) {
            throw new DepartmentMismatchException(this, teacher);
        }
        this.teacher = teacher;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        if (!((number > 100 && number < 500) ||
                (number > 5000 && number < 6000) ||
                (number > 7000 && number < 8000))) {
            throw new InvalidNumberException(number);
        }
        this.number = number;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAKTS() {
        return this.AKTS;
    }

    public void setAKTS(int AKTS) {
        if (AKTS <= 0) {
            throw new InvalidAKTSException(AKTS);
        }
        this.AKTS = AKTS;
    }

    public String courseCode() {
        return this.department.getID() + " " + this.number;
    }

    public String toString() {
        return this.department.getID() + " " + this.number + " - "
                + this.title + " (" + this.AKTS + ")";
    }
}

abstract class Person {

    public boolean emailValidation(String email) {
        String mail = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+" +
                "@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\." +
                "[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(mail);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private String name;
    private String email;
    private long ID;
    private Department department;

    public Person(String name, String email, long ID, Department department) {
        if (!emailValidation(email)) {
            throw new InvalidEmailException(email);
        }
        this.email = email;
        this.name = name;
        this.ID = ID;
        this.department = department;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        if (!emailValidation(email)) {
            throw new InvalidEmailException(email);
        }
        this.email = email;
    }

    public long getID() {
        return this.ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

class Teacher extends Person {

    private int rank;

    public Teacher(String name, String email, long ID,
                   Department department, int rank) {
        super(name, email, ID, department);
        if (!(rank == 1 || rank == 2 || rank == 3 || rank == 4 || rank == 5)) {
            throw new InvalidRankException(rank);
        }
        this.rank = rank;
    }

    @Override
    public void setDepartment(Department department) {
        if (this.toString() != null || department.getChair() == this) {
            department.setChair(null);
        }
        System.out.println(this);
    }

    public int getRank() {
        return this.rank;
    }

    public String getTitle() {
        return switch (rank) {
            case 1 -> "Adjunct Instructor";
            case 2 -> "Lecturer";
            case 3 -> "Assistant Professor";
            case 4 -> "Associate Professor";
            case 5 -> "Professor";
            default -> throw new RuntimeException();
        };
    }

    public void promote() {
        if (rank == 1 || rank == 2 || rank == 3 || rank == 4) {
            rank++;
        } else throw new InvalidRankException(rank);
    }

    public void demote() {
        if (rank == 2 || rank == 3 || rank == 4 || rank == 5) {
            rank--;
        } else throw new InvalidRankException(rank);
    }

    public String toString() {
        return getTitle() + " " + getName() + " (" + getID() + ") " +
                "- " + getEmail();
    }
}

class Student extends Person {
    protected ArrayList<Course> courses = new ArrayList<>();
    protected ArrayList<Semester> semesters = new ArrayList<>();
    protected ArrayList<Double> grades = new ArrayList<>();


    public Student(String name, String email, long ID,
                   Department department) {
        super(name, email, ID, department);
    }


    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<Semester> getSemesters() {
        return this.semesters;
    }

    public void setSemesters(ArrayList<Semester> semesters) {
        this.semesters = semesters;
    }

    public ArrayList<Double> getGrades() {
        return this.grades;
    }

    public void setGrades(ArrayList<Double> grades) {
        this.grades = grades;
    }

    public void addCourse(Course course, Semester semester, double grade) {

        if (grade < 0 || grade > 100) {
            throw new InvalidGradeException(grade);
        } else {
            grades.add(grade);
            courses.add(course);
            semesters.add(semester);

        }

    }

    public int getAKTS() {
        int akts = 0;
        ArrayList<Course> counted = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            if (grades.get(i) >= 60 && (!(counted.contains(courses.get(i))))) {
                akts += courses.get(i).getAKTS();
                counted.add(courses.get(i));

            }
        }
        return akts;
    }

    public int getAttemptedAKTS() {
        int akts = 0;
        ArrayList<Course> counted = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            if ((!(counted.contains(courses.get(i))))) {
                akts += courses.get(i).getAKTS();
                counted.add(courses.get(i));

            }
        }
        return akts;
    }

    public double gradeToGPA(double grade) {
        if (grade >= 88) return 4.0;
        else if (grade >= 81) return 3.5;
        else if (grade >= 74) return 3.0;
        else if (grade >= 67) return 2.5;
        else if (grade >= 60) return 2.0;
        else if (grade >= 53) return 1.5;
        else if (grade >= 46) return 1.0;
        else if (grade >= 35) return 0.5;
        else return 0.0;
    }

    public double courseGPAPoints(Course course) {
        ArrayList<Double> threegrades = new ArrayList<>();
        double grade = 0;
        if (!(courses.contains(course))) {
            throw new CourseNotFoundException(this, course);
        }
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i) == course) {
                threegrades.add(grades.get(i));
            }
        }

        for (int i = 0; i < threegrades.size(); i++) {
            if (threegrades.get(i) > grade) {
                grade = threegrades.get(i);
            }
        }
        if (grade >= 88) return 4.0;
        else if (grade >= 81) return 3.5;
        else if (grade >= 74) return 3.0;
        else if (grade >= 67) return 2.5;
        else if (grade >= 60) return 2.0;
        else if (grade >= 53) return 1.5;
        else if (grade >= 46) return 1.0;
        else if (grade >= 35) return 0.5;
        else return 0.0;
    }

    public String courseGradeLetter(Course course) {
        ArrayList<Double> threegrades = new ArrayList<>();
        double grade = 0;
        if (!(courses.contains(course))) {
            throw new CourseNotFoundException(this, course);
        }
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i) == course) {
                threegrades.add(grades.get(i));
            }
        }

        for (int i = 0; i < threegrades.size(); i++) {
            if (threegrades.get(i) > grade) {
                grade = threegrades.get(i);
            }
        }
        if (grade >= 88) return "AA";
        else if (grade >= 81) return "BA";
        else if (grade >= 74) return "BB";
        else if (grade >= 67) return "CB";
        else if (grade >= 60) return "CC";
        else if (grade >= 53) return "DC";
        else if (grade >= 46) return "DD";
        else if (grade >= 35) return "FD";
        else return "FF";
    }

    public String courseResult(Course course) {
        ArrayList<Double> threegrades = new ArrayList<>();
        double grade = 0;
        if (!(courses.contains(course))) {
            throw new CourseNotFoundException(this, course);
        }
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i) == course) {
                threegrades.add(grades.get(i));
            }
        }

        for (int i = 0; i < threegrades.size(); i++) {
            if (threegrades.get(i) > grade) {
                grade = threegrades.get(i);
            }
        }
        if (grade >= 60) return "Passed";
        else if (grade >= 53) return "Conditionally Passed";
        else return "Failed";
    }

    public String listGrades(Semester semester) {
        ArrayList<Course> memory = new ArrayList<>();

        String ret = "";
        for (int i = 0; i < semesters.size(); i++) {
            if ((!memory.contains(courses.get(i)))) {
                ret += courses.get(i).courseCode() + " - " +
                        courseGradeLetter(courses.get(i)) + "\n";
                memory.add(courses.get(i));

            }

        }

        return ret;
    }

    public String listGrades(Course course) {
        ArrayList<Course> memory = new ArrayList<>();

        String ret = "";
        for (int i = 0; i < courses.size(); i++) {
            if ((!memory.contains(courses.get(i)))) {
                ret += semesters.get(i) + " - " +
                        courseGradeLetter(courses.get(i)) + "\n";
                memory.add(courses.get(i));

            }

        }
        if (!(courses.contains(course))) {
            throw new CourseNotFoundException(this,course);
        }
        return ret;
    }

    public String transcript() {
        ArrayList<Course> memory = new ArrayList<>();
        String ret = "";
        for (int i = 0; i < courses.size(); i++) {
            if ((!memory.contains(courses.get(i)))) {
                ret += semesters.get(i) + "\n   " +
                        courses.get(i).courseCode() + " - " +
                        courseGradeLetter(courses.get(i)) + "\n" + "GPA: - "
                        + courseGPAPoints(courses.get(i)) + "\n";
                memory.add(courses.get(i));

            }

        }
        ret+= "\nOverall GPA: " + getGPA();
        return ret;
    }


    public double getGPA() {
        double gpa = 0;
        for (int i = 0; i < grades.toArray().length; i++) {
            gpa += (gradeToGPA(grades.get(i)) * courses.get(i).getAKTS());
        }
        return (gpa / getAttemptedAKTS());
    }

    public String toString() {
        return super.getName() + " (" + super.getID() + ") - " +
                super.getEmail() +  " - GPA: " + getGPA();
    }
}

class GradStudent extends Student {
    private String thesis;
    ArrayList<GradStudent> assistants = new ArrayList<>();

    public GradStudent(String name, String email, long ID,
                       Department department, String thesis) {
        super(name, email, ID, department);
        this.thesis = thesis;
    }

    public String getThesis() {
        return this.thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }

    @Override
    public double courseGPAPoints(Course course) {
        if (!(courses.contains(course))) {
            throw new CourseNotFoundException(this, course);
        }
        double grade = grades.get(courses.indexOf(course));
        if (grade >= 90) return 4.0;
        else if (grade >= 85) return 3.5;
        else if (grade >= 80) return 3.0;
        else if (grade >= 75) return 2.5;
        else if (grade >= 70) return 2.0;
        else return 0.0;
    }

    @Override
    public String courseGradeLetter(Course course) {
        if (!(courses.contains(course))) {
            throw new CourseNotFoundException(this, course);
        }
        double grade = grades.get(courses.indexOf(course));
        if (grade >= 90) return "AA";
        else if (grade >= 85) return "BA";
        else if (grade >= 80) return "BB";
        else if (grade >= 75) return "CB";
        else if (grade >= 70) return "CC";
        else return "FF";
    }

    @Override
    public String courseResult(Course course) {
        if (!(courses.contains(course))) {
            throw new CourseNotFoundException(this, course);
        }
        double grade = grades.get(courses.indexOf(course));
        if (grade >= 70) return "Passed";
        else return "Failed";
    }

    public void setTeachingAssistant(Course course) {
        for (int i = 0; i < courses.size(); i++) {
            if (this.getGrades().get(courses.indexOf(course)) > 80) {
                assistants.set(courses.indexOf(course), this);
            } else {
                throw new CourseNotFoundException(this,course);
            }
        }

    }

    public Course getTeachingAssistant() {
        if (assistants.contains(this)) {
            return courses.get(assistants.indexOf(this));
        } else throw new RuntimeException();

    }
}

final class Semester {
    private final int season;
    private final int year;

    public Semester(int season, int year) {
        if (!(season == 1 || season == 2 || season == 3)) {
            throw new RuntimeException();
        }
        this.season = season;
        this.year = year;
    }

    public String getSeason() {
        if (this.season == 1) {
            return "Fall";
        } else if (this.season == 2) {
            return "Spring";
        } else if (this.season == 3) {
            return "Summer";
        } else throw new RuntimeException();
    }

    public int getYear() {
        return this.year;
    }

    public String toString() {
        return getSeason() + " – " + this.year;
    }
}

class CourseNotFoundException extends RuntimeException {
    private Student student;
    private Course course;

    public CourseNotFoundException(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public String toString() {
        return "CourseNotFoundException: " + student.getName() +
                " has not yet taken " + course.getTitle() + " "
                + course.getNumber();
    }
}

class DepartmentMismatchException extends RuntimeException {
    private Department department;
    private Teacher person;
    private Course course;


    public DepartmentMismatchException(Course course, Teacher person) {
        this.department = null;
        this.person = person;
        this.course = course;
    }

    public DepartmentMismatchException(Department department, Teacher person) {
        this.course = null;
        this.person = person;
        this.department = department;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Teacher getPerson() {
        return this.person;
    }

    public void setPerson(Teacher person) {
        this.person = person;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String toString() {
        if (this.course == null) {
            return "DepartmentMismatchException: " + this.person.getName() +
                    "(" + this.person.getID() + ") cannot be chair of " +
                    this.department.getID() +
                    " because he/she is currently assigned to " +
                    this.course.courseCode();
        } else return "DepartmentMismatchException: " + this.person.getName()
                + "(" + this.person.getID() + ") cannot teach "
                + this.course.getNumber() +
                " because he/she is currently assigned to "
                + this.course.courseCode();
    }
}

class InvalidGradeException extends RuntimeException {
    private double grade;

    public InvalidGradeException(double grade) {
        this.grade = grade;
    }

    public String toString() {
        return "InvalidGradeException: " + this.grade;
    }
}

class InvalidRankException extends RuntimeException {
    private int rank;

    public InvalidRankException(int rank) {
        this.rank = rank;
    }

    public String toString() {
        return "InvalidRankException: " + this.rank;
    }
}

class InvalidEmailException extends RuntimeException {
    private String email;

    public InvalidEmailException(String email) {
        this.email = email;
    }

    public String toString() {
        return "InvalidEmailException: " + this.email;
    }
}

class InvalidIDException extends RuntimeException {
    private String ID;

    public InvalidIDException(String ID) {
        this.ID = ID;
    }

    public String toString() {
        return "InvalidIDException: " + this.ID;
    }
}

class InvalidNumberException extends RuntimeException {
    private int number;

    public InvalidNumberException(int number) {
        this.number = number;
    }

    public String toString() {
        return "InvalidNumberException: " + this.number;

    }

}

class InvalidAKTSException extends RuntimeException {
    private int number;

    public InvalidAKTSException(int number) {
        this.number = number;
    }

    public String toString() {
        return "InvalidAKTSException: " + this.number;

    }

}

class SemesterNotFoundException extends RuntimeException {
    private Student student;
    private Semester semester;

    public SemesterNotFoundException(Student student, Semester semester) {
        this.student = student;
        this.semester = semester;
    }

    public String toString() {
        return "SemesterNotFoundException: " + this.student.getID() + " has" +
                " not taken any courses in " + this.semester;
    }
}