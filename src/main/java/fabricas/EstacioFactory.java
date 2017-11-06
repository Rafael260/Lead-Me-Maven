/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabricas;

import extrator_dados.Extrator;
import extrator_dados.ExtratorEstacio;
import service.ComparadorDisciplinaDificil;
import service.ComparadorDisciplinaDificilEstacio;
import service.ComparadorMatrizDisciplina;
import service.ComparadorMatrizDisciplinaEstacio;
import service.MatriculaService;
import service.MatriculaServiceEstacio;
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
    public MatriculaService createMatriculaService() {
        return new MatriculaServiceEstacio();
    }

    @Override
    public ComparadorDisciplinaDificil createComparadorDisciplinaDificil() {
        return new ComparadorDisciplinaDificilEstacio();
    }

    @Override
    public ComparadorMatrizDisciplina createComparadorMatrizDisciplina() {
        return new ComparadorMatrizDisciplinaEstacio();
    }

    @Override
    public Extrator createExtrator() {
        return new ExtratorEstacio();
    }
}
