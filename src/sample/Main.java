package sample;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
            ObservableList<Book> data = FXCollections.observableArrayList();

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


            final TableView mytable = new TableView();

            TableColumn numberCol = new TableColumn("#");
            TableColumn bookidCol = new TableColumn("Book ID");
            TableColumn authorCol = new TableColumn("Author");
            TableColumn titleCol = new TableColumn("Title");
            TableColumn genreCol = new TableColumn("Genre");
            TableColumn priceCol = new TableColumn("Price");
            TableColumn publishCol = new TableColumn("Publish-date");
            TableColumn descCol = new TableColumn("Description");



            numberCol.setCellValueFactory(new PropertyValueFactory<>("count"));
            bookidCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
            authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            publishCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            descCol.setCellValueFactory(new PropertyValueFactory<Book,String>("desc"));


            mytable.setItems(data);
            mytable.getColumns().addAll(numberCol,bookidCol,authorCol,titleCol,genreCol, priceCol, publishCol, descCol);

            VBox vbx = new VBox();
            vbx.setAlignment(Pos.CENTER);
            vbx.setSpacing(10);

            HBox hbx = new HBox();
            hbx.setAlignment(Pos.CENTER);
            hbx.setSpacing(10);

            final TextField id=new TextField();
            id.setPromptText("Enter 'Book id' ");

            FilteredList<Book> filteredData = new FilteredList<>(data, p -> true);
            id.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(myObject -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name field in your object with filter.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (String.valueOf(myObject.getBookId()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                        // Filter matches first name.

                    }
                    return false; // Does not match.
                });
            });

            SortedList<Book> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(mytable.comparatorProperty());
            // 5. Add sorted (and filtered) data to the table.
            mytable.setItems(sortedData);



            vbx.getChildren().addAll(mytable,hbx);
            hbx.getChildren().addAll(id);

            StackPane root = new StackPane();
            primaryStage.setTitle("Books: "+doc.getDocumentElement().getNodeName());
            root.getChildren().add(vbx);
            primaryStage.setScene(new Scene(root, 1100, 500));
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
