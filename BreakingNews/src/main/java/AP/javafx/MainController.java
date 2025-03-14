package AP.javafx;

import AP.Infrastructure;
import AP.News;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    private Stage stage;
    private Scene scene;

    private Infrastructure infrastructure;

    private List<News> news;

    private final ToggleGroup toggleGroup;

    @FXML
    private VBox newsList;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblAuthor;

    public MainController(Stage stage) {
        this.stage = stage;
        this.toggleGroup = new ToggleGroup();
        infrastructure = new Infrastructure("09e4ae22c287412c898681c8170a7a68", "news.csv");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        news = infrastructure.getNewsList();

        newsList.getChildren().clear();

        for (News nw : news) {
            AnchorPane pane = new AnchorPane();

            Label number = new Label(String.valueOf(news.indexOf(nw) + 1));
            Label lblTitle = new Label(nw.title() == null ? " - " : nw.title());

            RadioButton radioButton = new RadioButton();
            radioButton.setToggleGroup(toggleGroup);
            radioButton.setId(String.valueOf(news.indexOf(nw)));

            toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                public void changed(ObservableValue<? extends Toggle> ob,
                                    Toggle o, Toggle n) {
                    RadioButton rb = (RadioButton)toggleGroup.getSelectedToggle();

                    if (rb != null) {
                        updateSelectedNews(news.get(Integer.parseInt(rb.getId())));
                    }
                }
            });

            pane.getChildren().addAll(number, lblTitle, radioButton);

            AnchorPane.setTopAnchor(number, 5d);
            AnchorPane.setTopAnchor(lblTitle, 5d);
            AnchorPane.setTopAnchor(radioButton, 5d);

            AnchorPane.setLeftAnchor(number, 5d);
            AnchorPane.setLeftAnchor(lblTitle, 50d);
            AnchorPane.setLeftAnchor(radioButton, 25d);

            newsList.getChildren().add(pane);
        }


    }

    public void refresh() {
        news = infrastructure.getAndRefreshNewsList();
    }

    public void updateSelectedNews(News nw) {

        String description = nw.description() == null ? "" : nw.description();

        if (description.length() > 2000){
            description = description.substring(0, 1000);
            description += " ...";
        }

        lblTitle.setText(nw.title() == null ? "-" : nw.title());
        lblAuthor.setText(nw.author() ==  null ? "-" : nw.author());
        lblDescription.setText(description);

    }
}





