/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.df;
import static config.Config.i18n;
import static config.DAO.cidadeRepository;
import static config.DAO.docFiscalRepository;
import static config.DAO.empresaRepository;
import static config.DAO.vendedorEmpresaRepository;
import static config.DAO.vendedorRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import model.Cidade;
import model.DocFiscal;
import model.Empresa;
import model.Vendedor;
import model.VendedorEmpresa;
import org.springframework.data.domain.Sort;
import utilit.NossoPopOver;
import javafx.stage.Stage;
import utilit.Dados;
 

/**
 * FXML Controller class
 *
 * @author idomar
 */
public class VendedorEmpresaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public Empresa empresa;
    public Vendedor vendSelec, vendObj;

    List<DocFiscal> lstDoc = docFiscalRepository.findByVendedor(vendSelec);

    @FXML
    public TableView<VendedorEmpresa> tblView;

    @FXML
    public ComboBox<Vendedor> cmbVendedor;

    @FXML
    private MaterialDesignIconView btnGerar;

    @FXML
    private MaterialDesignIconView btnTotalizar;

    @FXML
    private MaterialDesignIconView btnDeletar;
    
    @FXML
    private MaterialDesignIconView btnDocFiscVE;
    
    @FXML
    private MaterialDesignIconView btnLerArquivo;
    
    
    
    @FXML
    private Label lblTotalNotas;

    @FXML
    private HBox hbLabelTotalNotas;

