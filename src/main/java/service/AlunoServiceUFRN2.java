/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import base_dados.AlunoDAO;
import modelo.Aluno;

/**
 *
 * @author rafao
 */
public class AlunoServiceUFRN2 extends AlunoService{

    AlunoDAO alunoDAO;
    
    public AlunoServiceUFRN2(){
        alunoDAO = AlunoDAO.getInstance();
    }
    
    //Carregando as matrículas só do banco, ao invés de buscar pela API tbm
    @Override
    public void carregarMatriculasDoAluno(Aluno aluno) {
        Aluno alunoNoBanco = alunoDAO.encontrar(aluno.getId());
        aluno.setMatriculas(alunoNoBanco.getMatriculas());
    }
    
}
