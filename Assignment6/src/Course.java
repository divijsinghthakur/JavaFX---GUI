// Assignment #: Arizona State University Spring 2023 CSE205 #6
//         Name: Divij Singh Thakur	
//    StudentID: 1226929849
//      Lecture: TTh 10:30AM
//  Description: This file contains information about the course information that goes inside the course list. Its being aggregated by CoursePane.java.

//Note: when you submit on gradescope, you need to comment out the package line
//package yourPackageName;

public class Course
{
    private String subject;
    private int courseNum;
    private String instructor;

    public Course()
    {
        subject = "?";
        courseNum = 0;
        instructor = "?";
    }

    public Course(String subject, int courseNum, String instructor)
    {
        this.subject = subject;
        this.courseNum = courseNum;
        this.instructor = instructor;
    }

    public String getSubject()
    {
        return subject;
    }

    public int getCourseNum()
    {
        return courseNum;
    }

    public String getInstructor()
    {
        return instructor;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public void setCourseNum(int courseNum)
    {
        this.courseNum = courseNum;
    }

    public void setInstructor(String instructor)
    {
        this.instructor = instructor;
    }

    public String toString()
    {
        return	"\nCourse #:\t\t" + subject + " " + courseNum +
        		"\nInstructor:\t"+ instructor + "\n";
    }
}