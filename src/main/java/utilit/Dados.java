/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

import static config.Config.df;
import static config.Config.nfc;
import static config.DAO.docFiscalRepository;
import static config.DAO.empresaRepository;
import static config.DAO.vendedorRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.DocFiscal;
import model.Empresa;
import model.Vendedor;

/**
 *
 * @author Thiago
 */
public class Dados {

    private BufferedReader br = null;
    private String nomeArq;
    private DocFiscal docFiscalObj;
    private Empresa empresaObj, empresaBanco;

    // List<Time> lstTimes = new ArrayList<>();

    public Dados(String nomeArq) {
        this.nomeArq = nomeArq;
    }

    /*
    private Time achaTime(String nomeBusca) {
        for (Time t : lstTimes) {
            if (t.getNome().equals(nomeBusca)) {
                return t;
            }
        }
        Time novoTime = new Time(nomeBusca);
        lstTimes.add(novoTime);
        return novoTime;
    }

    private void analisa(Jogo jg) {
        Time posTimeA, posTimeB;
        posTimeA = achaTime(jg.getTimeA()); //Time que está em alguma posição da lista
        posTimeB = achaTime(jg.getTimeB());

        if (jg.getGolA() > jg.getGolB()) { // TimaA venceu partida
            posTimeA.addVit();
            posTimeB.addDer();
        } else if (jg.getGolA() < jg.getGolB()) { // TimeB venceu partida
            posTimeB.addVit();
            posTimeA.addDer();
        } else { // Empate
            posTimeA.addEmp();
            posTimeB.addEmp();
        }
        posTimeA.addGolP(jg.getGolA());
        posTimeA.addGolN(jg.getGolB());
        posTimeB.addGolP(jg.getGolB());
        posTimeB.addGolN(jg.getGolA());
    }

    public void ordena() { //método de ordenação por inserção
        Time timeAux;
        int i, j;
        i = 1;
        while (i <= lstTimes.size() - 1) {
            j = i;
            while ((j > 0) 
            && ((lstTimes.get(j - 1).getPontos() < lstTimes.get(j).getPontos())
            || ((lstTimes.get(j - 1).getPontos() == lstTimes.get(j).getPontos()) && lstTimes.get(j - 1).getVit() < lstTimes.get(j).getVit()) 
            || ((lstTimes.get(j - 1).getPontos() == lstTimes.get(j).getPontos()) && lstTimes.get(j - 1).getVit() == lstTimes.get(j).getVit() && lstTimes.get(j - 1).getSaldoGols() < lstTimes.get(j).getSaldoGols()))) {        
                timeAux = lstTimes.get(j - 1);
                lstTimes.set(j - 1, lstTimes.get(j));
                lstTimes.set(j, timeAux);
                j--;
            }
            i++;
        }
    }
    
    public List<Time> ler() {
        String linha;
        try {
            br = new BufferedReader(new FileReader(nomeArq)); //ponteiro br para o arquivo nomeArq
            while ((linha = br.readLine()) != null) { //lê as linhas até acabar, ou seja, até o buffer zerar
                jgLinha = new Jogo(linha);
                analisa(jgLinha);
            }
            
            ordena();
            
            byte i = 1;
            for (Time t : lstTimes) {
                t.setCla(i);
                i++;
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
        return lstTimes;
    }
    */
    
    private void separaLinha(String linha, Vendedor vendedorBanco) throws ParseException {
        String[] partes = linha.split(";");
        
        String cnpj = partes[0].replaceAll("[./-]+",""); //Limpa a string, deixa sem ./-
        
        empresaObj = new Empresa(cnpj, partes[1].toUpperCase());
        
        if (empresaRepository.findByCnpj(cnpj) == null){
            empresaBanco = empresaRepository.insert(empresaObj);
        } else {
            empresaBanco = empresaRepository.findByCnpj(cnpj);
        }
        docFiscalObj = new DocFiscal(empresaBanco, vendedorBanco, partes[2], LocalDate.parse(partes[3], df), nfc.parse(partes[4]).doubleValue(), nfc.parse(partes[4]).doubleValue());
        docFiscalRepository.insert(docFiscalObj);
    }
    
    public void lerTxt(Vendedor vendedor) {
        String linha;
        try {
            br = new BufferedReader(new FileReader(nomeArq)); //ponteiro br para o arquivo nomeArq
            br.readLine();
            while ((linha = br.readLine()) != null) { //lê as linhas até acabar, ou seja, até o buffer zerar                
                separaLinha(linha, vendedor);
            }
            
            /*
            System.out.println(docFiscalRepository.findAll());
            System.out.println(empresaRepository.findAll());
            System.out.println(vendedorRepository.findAll());
            */
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

}
