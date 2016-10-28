package ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuhang.classplus.ClassRoom;
import com.yuhang.classplus.Course;
import com.yuhang.classplus.HttpUtil;
import com.yuhang.classplus.MainActivity;
import com.yuhang.classplus.R;
import com.yuhang.classplus.User;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import widget.URL;

/**
 * Created by 宇航 on 2016/10/1.
 * class_info fragment
 * 教室查询，查询该教室的详细信息
 */

public class Fragment_ClassInfo extends Fragment{

    private ListView classInfo_item;
    private ListView classInfo_course;
    private ListView classInfo_teacherName;
    private TextView textView_className;
    private TextView textView_studentNum;
    private ArrayList<String> list = new ArrayList<>();
    private final String url = URL.URL_QUERY_CLASS; //查询教室
    ClassRoom classRoom = new ClassRoom();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_info, container,false);
        Bundle data = getArguments();   //从homepage页面传入的信息（名称，剩余人数，状态）
        init(rootView,data);
        User current_student = ((MainActivity) getActivity()).getUser();
        loadData(url,data.getString("className"));
        classInfo_item.setAdapter(getAdapter());    //设置课程序列号的信息
        return rootView;
    }

    //  加载时间列表项的adapter
    ArrayAdapter<String> getAdapter(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.text_view_class_time_table,list);
        list.add("1\n\n2");
        list.add("3\n\n4");
        list.add("5\n\n6");
        list.add("7\n\n8");
        list.add("9\n\n10");
        return arrayAdapter;
    }

    /**
     * 请求数据并加载课程表
     * @param url
     * @param classname
     */
    private void loadData(String url, final String classname) {
        RequestParams params = new RequestParams();
        params.add("no",classname);

//        提交get请求
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            Course[] originalCourses = classRoom.getCourses();
            Course[] sortedCourses;
            ArrayList<String> listOfCourse;
            ArrayList<String> listOfTeacherName;
//           将得到的数据（课程信息和教师信息）进行排序
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getActivity(), "get data success"+statusCode, Toast.LENGTH_SHORT).show();
                if (statusCode == 200) {
                    Log.i("classInfo", "get is success");
                    originalCourses = getCourses(response); //get the course array with length 5（一天5节课）
                    sortedCourses = sortCourses(originalCourses);   //sort the array,return sorted array of course
                    listOfCourse = getListOfCourseName(sortedCourses);
                    listOfTeacherName = getListOfTeacherName(sortedCourses);
                    ArrayAdapter<String> adapterOfCourse = new ArrayAdapter<>(getActivity(), R.layout.text_view_class_time_table, listOfCourse);
                    ArrayAdapter<String> adapterOfTeacher = new ArrayAdapter<>(getActivity(), R.layout.text_view_class_time_table, listOfTeacherName);
                    classInfo_course.setAdapter(adapterOfCourse);
                    classInfo_teacherName.setAdapter(adapterOfTeacher);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getActivity(), "get data failed"+statusCode, Toast.LENGTH_SHORT).show();
            }

            //            从排序好的course对象中获取排序好的教师名
            private ArrayList<String> getListOfTeacherName(Course[] sortedCourses) {
                ArrayList<String> listOfTeacherName = new ArrayList<>();
                for (Course course:sortedCourses) {
                    listOfTeacherName.add(course.getTeacherName());
                }
                return listOfTeacherName;
            }

//            从排序好的course对象中获取排序好的课程名
            private ArrayList<String> getListOfCourseName(Course[] sortedCourses) {
                ArrayList<String> listOfCourseName = new ArrayList<>();
                for (Course course:sortedCourses) {
                    listOfCourseName.add(course.getCourseName());
                }
                return listOfCourseName;
            }

//            Sort the original data of course 通过pos(一天中的那个时段有这节课)排序，返回按照上课顺序的course数组
            private Course[] sortCourses(Course[] courses) {
                Course[] sortedCourse = new Course[5];
                for (int i = 0; i < courses.length; i++) {
                    if (courses[i] != null) {

                        switch (courses[i].getPos()) {
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
                }
                for (int i = 0;i<sortedCourse.length;i++) {
                    if (sortedCourse[i] == null){
                        sortedCourse[i] = new Course();
                        sortedCourse[i].setTeacherName("null");
                        sortedCourse[i].setCourseName("null");
                    }
                }
                return sortedCourse;
            }

//            return a course with length 5 from response 将获得的信息存入course对象中
            private Course[] getCourses(JSONObject response) {
                Course[] courses = new Course[5];
                try {
                    JSONArray courseInfos = response.getJSONArray("courses");
                    for (int i = 0; i < courseInfos.length(); i++) {
                        JSONObject courseInfo = courseInfos.getJSONObject(i);
                        courses[i] = new Course();
                        courses[i].setPos(courseInfo.getString("pos"));
                        courses[i].setCourseName(courseInfo.getString("name"));
                        courses[i].setTeacherName(courseInfo.getString("teacher"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return courses;
            }
        });
    }

    private void init(View rootView, Bundle data) {
        classInfo_item = (ListView) rootView.findViewById(R.id.listView_classInfo_item);
        classInfo_course = (ListView) rootView.findViewById(R.id.listView_courseName);
        classInfo_teacherName = (ListView) rootView.findViewById(R.id.listView_teacherName);
        textView_className = (TextView) rootView.findViewById(R.id.textView_className);
        textView_className.setText(data.getString("className"));
        textView_studentNum = (TextView) rootView.findViewById(R.id.textView_realNum);
        textView_studentNum.setText("实际："+data.getString("studentNum"));
    }

}
