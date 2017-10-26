/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.DAO.docFiscalRepository;
import static config.DAO.empresaRepository;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import utilit.NossoPopOver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.DocFiscal;
import model.Empresa;

/**
 * FXML Controller class
 *
 * @author Thiago
 */
//Classe que controla a tela que exibe as matriculas do Aluno
public class ListaDocFiscVEController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private VendedorEmpresaController controllerPai;

    public DocFiscal dosFiscal;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Label lblVendedorDocFiscal;

    @FXML
    public TableView<DocFiscal> tblView;
    
    List<DocFiscal> lstDocFiscalVE = new ArrayList<DocFiscal>();
    
    private void geraListaDocFiscalVE() {
        for (DocFiscal d : docFiscalRepository.findByVendedor(controllerPai.vendSelec)) {
            if (d.getEmpresa().equals(controllerPai.tblView.getSelectionModel().getSelectedItem().getEmpresa())) {
                lstDocFiscalVE.add(d);
            }
        }
    }

    public void setControllerPai(VendedorEmpresaController controllerPai) {
        this.controllerPai = controllerPai;
        lblVendedorDocFiscal.setText(controllerPai.vendSelec + " - " + controllerPai.tblView.getSelectionModel().getSelectedItem().getEmpresa());
        geraListaDocFiscalVE();
        //Exibe a lista de matriculas do aluno selecionado no TableView
        tblView.setItems(FXCollections.observableList(lstDocFiscalVE));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
