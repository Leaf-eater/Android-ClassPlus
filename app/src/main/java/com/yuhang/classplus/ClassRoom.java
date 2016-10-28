package com.yuhang.classplus;

/**
 * Created by 宇航 on 2016/9/21.
 *
 */
public class ClassRoom {
    private String className;
    private String peopleNum;
    private Course[] Courses;

    public Course[] getCourses() {
        return Courses;
    }

    public void setCourses(Course[] courses) {
        Courses = courses;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getClassNmae() {
        return className;
    }

    public void setClassNmae(String className) {
        this.className = className;
    }
}
