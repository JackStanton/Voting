package trojan.xml;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import trojan.GUI.ChooseWindow;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class GenerateXML {
    public static void execute(File fileName) {
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("document");
            doc.appendChild(rootElement);
            String name;
            int lvl = 0;
            boolean signed = true;
            for (int i = 0; i < ChooseWindow.resultList.size(); i++) {
                boolean choice = ChooseWindow.resultList.get(0).getChoose();
                boolean result = choice;
                 if (lvl < ChooseWindow.resultList.get(i).getLevel()){
                     lvl = ChooseWindow.resultList.get(i).getLevel();
                     Element staff =  doc.createElement("Level");
                     rootElement.appendChild(staff);

                     Attr attr = doc.createAttribute("level");
                     attr.setValue(""+lvl);
                     staff.setAttributeNode(attr);

                     if (!(ChooseWindow.resultList.get(i).getMode().equals(""))){
                         attr = doc.createAttribute("mode");
                         attr.setValue(""+ChooseWindow.resultList.get(i).getMode());
                         staff.setAttributeNode(attr);
                     }


                     for (int j = 0; j < ChooseWindow.resultList.size(); j++) {
                         if (lvl == ChooseWindow.resultList.get(j).getLevel()){
                             name = ChooseWindow.resultList.get(j).getUsername();
                             choice = ChooseWindow.resultList.get(j).getChoose();
                             if (!(ChooseWindow.resultList.get(i).getMode().equals(""))){
                                 if (ChooseWindow.resultList.get(i).getMode().equals("or")){
                                     result = result || choice;
                                 }
                                 if (ChooseWindow.resultList.get(i).getMode().equals("and")){
                                     result = result && choice;
                                 }
                             } else result = ChooseWindow.resultList.get(i).getChoose();

                             Element firstname =  doc.createElement("user");
                             Element nameUser = doc.createElement("username");
                             nameUser.appendChild(doc.createTextNode(name));
                             firstname.appendChild(nameUser);
                             staff.appendChild(firstname);
                             Element userChoice =  doc.createElement("choice");
                             userChoice.appendChild(doc.createTextNode(""+choice));
                             firstname.appendChild(userChoice);
                         }

                     }
                     signed = signed && result;
                     attr = doc.createAttribute("result");
                     attr.setValue(""+result);
                     staff.setAttributeNode(attr);
                 }

                Attr attr = doc.createAttribute("signed");
                attr.setValue(""+signed);
                rootElement.setAttributeNode(attr);

            }



            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult resulFile =  new StreamResult(new File(String.valueOf(fileName)));
            transformer.transform(source, resulFile);

        }catch(ParserConfigurationException | TransformerException pce){
            pce.printStackTrace();
        }
    }
}
