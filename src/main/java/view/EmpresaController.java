package view;

import static config.Config.ALTERAR;
import static config.DAO.empresaRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Empresa;
import utilit.NossoPopOver;

public class EmpresaController implements Initializable {

    public char acao;
    public Empresa empresa;

    @FXML
    public TableView<Empresa> tblView;

    @FXML
    private TextField txtFldFiltroEmpresa;
    
    @FXML
    private MaterialDesignIconView btnAlterar;

    @FXML
    private void onKeyPressFiltrarEmpresa(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            tblView.setItems(FXCollections.observableList(empresaRepository.findByNomeLikeIgnoreCaseOrFantasiaIgnoreCase(txtFldFiltroEmpresa.getText(), txtFldFiltroEmpresa.getText())));
        }
    }
    
    @FXML
    private void acAlterar() {
        acao = ALTERAR;
        empresa = tblView.getSelectionModel().getSelectedItem();
        showCRUD();
    }

    private void showCRUD() {
        String scena = "/fxml/CRUDEmpresa.fxml";
        NossoPopOver popOver = null;
        switch(acao) {
            case ALTERAR:
                popOver = new NossoPopOver(scena, "Alterar Empresa", null);
                break;
        }
        CRUDEmpresaController controller = popOver.getLoader().getController();
        controller.setControllerPai(this);
    }
    
    @FXML
    public void acLimpaFiltroEmpresa() {
        txtFldFiltroEmpresa.clear();
        tblView.setItems(FXCollections.observableList(empresaRepository.findAll()));
        txtFldFiltroEmpresa.requestFocus();
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblView.setItems(FXCollections.observableList(empresaRepository.findAll()));
        btnAlterar.visibleProperty().bind(Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
    }
}
