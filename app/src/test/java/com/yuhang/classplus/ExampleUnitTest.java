package com.yuhang.classplus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import cn.duan.DataProcessor;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println("hello");
        ArrayList<String> listOfCourse;
        ArrayList<String> listOfTeacherName;
        Course[] originalCourses;
        Course[] sortedCourses;
        JSONObject response = getresponse();
        originalCourses = getCourses(response); //get the course array with length 5
        sortedCourses = sortCourses(originalCourses);   //sort the array,return sorted array of course
        System.out.println(getListOfCourseName(sortedCourses).toString());
        System.out.println(getListOfTeacherName(sortedCourses).toString());
    }

    private ArrayList<String> getListOfTeacherName(Course[] sortedCourses) {
        ArrayList<String> listOfTeacherName = new ArrayList<String>();
        for (Course course:sortedCourses) {
            listOfTeacherName.add(course.getTeacherName());
        }
        return listOfTeacherName;
    }

    private ArrayList<String> getListOfCourseName(Course[] sortedCourses) {
        ArrayList<String> listOfCourseName = new ArrayList<>();
        for (Course course:sortedCourses) {
            listOfCourseName.add(course.getCourseName());
        }
        return listOfCourseName;
    }

    // Sort the original data of course
    private Course[] sortCourses(Course[] courses) {
        Course[] sortedCourse = new Course[5];
        for (int i = 0; i < courses.length; i++) {
            switch (courses[i].getPos()){
                case "1":
                    sortedCourse[0] = courses[i];
                    break;
                case "2":
                    sortedCourse[1] = courses[i];
                    break;
                case "3":
                    sortedCourse[2] = courses[i];
                    break;
                case "4":
                    sortedCourse[3] = courses[i];
                    break;
                case "5":
                    sortedCourse[4] = courses[i];
                    break;
                default:
                    sortedCourse[i] = null;
            }
        }
        return sortedCourse;
    }

    // return a course with length 5 from response
    private Course[] getCourses(JSONObject response) {
        Course[] courses = new Course[5];
        try {

            JSONArray courseInfos= response.getJSONArray("courses");
            for (int i = 0; i < 5; i++) {
                courses[i] = new Course();
                if (courseInfos.getJSONObject(i) == null) {
                    courses[i].setPos("");
                    courses[i].setTeacherName("");
                    courses[i].setCourseName("");
                }else {
                    JSONObject courseInfo = courseInfos.getJSONObject(i);
                    courses[i].setPos(courseInfo.getString("pos"));
                    courses[i].setCourseName(courseInfo.getString("name"));
                    courses[i].setTeacherName(courseInfo.getString("teacher"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private JSONObject getresponse() throws JSONException {
        return new JSONObject(DataProcessor.getData("G103"));
    }

}