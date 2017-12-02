/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabricas;

import com.mycompany.lead.me.CarregadorTelaLogin;
import com.mycompany.lead.me.CarregadorTelaLoginUFRN2;
import extrator_dados.Extrator;
import extrator_dados.ExtratorUFRN2;
import service.AlunoService;
import service.AlunoServiceUFRN2;
import service.ComparadorMatrizDisciplina;
import service.ComparadorMatrizDisciplinaUFRN2;
import service.RequisitosService;
import service.RequisitosServiceUFRN2;

/**
 *
 * @author rafao
 */
public class UFRN2Factory implements AbstractFactory{

    @Override
    public CarregadorTelaLogin createCarregadorTelaLogin() {
        return new CarregadorTelaLoginUFRN2();
    }

    @Override
    public AlunoService createAlunoService() {
        return new AlunoServiceUFRN2();
    }

    @Override
    public RequisitosService createRequisitosService() {
        return new RequisitosServiceUFRN2();
    }

    @Override
    public ComparadorMatrizDisciplina createComparadorMatrizDisciplina() {
        return new ComparadorMatrizDisciplinaUFRN2();
    }

    @Override
    public Extrator createExtrator() {
        return new ExtratorUFRN2();
    }
    
}
