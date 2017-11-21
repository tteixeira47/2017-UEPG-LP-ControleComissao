/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.DAO.empresaRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CRUDEmpresaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private EmpresaController controllerPai;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField txtFldNome;

    @FXML
    private TextField txtFldFantasia;

    @FXML
    private TextField txtFldCnpj;

    @FXML
    private Button btnConfirma;

    @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
        controllerPai.tblView.requestFocus();
    }

    @FXML
    private void btnConfirmaClick() {
        controllerPai.empresa.setCnpj(txtFldCnpj.getText());
        controllerPai.empresa.setNome(txtFldNome.getText());
        controllerPai.empresa.setFantasia(txtFldFantasia.getText());

        try {
            switch (controllerPai.acao) {
                case ALTERAR:
                    empresaRepository.save(controllerPai.empresa);
                    break;
            }

            controllerPai.tblView.setItems(FXCollections.observableList(empresaRepository.findAll()));
            controllerPai.tblView.refresh();
            anchorPane.getScene().getWindow().hide();
            controllerPai.tblView.requestFocus();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Empresa");

            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("CNPJ ja cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
            txtFldCnpj.requestFocus();
        }

    }

    public void setControllerPai(EmpresaController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldCnpj.setText(controllerPai.empresa.getCnpj());
        txtFldNome.setText(controllerPai.empresa.getNome());
        txtFldFantasia.setText(controllerPai.empresa.getFantasia());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnConfirma.disableProperty().bind((txtFldCnpj.textProperty().isEmpty()).or(txtFldNome.textProperty().isEmpty()).or(txtFldFantasia.textProperty().isEmpty()));
    }

}
