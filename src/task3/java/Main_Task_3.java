
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.*;

public class Main_Task_3 {
    public static void main(String[] args) {
        String jsonFile = "new_json_Task2.json";

    }

    public static void readString(String jsonFile) {
        String jsonFileRead = null;
        JSONParser jsonParser = new JSONParser();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))) {

            Object object = jsonParser.parse(new FileReader(jsonFile));
            JSONObject jsonObject = (JSONObject) object;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
