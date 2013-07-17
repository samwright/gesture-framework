package io.github.samwright.framework.controller.helper;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * User: Sam Wright Date: 17/07/2013 Time: 13:56
 */
public class WorkflowLinkController extends VBox {

    @FXML
    private Line line;

    @FXML
    private Polygon triangle;

    public WorkflowLinkController() {
        Controllers.bindViewToController("/fxml/WorkflowLink.fxml", this);
        line.endXProperty().bind(widthProperty());
        line.startYProperty().bind(heightProperty().divide(2));
        line.endYProperty().bind(heightProperty().divide(2));
    }

}
