/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_dados;

import excecoes.DataException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Aluno;
import modelo.Curso;

/**
 *
 * @author rafao
 */
public class CarregadorAluno extends Thread{
    private AlunoDAO alunoDAO;
    private String arquivoHistorico;
    private Curso curso;

    public CarregadorAluno(AlunoDAO alunoDAO, String arquivoHistorico, Curso curso) {
        this.alunoDAO = alunoDAO;
        this.arquivoHistorico = arquivoHistorico;
        this.curso = curso;
    }
    
    @Override
    public void run(){
        try {
            alunoDAO.carregarAluno(arquivoHistorico, curso);
        } catch (DataException ex) {
            Logger.getLogger(CarregadorAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}