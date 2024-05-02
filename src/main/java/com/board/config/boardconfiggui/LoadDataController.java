package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.controllers.SaveFileController;
import com.invecas.CodeGenerator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoadDataController {

    @FXML
    private TextField xmlPathField;
    @FXML
    private TextField repoPathField;
    @FXML
    private TextField toolChainPathField;
    @FXML
    private TextField outputPathField;
    @FXML
    private Button submitBtn;

    private final HomeViewController homeViewController;

    public LoadDataController(HomeViewController homeViewController) {
        this.homeViewController = homeViewController;
    }

    @FXML
    public void initialize(){
        submitBtn.setDisable(true);
    }

    @FXML
    protected void onButtonClick(Event event) {
        Node sourceNode = (Node) event.getSource();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedFolder = directoryChooser.showDialog(null);
        if (selectedFolder != null) {
            switch (sourceNode.getUserData().toString()){
                case "xmlBtn":
                    xmlPathField.setText(selectedFolder.getAbsolutePath());
                    break;
                case "repoBtn":
                    repoPathField.setText(selectedFolder.getAbsolutePath());
                    break;
                case "toolChainBtn":
                    toolChainPathField.setText(selectedFolder.getAbsolutePath());
                    break;
                case "outputBtn":
                    outputPathField.setText(selectedFolder.getAbsolutePath());
                    break;
                default:
                    break;
            }
        }

        boolean allFields = !xmlPathField.getText().isEmpty() && !repoPathField.getText().isEmpty() && !toolChainPathField.getText().isEmpty();
        if (allFields){
            submitBtn.setDisable(false);
        }
    }

    public void onSubmit() {
        Map<String, Object> folderPaths = new LinkedHashMap<>();
        folderPaths.put("xml",xmlPathField.getText());
        folderPaths.put("repository",repoPathField.getText());
        folderPaths.put("toolchain",toolChainPathField.getText().replace("\\","/"));
        String outputPath = outputPathField.getText();
        if (!outputPath.isEmpty()){
            folderPaths.put("output",outputPath);
        }
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        try {
            CodeGenerator generator = new CodeGenerator(folderPaths);
            generator.initiate();
            successAlert.setTitle("Success Message");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Code generated successful!");
            successAlert.getDialogPane().getStyleClass().add("success-dialog");
            successAlert.showAndWait();

        }catch (Exception e){
            successAlert.setTitle("Failure Message");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Something went wrong..!" + e.getMessage());
            successAlert.showAndWait();
        }
    }

    public void onConfigureClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("save-file.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            SaveFileController controller = loader.getController();
            controller.setDialogStage(stage);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Board Name");
            stage.showAndWait();

            if (controller.isContinueClicked()) {
                String text = controller.getBoardName();
                homeViewController.onConfigureClick(xmlPathField.getText());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}