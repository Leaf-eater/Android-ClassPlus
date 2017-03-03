package com.yuhang.classplus;

/**
 * Created by 宇航 on 2016/10/3.
 *
 */

public class Course {
    private String courseName;
    private String teacherName;
    private String pos;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {

        return "pos:" + getPos() + "CourseName:" + getCourseName() + "TeacherName:" + getTeacherName();
    }
}
