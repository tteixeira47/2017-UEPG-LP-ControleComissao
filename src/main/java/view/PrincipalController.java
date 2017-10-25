package view;

import static config.Config.df;
import static config.DAO.cidadeRepository;
import static config.DAO.docFiscalRepository;
import static config.DAO.empresaRepository;
import static config.DAO.vendedorEmpresaRepository;
import static config.DAO.vendedorRepository;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import model.Cidade;
import model.DocFiscal;
import model.Empresa;
import model.Vendedor;
import model.VendedorEmpresa;

public class PrincipalController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
/*
        Cidade cid = new Cidade("Ponta Grossa");
        cid = cidadeRepository.insert(cid);
        
        Vendedor ven = new Vendedor("João da Silva");
        ven = vendedorRepository.insert(ven);

        Empresa emp = new Empresa("22660046000170",
                "Lojão do Queima", cid);
        emp = empresaRepository.insert(emp);
        docFiscalRepository.insert(new DocFiscal(emp, ven,"120",LocalDate.parse("01/05/2017", df), 1230, 110));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "118", LocalDate.parse("12/05/2017", df), 2230, 81));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "128", LocalDate.parse("14/05/2017", df), 2370, 110));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "127", LocalDate.parse("17/05/2017", df), 2230, 210));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "132", LocalDate.parse("18/05/2017", df), 5230, 310));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "133", LocalDate.parse("19/05/2017", df), 6230, 100));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "134", LocalDate.parse("20/05/2017", df), 2230, 110));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "144", LocalDate.parse("21/05/2017", df), 8230, 810));
        
        VendedorEmpresa vendEmp = new VendedorEmpresa(ven, emp,
                8, 45098.80, 2345.9);
        vendedorEmpresaRepository.insert(vendEmp);

        
        emp = new Empresa("56660046000170","Maxi Tango", cid);
        emp = empresaRepository.insert(emp);
        docFiscalRepository.insert(new DocFiscal(emp, ven,"140",LocalDate.parse("01/05/2017", df), 1230, 110));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "148", LocalDate.parse("15/05/2017", df), 1230, 81));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "138", LocalDate.parse("15/05/2017", df), 1370, 10));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "137", LocalDate.parse("12/05/2017", df), 1230, 20));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "132", LocalDate.parse("13/05/2017", df), 1230, 30));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "134", LocalDate.parse("14/05/2017", df), 1230, 10));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "144", LocalDate.parse("25/05/2017", df), 1230, 10));
        docFiscalRepository.insert(new DocFiscal(emp, ven, "178", LocalDate.parse("27/05/2017", df), 1230, 80));

        vendEmp = new VendedorEmpresa(ven, emp,
                8, 54045.80, 4545.9);
        vendedorEmpresaRepository.insert(vendEmp);
        
        ven = new Vendedor("Joaquim Manuel");
        ven = vendedorRepository.insert(ven);
        
        emp = empresaRepository.findByCnpj("22660046000170");
        
        docFiscalRepository.insert(new DocFiscal(emp, ven,"6140",LocalDate.parse("10/05/2017", df), 100230, 12110));
        
        
        vendEmp = new VendedorEmpresa(ven, emp,
                1, 100230.0, 12110.0);
        
        vendedorEmpresaRepository.insert(vendEmp);

*/
        
        

    }
}
