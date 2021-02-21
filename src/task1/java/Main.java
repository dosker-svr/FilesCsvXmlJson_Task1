import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        String jsonFileName = "new_file.json";
        List<Employee> employeeList = null;
        String jsonElement = null;
        if ((employeeList = parseCSV(fileName, columnMapping)) != null) {
            if ((jsonElement =listToStringForJSON(employeeList)) != null) {
                writeStringToJSON(jsonElement, jsonFileName);
            }
        }

    }

    public static List<Employee> parseCSV(String fileName, String[] columnMapping) {
        File fileCSV = new File(fileName);
        List<Employee> list = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(fileCSV))) {

            // создаём класс определения порядка полей
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping); // определяем порядк расположения полей

            //CsvToBeanBuilder мы передаём "считывание файла csv", вызываем "стратегию" и строим в CsvToBean
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy).build();
            // Потом парсим в list получившийся CsvToBean
            list = csvToBean.parse();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String listToStringForJSON(List<Employee> employeeList) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Type listType = new TypeToken<List<Employee>>() {}.getType(); //получает тип дженерика
        String jsonElement = gson.toJson(employeeList, listType);
        return jsonElement;
    }

    public static void writeStringToJSON(String stringJSON, String jsonFileName) {
        JSONObject jsonObject = new JSONObject();

        try (FileWriter fileJSON = new FileWriter(jsonFileName)) {

            fileJSON.write(stringJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
