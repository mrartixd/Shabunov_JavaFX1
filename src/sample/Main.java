package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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


            mytable.setItems(XmlPars.data);
            mytable.getColumns().addAll(numberCol,bookidCol,authorCol,titleCol,genreCol, priceCol, publishCol, descCol);

            VBox vbx = new VBox();
            vbx.setAlignment(Pos.CENTER);
            vbx.setSpacing(10);

            HBox hbx = new HBox();
            hbx.setAlignment(Pos.CENTER);
            hbx.setSpacing(10);

            final TextField id=new TextField();
            id.setPromptText("Enter 'Book id' ");

            FilteredList<Book> filteredData = new FilteredList<>(XmlPars.data, p -> true);
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
            primaryStage.setTitle("Books: "+XmlPars.doc.getDocumentElement().getNodeName());
            root.getChildren().add(vbx);

            primaryStage.setScene(new Scene(root, 1100, 500));
            primaryStage.show();



    }


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        XmlPars.printBook();
        launch(args);
    }
}