//    Empresa empTotal = new Empresa("", "Total");
    //  MesEmpresa mesEmpresaTotal = new MesEmpresa();
    /*  vendSelec = cmbMes.getSelectionModel().getSelectedItem();
        if (mesSelec != null) {
            tblView.setItems(FXCollections.observableList(
                    mesEmpresaRepository.findByAnoAndMes(mesSelec.getAno(), mesSelec.getMes(),
                            new Sort(
                                    new Sort.Order(Sort.Direction.DESC, "numNotas")))));
            tblView.getSelectionModel().selectFirst();
            
            mesEmpresaTotal.setEmpresa(empTotal);
            mesEmpresaTotal.setNumNotas(mesSelec.getNotas());
            mesEmpresaTotal.setTotalNotasVal(mesSelec.getTotal());
            mesEmpresaTotal.setTotalCredito(mesSelec.getCreditos());
            
            List<MesEmpresa> lstTotal = new ArrayList<>();
            lstTotal.add(mesEmpresaTotal);
            tblViewTotal.getItems().clear();
            tblViewTotal.setItems(FXCollections.observableList(lstTotal));
            tblViewTotal.getSelectionModel().selectFirst();
            
        }
     */
    @FXML
    public void acDeletaAll() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(i18n.getString("alertLimpaBancoTitle.text")); 
        alert.setHeaderText(i18n.getString("alertLimpaBancoHeader.text"));
        alert.setContentText(i18n.getString("alertLimpaBancoContent.text"));

        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeOk = new ButtonType(i18n.getString("btnLimpaBanco.text"));

        alert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeOk);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk) {
            cidadeRepository.deleteAll();
            docFiscalRepository.deleteAll();
            empresaRepository.deleteAll();
            vendedorRepository.deleteAll();
            vendedorEmpresaRepository.deleteAll();

            System.out.println("Collection Cidade:" + cidadeRepository.findAll());
            System.out.println("Collection DocFiscal:" + docFiscalRepository.findAll());
            System.out.println("Collection Empresa:" + empresaRepository.findAll());
            System.out.println("Collection Vendedor:" + vendedorRepository.findAll());
            System.out.println("Collection VendedorEmpresa:" + vendedorEmpresaRepository.findAll());
        }

        atualizaCombo();
        desabilitaBotoes();
        btnTotalizar.setDisable(true);

    }

    @FXML
    public void acGeraRandom() {
        Vendedor vendedor = new Vendedor("Zeca Pagode");
        vendedorRepository.insert(vendedor);
        vendedor = new Vendedor("Juca da Silva");
        vendedorRepository.insert(vendedor);

        cidadeRepository.insert(new Cidade("Ponta Grossa/PR"));
        cidadeRepository.insert(new Cidade("Jaguariaíva/PR"));
        cidadeRepository.insert(new Cidade("Arapoti/PR"));
        cidadeRepository.insert(new Cidade("Sengés/PR"));
        cidadeRepository.insert(new Cidade("Carambeí/PR"));
        cidadeRepository.insert(new Cidade("Curitiba/PR"));
        cidadeRepository.insert(new Cidade("Telemaco Borba/PR"));
        cidadeRepository.insert(new Cidade("São Paulo/SP"));
        cidadeRepository.insert(new Cidade("Volta Redonda/RJ"));

        List<Cidade> lstCidades = new ArrayList<Cidade>(cidadeRepository.findAll());

        empresaRepository.insert(new Empresa("31524127000174", "Panificadora Alfa LTDA", "Panificadora Alfa", lstCidades.get(3)));
        empresaRepository.insert(new Empresa("38101599000144", "Cartel de Cali LTDA", "Cartel de Cali", lstCidades.get(3)));
        empresaRepository.insert(new Empresa("66792247000104", "Cartel de Medellín LTDA", "Cartel de Medellín", lstCidades.get(0)));
        empresaRepository.insert(new Empresa("82034362000168", "Better Call Saul LTDA", "Saul Goodman's Office", lstCidades.get(0)));
        empresaRepository.insert(new Empresa("31024127000174", "Restaurantes Los Pollos Hermanos", "Los Pollos Hermanos", lstCidades.get(1)));
        empresaRepository.insert(new Empresa("37901599000144", "Fun Society INC", "F. Society", lstCidades.get(1)));
        empresaRepository.insert(new Empresa("92034362000168", "Rust-eze Medicated Bumper Ointment", "Rust-Eze", lstCidades.get(2)));
        empresaRepository.insert(new Empresa("66892247000104", "Dinoco Inc", "Dinoco", lstCidades.get(2)));
        empresaRepository.insert(new Empresa("33024127000174", "Monster INC", "Monstros SA", lstCidades.get(3)));
        empresaRepository.insert(new Empresa("38001599000144", "A Empresa do Malvado Doofenshmirtz SA", "A Empresa do Malvado Doofenshmirtz", lstCidades.get(3)));

        List<Empresa> lstEmpresa = new ArrayList<Empresa>(empresaRepository.findAll());
        List<Vendedor> lstVendedor = new ArrayList<Vendedor>(vendedorRepository.findAll());

        Random rand = new Random();
        String dia = "", mes = "";

        for (int i = 0; i < 100; i++) {

            DocFiscal docFisc = new DocFiscal(lstEmpresa.get(rand.nextInt(lstEmpresa.size())),
                    lstVendedor.get(rand.nextInt(lstVendedor.size())),
                    String.valueOf(rand.nextInt(100) + 1),
                    LocalDate.parse("25/10/2017", df),
                    rand.nextDouble() * 100,
                    rand.nextDouble() * 10);
            docFiscalRepository.insert(docFisc);
        }
        atualizaCombo();
        desabilitaBotoes();
        btnTotalizar.setDisable(false);
    }
    

    private void desabilitaBotoes() {
        if (cidadeRepository.findAll().isEmpty()
                && empresaRepository.findAll().isEmpty()
                && vendedorRepository.findAll().isEmpty()) {
            btnDeletar.setDisable(true);
            btnLerArquivo.setDisable(false);
            btnGerar.setDisable(false);
        } else {
            btnDeletar.setDisable(false);
            btnLerArquivo.setDisable(true);
            btnGerar.setDisable(true);
        }
    }

    @FXML
    public void acTotalizarVendedor() {
        vendSelec = cmbVendedor.getSelectionModel().getSelectedItem();
        vendedorEmpresaRepository.deleteByVendedor(vendSelec);

        for (DocFiscal d : docFiscalRepository.findByVendedor(vendSelec)) {
            VendedorEmpresa vEmp = vendedorEmpresaRepository.findByVendedorAndEmpresa(vendSelec, d.getEmpresa());
            d.getEmpresa();

            if (vEmp == null) {
                vEmp = new VendedorEmpresa(vendSelec, d.getEmpresa(), 1, d.getValorNota(), d.getValorCredito());
                vendedorEmpresaRepository.insert(vEmp);
            } else {
                vEmp.addNumNotas();
                vEmp.addValorTotal(d.getValorNota());
                vEmp.addValorComissao(d.getValorCredito());
                vendedorEmpresaRepository.save(vEmp);
            }
        }
        atualizaVendedor();

    }
    
    private Dados dados;
    
    @FXML
    public void acLerArquivo() {
        final Stage stage = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha o seu arquivo txt");
        fileChooser.setInitialDirectory(new File("C:\\Users\\Thiago.TEIXEIRA\\Documents\\UEPG\\2o ANO\\LINGUAGENS PROGRAMAÇÃO"));
        fileChooser.setInitialFileName("C:\\Users\\Thiago.TEIXEIRA\\Documents\\UEPG\\2o ANO\\LINGUAGENS PROGRAMAÇÃO\\Maria do Rosário.txt");
        String s = String.valueOf(fileChooser.showOpenDialog(stage));
        dados = new Dados(s);
        String nome = s.substring(s.lastIndexOf("\\") + 1,s.length());   
        String nomeVendedor[];
        nomeVendedor = nome.split(".txt");
        vendObj = new Vendedor(nomeVendedor[0]);
        if (vendedorRepository.findByNome(nomeVendedor[0]) == null){
            dados.lerTxt(vendedorRepository.insert(vendObj));
        }
        desabilitaBotoes();
        atualizaCombo();
    }
    /*
    private String nomeVendedorArquivo(String linha){
        return 
    }*/

    private void atualizaVendedor() {
        vendSelec = cmbVendedor.getSelectionModel().getSelectedItem();
        tblView.setItems(FXCollections.observableList(vendedorEmpresaRepository.findByVendedor(vendSelec)));
        
         if (vendedorEmpresaRepository.findByVendedor(vendSelec) == null) {
            hbLabelTotalNotas.setVisible(false);
        } else {
            int qtdeNotas = 0;
            for (VendedorEmpresa vE : vendedorEmpresaRepository.findByVendedor(vendSelec)) {
                qtdeNotas += vE.getNumNotas();
            }

            hbLabelTotalNotas.setVisible(true);
            lblTotalNotas.setText(String.valueOf(qtdeNotas));
        }
    }
    
    @FXML
    private void acShowDocFiscalTable() {
        String cena = "/fxml/ListaDocFiscVE.fxml";
        //Cria o PopOver para exibir as matriculas
        NossoPopOver popOver = null;
        popOver = new NossoPopOver(cena,
                tblView.getSelectionModel().getSelectedItem().getEmpresaFantasia(), null);  
        //É definido o controller filho (PopOver Matricula)
        ListaDocFiscVEController controller = popOver.getLoader().getController();
        //O controller filho recebe AlunoController como pai
        //Assim as ações efetuadas no PopOver terão relação com o Aluno selecionado no TableView
        controller.setControllerPai(this);
    }

    private void atualizaCombo() {

        //Dispara a atualização do tableview smepre que outro objeto é selecionado no combo
        cmbVendedor.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    atualizaVendedor();
                }
        );
        cmbVendedor.setItems(FXCollections.observableList(vendedorRepository.findAll(
                new Sort(
                        new Sort.Order(Sort.Direction.DESC, "nome"))
        )));

        cmbVendedor.getSelectionModel().selectFirst();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnDocFiscVE.visibleProperty().bind(Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
        hbLabelTotalNotas.setVisible(false);
        desabilitaBotoes();
        atualizaCombo();
        // tblView.setItems(FXCollections.observableList(vendedorEmpresaRepository.findAll()));
    }
}
