package view;

import static config.DAO.docFiscalRepository;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.DocFiscal;

//Classe que controla a tela que exibe os Documentos Fiscais de um VendedorEmpresa
public class ListaDocFiscVEController implements Initializable {

    private VendedorEmpresaController controllerPai;
    public DocFiscal dosFiscal;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lblVendedorDocFiscal;

    @FXML
    public TableView<DocFiscal> tblView;

    List<DocFiscal> lstDocFiscalVE = new ArrayList<DocFiscal>();

    //Mètodo que cria a lista com os documentos fiscais do VendedorEmpresa selecionado
    private void geraListaDocFiscalVE() {
        for (DocFiscal d : docFiscalRepository.findByVendedor(controllerPai.vendSelec)) {
            if (d.getEmpresa().equals(controllerPai.tblView.getSelectionModel().getSelectedItem().getEmpresa())) {
                lstDocFiscalVE.add(d);
            }
        }
    }

    //Método de relação com o controller anterior (pai)
    public void setControllerPai(VendedorEmpresaController controllerPai) {
        this.controllerPai = controllerPai;
        //Exibe o nome do VendedorEmpresa no título
        lblVendedorDocFiscal.setText(controllerPai.vendSelec + " - " + controllerPai.tblView.getSelectionModel().getSelectedItem().getEmpresa());
        //Cria a lista
        geraListaDocFiscalVE();
        //Exibe a lista de Documentos Fiscais do VE selecionado no TableView
        tblView.setItems(FXCollections.observableList(lstDocFiscalVE));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
