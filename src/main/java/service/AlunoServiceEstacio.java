/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import base_dados.TurmaDAO;
import modelo.Aluno;

/**
 *
 * @author diego
 */
public class AlunoServiceEstacio extends AlunoService{
  
    //private TurmaDAO turmaDAO;
    public AlunoServiceEstacio(){
   // turmaDAO = TurmaDAO.getInstance();
    }
    @Override
    public void carregarMatriculasDoAluno(Aluno aluno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
