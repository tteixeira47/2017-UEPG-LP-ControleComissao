package view;

import static config.Config.df;
import static config.Config.i18n;
import static config.Config.nf;
import static config.DAO.cidadeRepository;
import static config.DAO.docFiscalRepository;
import static config.DAO.empresaRepository;
import static config.DAO.vendedorEmpresaRepository;
import static config.DAO.vendedorRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.File;
import java.net.URL;
import java.text.ParseException;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

public class VendedorEmpresaController implements Initializable {

    public Empresa empresa;
    public Vendedor vendSelec, vendObj;
    private Dados dados;

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

    @FXML
    private TextField txtFldFiltro;


    /*
    Empresa empTotal = new Empresa("", "Total");
    MesEmpresa mesEmpresaTotal = new MesEmpresa();
    vendSelec  = cmbMes.getSelectionModel().getSelectedItem();
    if (mesSelec

    
        != null) {
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
    private void onKeyPressFiltrar(KeyEvent evt) throws ParseException {
        vendSelec = cmbVendedor.getSelectionModel().getSelectedItem();
        
        if (evt.getCode() == KeyCode.ENTER) {
            tblView.setItems(FXCollections.observableList(vendedorEmpresaRepository.findByVendedorAndValorComissaoGreaterThan(vendSelec, nf.parse(txtFldFiltro.getText()).doubleValue())));
        }
    }

    //Mètodo que desabilita os botões de acordo com a condição atual
    private void desabilitaBotoes() {
        if (vendedorRepository.findAll().isEmpty()) {
            btnDeletar.setDisable(true);
            btnLerArquivo.setDisable(false);
            btnGerar.setDisable(false);
        } else {
            btnDeletar.setDisable(false);
            btnGerar.setDisable(true);
        }
    }

    //Método que atualiza o número de notas do Vendedor selecionado
    private void atualizaNumeroNotas() {
        if (vendedorEmpresaRepository.findByVendedor(vendSelec) == null) {
            hbLabelTotalNotas.setVisible(false);
        } else {
            tblView.setItems(FXCollections.observableList(vendedorEmpresaRepository.findByVendedor(vendSelec)));
            int qtdeNotas = 0;
            for (VendedorEmpresa vE : vendedorEmpresaRepository.findByVendedor(vendSelec)) {
                qtdeNotas += vE.getNumNotas();
            }

            hbLabelTotalNotas.setVisible(true);
            lblTotalNotas.setText(String.valueOf(qtdeNotas));
        }
    }

    private void atualizaVendedor() {
        //Seleciona o vendedor selecionado no ComboBox
        vendSelec = cmbVendedor.getSelectionModel().getSelectedItem();
        atualizaNumeroNotas();
    }

    private void atualizaCombo() {
        //Dispara a atualização do tableview smepre que outro objeto é selecionado no combo
        cmbVendedor.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    atualizaVendedor();
                }
        );
        //Cria a lista do ComboBox em ordem alfabética com base nos vendedores do banco
        cmbVendedor.setItems(FXCollections.observableList(vendedorRepository.findAll(
                new Sort(new Sort.Order(Sort.Direction.DESC, "nome")
                ))));

        cmbVendedor.getSelectionModel().selectFirst();
    }

    //Método que limpa o banco
    @FXML
    public void acDeletaAll() {
        //Alerta de confirmação de exclusão
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

            /*
            System.out.println("Collection Cidade:" + cidadeRepository.findAll());
            System.out.println("Collection DocFiscal:" + docFiscalRepository.findAll());
            System.out.println("Collection Empresa:" + empresaRepository.findAll());
            System.out.println("Collection Vendedor:" + vendedorRepository.findAll());
            System.out.println("Collection VendedorEmpresa:" + vendedorEmpresaRepository.findAll());
             */
        }

        //Atualiza a interface
        atualizaCombo();
        desabilitaBotoes();
        //Desabilita o botão de totalizar, pois não existe nenhum vendedor para totalizar
        btnTotalizar.setDisable(true);
    }

    //Método que popula o banco com 2 vendedores, 10 cidades, 10 empresas e 100 notas aleatórias
    @FXML
    public void acLimpaFiltro() {
        txtFldFiltro.clear();
        tblView.setItems(FXCollections.observableList(vendedorEmpresaRepository.findAll()));
        txtFldFiltro.requestFocus();
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
        //Habilita o botão de totalizar
        btnTotalizar.setDisable(false);
    }

    //Método que realiza os cálculos de totalização do VendedorEmpresa
    @FXML
    public void acTotalizarVendedor() {
        //Seleciona o vendedor do ComboBox
        vendSelec = cmbVendedor.getSelectionModel().getSelectedItem();
        //Limpa os registros de VendedorEmpresa deste vendedor
        vendedorEmpresaRepository.deleteByVendedor(vendSelec);

        for (DocFiscal d : docFiscalRepository.findByVendedor(vendSelec)) {
            VendedorEmpresa vEmp = vendedorEmpresaRepository.findByVendedorAndEmpresa(vendSelec, d.getEmpresa());
            d.getEmpresa();

            //Cria os registros de VendedorEmpresa com base nos Documentos Fiscais do vendedor selecionado
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

    //Método que lê um arquivo e cria uma base dados com as informações contidas nele
    @FXML
    public void acLerArquivo() {
        //Abre um FileChooser
        final Stage stage = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha o seu arquivo txt");
        fileChooser.setInitialDirectory(new File("C:\\Users\\Thiago.TEIXEIRA\\Documents\\UEPG\\2o ANO\\LINGUAGENS PROGRAMAÇÃO\\Vendedores"));
        fileChooser.setInitialFileName("C:\\Users\\Thiago.TEIXEIRA\\Documents\\UEPG\\2o ANO\\LINGUAGENS PROGRAMAÇÃO\\Vendedores\\Maria do Rosário.txt");
        String s = String.valueOf(fileChooser.showOpenDialog(stage));

        //Só cria a base de dados se um arquivo for selecionado
        if (s.contains("\\")) {
            dados = new Dados(s);
            //Seleciona a última parte do caminho do arquivo, ou seja, o nome do vendedor
            String nome = s.substring(s.lastIndexOf("\\") + 1, s.length());
            String nomeVendedor[];
            //Apaga o .txt do nome
            nomeVendedor = nome.split(".txt");
            vendObj = new Vendedor(nomeVendedor[0]);
            //Realiza a importação dos dados caso o vendedor não esteja cadastrado
            if (vendedorRepository.findByNome(nomeVendedor[0]) == null) {
                dados.lerTxt(vendedorRepository.insert(vendObj));
                //Alerta de arquivo inválido quando nenhum Documento Fiscal é criado
                if (dados.getNumeroDocs() == 0) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle(i18n.getString("alertImportaVendedorTitle.text"));
                    alert.setHeaderText(i18n.getString("alertImportaVendedorErroHeader.text"));
                    alert.setContentText(i18n.getString("alertImportaVendedorErroArquivoContent.text"));
                    alert.showAndWait();
                    //Não cria o vendedor
                    vendedorRepository.delete(vendObj);
                } else {
                    //Alerta de sucesso na importação
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(i18n.getString("alertImportaVendedorTitle.text"));
                    alert.setHeaderText(i18n.getString("alertImportaVendedorSucessoHeader.text"));
                    //Caso haja algum erro na leitura, eles são exibidos no alerta
                    alert.setContentText(i18n.getString("alertImportaVendedorSucessoContent.text") + " " + Integer.toString(dados.getErros()));
                    alert.showAndWait();

                    atualizaCombo();
                    desabilitaBotoes();
                    btnTotalizar.setDisable(false);
                }
            } else {
                //Alerta de que o vendedor selecionado já foi cadastrado
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(i18n.getString("alertImportaVendedorTitle.text"));
                alert.setHeaderText(i18n.getString("alertImportaVendedorErroCadastroHeader.text"));
                alert.setContentText(i18n.getString("alertImportaVendedorErroCadastroContent.text"));
                alert.showAndWait();
            }
        }
    }

    //Método que exibe os documentos fiscais de um VendedorEmpresa num PopOver
    @FXML
    private void acShowDocFiscalTable() {
        String cena = "/fxml/ListaDocFiscVE.fxml";
        //Cria o PopOver para exibir os Documentos Fiscais
        NossoPopOver popOver = null;
        popOver = new NossoPopOver(cena, tblView.getSelectionModel().getSelectedItem().getEmpresaFantasia(), null);
        ListaDocFiscVEController controller = popOver.getLoader().getController();
        controller.setControllerPai(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Exibe o botão de exibir os documentos fiscais apenas quando a empresa é selecionado no TableView
        btnDocFiscVE.visibleProperty().bind(Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
        hbLabelTotalNotas.setVisible(false);
        atualizaCombo();
        desabilitaBotoes();
    }
}
