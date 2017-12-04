package view;

import static config.Config.ALTERAR;
import static config.Config.i18n;
import static config.DAO.empresaRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Cidade;
import model.Empresa;
import repository.EmpresaRepository;
import utilit.NossoPopOver;

public class EmpresaController implements Initializable {

    public char acao;
    public Empresa empresa;

    @FXML
    public TableView<Empresa> tblView;

    @FXML
    public Label lblRandNum;

    @FXML
    private TextField txtFldFiltroEmpresa;

    @FXML
    private MaterialDesignIconView btnAlterar;

    @FXML
    private MaterialDesignIconView btnInfoCidade;
    
    @FXML
    private MaterialDesignIconView btnFiltroCnpj;
    
    @FXML
    private void onKeyPressFiltrarEmpresa(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            tblView.setItems(FXCollections.observableList(empresaRepository.findByNomeLikeIgnoreCaseOrFantasiaLikeIgnoreCase(txtFldFiltroEmpresa.getText(), txtFldFiltroEmpresa.getText())));
        }
    }

    @FXML
    private void acFiltroCnpj() {
            tblView.setItems(FXCollections.observableList(empresaRepository.findByCnpjLike(filtroCnpj())));
    }

    private String filtroCnpj() {
        empresa = tblView.getSelectionModel().getSelectedItem();
        String cnpj[];
        cnpj = empresa.getCnpj().split("");
        return (cnpj[0] + cnpj[1] + cnpj[2] + cnpj[3] + cnpj[4] + cnpj[5] + cnpj[6] + cnpj[7]);
    }

    @FXML
    private void acAlterar() {
        acao = ALTERAR;
        empresa = tblView.getSelectionModel().getSelectedItem();
        showCRUD();
    }

    @FXML
    private void acInfoCidade() {
        empresa = tblView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cidade");
        alert.setHeaderText(empresa.getCidade().toString());
        alert.setContentText("Empresas cadastradas: " + " " + Integer.toString(empresaRepository.countByCidade(empresa.getCidade())));
        alert.showAndWait();
    }

    private void showCRUD() {
        String scena = "/fxml/CRUDEmpresa.fxml";
        NossoPopOver popOver = null;
        switch (acao) {
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

    private void atualizaLabel() {
        Random rand = new Random();
        lblRandNum.setText(Integer.toString(rand.nextInt(500)));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblView.setItems(FXCollections.observableList(empresaRepository.findAll()));
        btnAlterar.visibleProperty().bind(Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
        btnInfoCidade.visibleProperty().bind(Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
        btnFiltroCnpj.visibleProperty().bind(Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());

        KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> atualizaLabel());
        Timeline timeline = new Timeline(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
}
