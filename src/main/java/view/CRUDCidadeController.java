/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.DELETAR;
import static config.Config.INCLUIR;
import static config.DAO.cidadeRepository;
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

public class CRUDCidadeController implements Initializable {

    private CidadeController controllerPai;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField txtFldNome;

    @FXML
    private Button btnConfirma;

    @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
        controllerPai.tblView.requestFocus();
    }

    @FXML
    private void btnConfirmaClick() {

        controllerPai.cidade.setNome(txtFldNome.getText());

        try {
            switch (controllerPai.acao) {
                case INCLUIR:
                    if (cidadeRepository.countByNome(controllerPai.cidade.getNome()) == 0) {
                        cidadeRepository.insert(controllerPai.cidade);
                        break;
                    }

                    System.out.println("Cidade já cadastrada");

                    break;

                case DELETAR:
                    if (empresaRepository.countByCidade(controllerPai.cidade) == 0) {
                        cidadeRepository.delete(controllerPai.cidade);
                        break;
                    }

                    System.out.println("Cidade está vinculada a uma empresa");

                    break;

                case ALTERAR:
                    cidadeRepository.save(controllerPai.cidade);

                    break;
            }

            controllerPai.tblView.setItems(FXCollections.observableList(cidadeRepository.findAll()));
            controllerPai.tblView.refresh();
            controllerPai.tblView.getSelectionModel().clearSelection();
            controllerPai.tblView.getSelectionModel().select(controllerPai.cidade);
            anchorPane.getScene().getWindow().hide();
            controllerPai.tblView.requestFocus();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Cidade");

            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("Nome ja cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
            txtFldNome.requestFocus();
        }

    }

    public void setControllerPai(CidadeController controllerPai) {

        this.controllerPai = controllerPai;
        txtFldNome.setText(controllerPai.cidade.getNome());
        txtFldNome.setDisable(controllerPai.acao == DELETAR);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnConfirma.disableProperty().bind(
                (txtFldNome.textProperty().isEmpty()));

    }

}
