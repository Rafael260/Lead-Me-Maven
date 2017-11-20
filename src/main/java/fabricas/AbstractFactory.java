/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabricas;

import com.mycompany.lead.me.CarregadorTelaLogin;
import extrator_dados.Extrator;
import service.ComparadorDisciplinaDificil;
import service.ComparadorMatrizDisciplina;
import service.MatriculaService;
import service.RequisitosService;

/**
 *
 * @author rafao
 */
public interface AbstractFactory {
    public CarregadorTelaLogin createCarregadorTelaLogin();
    public RequisitosService createRequisitosService();
    public MatriculaService createMatriculaService();
    public ComparadorDisciplinaDificil createComparadorDisciplinaDificil();
    public ComparadorMatrizDisciplina createComparadorMatrizDisciplina();
    public Extrator createExtrator();
}
