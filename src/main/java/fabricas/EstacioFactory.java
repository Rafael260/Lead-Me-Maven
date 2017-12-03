/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabricas;

import com.mycompany.lead.me.CarregadorTelaLogin;
import com.mycompany.lead.me.CarregadorTelaLoginEstacio;
import extrator_dados.Extrator;
import extrator_dados.ExtratorEstacio;
import minerador.AssociacaoStrategy;
import minerador.UFRNStrategy;
import service.AlunoService;
import service.AlunoServiceEstacio;
import service.ComparadorMatrizDisciplina;
import service.ComparadorMatrizDisciplinaEstacio;
import service.MatriculaService;
import service.RequisitosService;
import service.RequisitosServiceEstacio;

/**
 *
 * @author rafao
 */
public class EstacioFactory implements AbstractFactory{

    @Override
    public RequisitosService createRequisitosService() {
        return new RequisitosServiceEstacio();
    }


    @Override
    public ComparadorMatrizDisciplina createComparadorMatrizDisciplina() {
        return new ComparadorMatrizDisciplinaEstacio();
    }

    @Override
    public Extrator createExtrator() {
        return new ExtratorEstacio();
    }

    @Override
    public CarregadorTelaLogin createCarregadorTelaLogin() {
        return new CarregadorTelaLoginEstacio();
    }

    @Override
    public AlunoService createAlunoService() {
      return new AlunoServiceEstacio(); 
    }

    @Override
    public AssociacaoStrategy createAssociacaoStrategy() {
        return new UFRNStrategy();
    }
    
}
