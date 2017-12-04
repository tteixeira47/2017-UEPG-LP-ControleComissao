package view;

import static config.Config.ALTERAR;
import static config.Config.DELETAR;
import static config.Config.INCLUIR;
import static config.DAO.cidadeRepository;
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
import model.Cidade;
import model.Empresa;
import utilit.NossoPopOver;

public class CidadeController implements Initializable {

    public char acao;
    public Cidade cidade;

    @FXML
    public TableView<Cidade> tblView;

    /*
    @FXML
    private TextField txtFldFiltroCidade;
     */
    @FXML
    private MaterialDesignIconView btnAlterar;

    @FXML
    private MaterialDesignIconView btnIncluir;

    @FXML
    private MaterialDesignIconView btnDeletar;

    /*
    @FXML
    private void onKeyPressFiltrarCidade(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            tblView.setItems(FXCollections.observableList(empresaRepository.findByNomeLikeIgnoreCaseOrFantasiaLikeIgnoreCase(txtFldFiltroEmpresa.getText(), txtFldFiltroEmpresa.getText())));
        }
    }
     */
    @FXML
    private void acAlterar() {
        acao = ALTERAR;
        cidade = tblView.getSelectionModel().getSelectedItem();
        if (!cidade.equals(null)){
            showCRUD();
        }        
    }
    
    @FXML
    public void acLimparCidade() {
        tblView.refresh();
        tblView.setItems(FXCollections.observableList(cidadeRepository.findAll()));
        tblView.requestFocus();
    }
    
    

    @FXML
    private void acIncluir() {
        acao = INCLUIR;
        cidade = new Cidade();
        showCRUD();
    }

    @FXML
    private void acDeletar() {
        acao = DELETAR;
        cidade = tblView.getSelectionModel().getSelectedItem();
        showCRUD();
    }

    private void showCRUD() {
        String scena = "/fxml/CRUDCidade.fxml";
        NossoPopOver popOver = null;
        switch (acao) {
            case ALTERAR:
                popOver = new NossoPopOver(scena, "Alterar Cidade", null);
                break;

            case INCLUIR:
                popOver = new NossoPopOver(scena, "Incluir Cidade", null);
                break;
            
            case DELETAR:
                popOver = new NossoPopOver(scena, "Deletar Cidade", null);
                break;
        }
        CRUDCidadeController controller = popOver.getLoader().getController();
        controller.setControllerPai(this);
    }

    /*
    @FXML
    public void acLimpaFiltroEmpresa() {
        txtFldFiltroEmpresa.clear();
        tblView.setItems(FXCollections.observableList(empresaRepository.findAll()));
        txtFldFiltroEmpresa.requestFocus();
    }
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblView.setItems(FXCollections.observableList(cidadeRepository.findAll()));
        tblView.refresh();
        btnAlterar.visibleProperty().bind(Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
        btnDeletar.visibleProperty().bind(Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
    }
}
