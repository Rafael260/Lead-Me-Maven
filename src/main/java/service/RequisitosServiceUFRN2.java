/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author rafao
 */
public class RequisitosServiceUFRN2 extends RequisitosService{

    //Na base de dados, colocar sempre uma letra a mais nos codigos: ex: IIMD0024
    @Override
    public String coletarRegexCodigoDisciplina() {
        return "[A-Z]{4}[0-9]{4}";
    }

    @Override
    public String coletarExpressaoRequisitosComOperadores(String requisitos) {
         return requisitos.replace(" AND ", "*").replace(" OR ", "+");
    }
    
}
