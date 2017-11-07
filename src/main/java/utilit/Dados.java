package utilit;

import static config.Config.df;
import static config.Config.nfc;
import static config.DAO.docFiscalRepository;
import static config.DAO.empresaRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import model.DocFiscal;
import model.Empresa;
import model.Vendedor;

//Classe responsável pelo tratamento de arquivos
public class Dados {

    private BufferedReader br = null;
    private String nomeArq;
    private DocFiscal docFiscalObj;
    private Empresa empresaObj, empresaBanco;
    //Número de erros na leitura do arquivo (linhas inválidas)
    public int erros = 0;
    //Número de Documentos Fiscais criados
    public int numeroDocs = 0;

    public Dados(String nomeArq) {
        this.nomeArq = nomeArq;
    }

    //Método que abre o arquivo e realiza a leitura
    public void lerTxt(Vendedor vendedor) {
        String linha;
        try {
            br = new BufferedReader(new FileReader(nomeArq)); //ponteiro br para o arquivo nomeArq
            br.readLine();
            while ((linha = br.readLine()) != null) { //lê as linhas até acabar, ou seja, até o buffer zerar                
                separaLinha(linha, vendedor);
            }
        } catch (Exception e) {

        } finally { //executa mesmo que dê erro
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {

            }
        }
    }

    //Método que divide a linha do arquivo em partes e realiza as devidas alocações e inserções no banco
    private void separaLinha(String linha, Vendedor vendedorBanco) throws ParseException {
        String[] partes = linha.split(";");
        String cnpj = partes[0].replaceAll("[./-]+", ""); //Limpa a string, deixa sem ./-
        //Só realiza a alocações caso a linha seja válida (parâmetro: cnpj e 7 partes)
        if (cnpj.matches("([0-9]){14}") && partes.length == 7) {

            empresaObj = new Empresa(cnpj, partes[1].toUpperCase());

            //Se não houver empresa no banco, cria
            if (empresaRepository.findByCnpj(cnpj) == null) {
                empresaBanco = empresaRepository.insert(empresaObj);
            } else {
                //Se já existe, usa ela
                empresaBanco = empresaRepository.findByCnpj(cnpj);
            }

            docFiscalObj = new DocFiscal(empresaBanco, vendedorBanco, partes[2], LocalDate.parse(partes[3], df), nfc.parse(partes[4]).doubleValue(), nfc.parse(partes[5]).doubleValue(), partes[6]);

            //Só realiza a inserção do Documento Fiscal no banco se ele não estiver lá
            if (docFiscalRepository.findByEmpresaAndNota(empresaBanco, partes[2]) == null) {
                docFiscalRepository.insert(docFiscalObj);
                numeroDocs++;
            }
        } else {
            //Caso a linha sejá inválida, incrementa o número de erros
            erros++;
        }
    }

    public int getNumeroDocs() {
        return numeroDocs;
    }

    public void setNumeroDocs(int numeroDocs) {
        this.numeroDocs = numeroDocs;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }
}
