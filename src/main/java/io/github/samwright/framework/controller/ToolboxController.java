package io.github.samwright.framework.controller;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * User: Sam Wright Date: 29/07/2013 Time: 17:42
 */
public class ToolboxController extends FlowPane {

    public ToolboxController() {
        HBox.setHgrow(this, Priority.NEVER);
        setPrefWidth(150);
//        setMinWidth(100);
    }

}
