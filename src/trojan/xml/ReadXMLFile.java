package trojan.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class ReadXMLFile {

    public static ArrayList<Users> list = new ArrayList<>();

    public static boolean execute(String filename) {
        try {

            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("staff");

            int level = 1;
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    for (int i = 0; i < eElement.getElementsByTagName("user").getLength(); i++) {
                        Users elem = new Users(level, eElement.getElementsByTagName("user").item(i).getTextContent(),eElement.getAttribute("mode"));
                        list.add(elem);
                    }
                    level++;
                }
            }


        } catch (Exception e) {
            return false;
        }
        if(list.size() > 0){
            return true;
        } else return false;
    }





}
