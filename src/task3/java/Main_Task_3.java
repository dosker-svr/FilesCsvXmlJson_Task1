
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main_Task_3 {
    public static void main(String[] args) {
        String jsonFile = "new_json_Task2.json";
        List<Employee> employeeList = jsonToList(jsonFile);
        printList(employeeList);
    }

    public static List<Employee> jsonToList(String jsonFile) {
        List<Employee> list = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))) {

            Object objectForArray = jsonParser.parse(bufferedReader);
            JSONArray employeeJsonArray = (JSONArray) objectForArray;

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            ///
            for (int i = 0; i < employeeJsonArray.size(); i++) {

                JSONObject employeeJsonObj = (JSONObject) employeeJsonArray.get(i);
                Employee employee = gson.fromJson(employeeJsonObj.toJSONString(), Employee.class);
                list.add(employee);
            }
            ///
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void printList(List<Employee> employeeList) {
        for (Employee emp : employeeList) {
            System.out.println(emp);
        }
    }

}
