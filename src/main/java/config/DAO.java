/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import model.VendedorEmpresa;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.VendedorRepository;
import repository.DocFiscalRepository;
import repository.EmpresaRepository;
import repository.CidadeRepository;
import repository.VendedorEmpresaRepository;

/**
 *
 * @author mathe
 */
public class DAO {
    private static final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DBConfig.class);
    public static VendedorRepository vendedorRepository = ctx.getBean(VendedorRepository.class);
    public static DocFiscalRepository docFiscalRepository = ctx.getBean(DocFiscalRepository.class);
    public static EmpresaRepository empresaRepository = ctx.getBean(EmpresaRepository.class);
    public static CidadeRepository cidadeRepository = ctx.getBean(CidadeRepository.class);
    public static VendedorEmpresaRepository vendedorEmpresaRepository = ctx.getBean(VendedorEmpresaRepository.class);
    
}
