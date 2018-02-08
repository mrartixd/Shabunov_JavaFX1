/**
 * RDIR61 Artur Shabunov
 */
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlPars {
    public static final String FILENAME = "books.xml";
    public static final File xmlParse = new File(System.getProperty("user.dir")+File.separator + FILENAME);
    public static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    public static DocumentBuilder db;
    public static Document doc;
    public static ObservableList<Book> data = FXCollections.observableArrayList();

    public static void printBook() throws ParserConfigurationException, SAXException, IOException {
        db = dbf.newDocumentBuilder();
        doc = db.parse(xmlParse);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("book");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                data.add(new Book(
                        i+1,
                        element.getAttribute("id"),
                        element.getElementsByTagName("author").item(0).getTextContent(),
                        element.getElementsByTagName("title").item(0).getTextContent(),
                        element.getElementsByTagName("genre").item(0).getTextContent(),
                        element.getElementsByTagName("price").item(0).getTextContent(),
                        element.getElementsByTagName("publish_date").item(0).getTextContent(),
                        element.getElementsByTagName("description").item(0).getTextContent()));
            }
        }
    }

}
