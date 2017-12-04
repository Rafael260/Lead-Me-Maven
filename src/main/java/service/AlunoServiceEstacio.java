/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import base_dados.MatriculaDAO;
import base_dados.TurmaDAO;
import java.util.ArrayList;
import java.util.List;
import modelo.Aluno;
import modelo.Matricula;
import modelo.Turma;

/**
 *
 * @author diego
 */
public class AlunoServiceEstacio extends AlunoService{
    private TurmaDAO turmaDAO;
    private MatriculaDAO matriculaDAO;
    public AlunoServiceEstacio(){
    turmaDAO = TurmaDAO.getInstance();
    matriculaDAO = MatriculaDAO.getInstance();
    }
    @Override
    public void carregarMatriculasDoAluno(Aluno aluno) {
        //List<MatriculaComponenteUfrnDTO> matriculasDTO = consumidor.coletarMatriculas(Integer.parseInt(aluno.getId()));
        List<Matricula> matriculas = new ArrayList<>();
        Matricula matricula;
        Turma turma;

        for (Matricula matriculaIt : matriculas) {

            if (!matriculaIt.situacaoAprovada()) {
                System.out.println("Carregando a matricula");
                matricula = new Matricula();
                matricula.setAluno(aluno);
                turma = turmaDAO.encontrar(matricula.getId());
                if (turma == null) {
                    System.err.println("Não encontrou a turma");
                    continue;
                }
                matricula.setTurma(turma);
    //            matricula.setMedia((Double) matriculaDTO.getMediaFinal());
                String situacao = matriculaIt.getSituacao();
                matricula.setSituacao(situacao);
                matricula.setNumeroFaltas(matriculaIt.getNumeroFaltas());
                matriculas.add(matricula);

            }
            continue;
        }
        aluno.setMatriculas(matriculas);
    }
}
