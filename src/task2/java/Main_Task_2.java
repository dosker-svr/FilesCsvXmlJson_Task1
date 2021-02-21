import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main_Task_2 {
    public static void main(String[] args) {
        String fileName = "data.xml";
        String newJsonFileName = "new_json_Task2.json";
        Main.writeStringToJSON(Main.listToStringForJSON(parseXML(fileName)), newJsonFileName);


    }

    public static List<Employee> parseXML(String fileName) {
        File file = new File(fileName);

        List<Employee> list = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // паттерн Фабрика
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file); //получаем экземпляр класса Document
            Node root = document.getDocumentElement(); //получаем корневой узел документа (в данном случае staff)
//            NodeList nodeList = root.getChildNodes(); // получаем список дочерних узлов узла root
            read(root, list);
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
//            NodeList elements = document.getDocumentElement().getElementsByTagName("employee");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void read(Node node, List<Employee> list) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (node_.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node_;
                if (element.getTagName().equals("employee")) {
                    Employee employee = new Employee();
                    NodeList listAttrib = element.getChildNodes();
                    for (int j = 1; j < listAttrib.getLength(); j = j + 2) {
                        String attrib = listAttrib.item(j).getTextContent();
                        switch (j) {
                            case 1:
                                employee.setId(Long.parseLong(attrib));
                                break;
                            case 3:
                                employee.setFirstName(attrib);
                                break;
                            case 5:
                                employee.setLastName(attrib);
                                break;
                            case 7:
                                employee.setCountry(attrib);
                                break;
                            case 9:
                                employee.setAge(Integer.parseInt(attrib));
                                break;
                        }
                    }
                    addEmployeeToList(list, employee);

                }
                read(node_, list);

            }
        }
    }

    public static void addEmployeeToList(List<Employee> list, Employee employee) {
        list.add(employee);
    }
}
