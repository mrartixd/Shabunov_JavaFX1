package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application {


    private static final String FILENAME = "books.xml";

    @Override
    public void start(Stage primaryStage) {
        try {
            final File xmlFile = new File(System.getProperty("user.dir")+File.separator + FILENAME);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("book");
            ObservableList<Book> data = null;

            for (int i = 0; i < nodeList.getLength(); i++) {
                // Выводим информацию по каждому из найденных элементов
                Node node = nodeList.item(i);

                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    data = FXCollections.observableArrayList(
                            new Book(element.getAttribute("id"),
                                    element.getElementsByTagName("author").item(0).getTextContent(),
                                    element.getElementsByTagName("title").item(0).getTextContent(),
                                    element.getElementsByTagName("genre").item(0).getTextContent(),
                                    element.getElementsByTagName("price").item(0).getTextContent(),
                                    element.getElementsByTagName("publish_date").item(0).getTextContent(),
                                    element.getElementsByTagName("description").item(0).getTextContent()),
                    new Book("1","test","her","lel","22","12","heheheh")
                    );

                }
            }


            final TableView mytable = new TableView();

            TableColumn authorCol = new TableColumn("Author");
            TableColumn titleCol = new TableColumn("Title");
            TableColumn genreCol = new TableColumn("Genre");
            TableColumn priceCol = new TableColumn("Price");
            TableColumn publishCol = new TableColumn("Publish-date");
            TableColumn descCol = new TableColumn("Description");


            authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            publishCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("des"));


            mytable.setItems(data);
            mytable.getColumns().addAll(authorCol,titleCol,genreCol, priceCol, publishCol, descCol);



            VBox vbx = new VBox();
            vbx.setAlignment(Pos.CENTER);
            vbx.setSpacing(10);

            HBox hbx = new HBox();
            hbx.setAlignment(Pos.CENTER);
            hbx.setSpacing(10);

            vbx.getChildren().addAll(mytable,hbx);

            StackPane root = new StackPane();
            primaryStage.setTitle("Books: "+doc.getDocumentElement().getNodeName());
            root.getChildren().add(vbx);
            primaryStage.setScene(new Scene(root, 592, 425));
            primaryStage.show();


        } catch (ParserConfigurationException | SAXException
                | IOException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
