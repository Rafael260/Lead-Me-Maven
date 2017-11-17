/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extrator_dados;

import base_dados.AlunoDAO;
import base_dados.CursoDAO;
import base_dados.MatriculaDAO;
import base_dados.TurmaDAO;
import static extrator_dados.ExtratorUFRN.PASTA_DADOS_ABERTOS;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import javax.persistence.PersistenceException;
import modelo.Aluno;
import modelo.Curso;
import modelo.Matricula;
import modelo.Turma;

/**
 *
 * @author rafao
 */
public class LeitorArquivoMatriculaUFRN extends Thread {

//    private static final int NUMERO_THREADS_PROCESSAMENTO_LINHAS = 20;
    private int ano;
    private int periodo;

    private CursoDAO cursoDAO;
    private TurmaDAO turmaDAO;
    private AlunoDAO alunoDAO;
    private MatriculaDAO matriculaDAO;

    public LeitorArquivoMatriculaUFRN(int ano, int periodo) {
        this.ano = ano;
        this.periodo = periodo;
        cursoDAO = CursoDAO.getInstance();
        turmaDAO = TurmaDAO.getInstance();
        alunoDAO = AlunoDAO.getInstance();
        matriculaDAO = MatriculaDAO.getInstance();
    }

    public void run() {
        BufferedReader lerArq;
        try {
            lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "\\matriculas\\matricula-componente-" + ano + "." + periodo + ".csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dadosMatricula;
            Aluno aluno;
            Matricula matricula;
            Turma turma;
            Curso curso;
            while ((linha = lerArq.readLine()) != null) {
                dadosMatricula = linha.split(ExtratorUFRN.SEPARADOR_CSV);
                if (dadosMatricula.length < 10 || !ExtratorUFRN.camposValidos(dadosMatricula, 0, 1, 2, 4, 6, 7, 8, 9)) {
                    System.out.println("Linha inválida da matricula");
                    return;
                }
                ExtratorUFRN.prepararCampos(dadosMatricula, 0, 1, 2, 4, 6, 7, 8, 9);

                if (!dadosMatricula[9].contains("APROVADO") && !dadosMatricula[9].contains("REPROVADO")) {
                    System.out.println("Situacao invalida da matricula");
                    return;
                }
                aluno = new Aluno();
                matricula = new Matricula();
                aluno.setId(dadosMatricula[1]);
                turma = turmaDAO.encontrar(Integer.parseInt(dadosMatricula[0]));
                curso = cursoDAO.encontrar(Integer.parseInt(dadosMatricula[2]));
                if (turma == null || curso == null) {
                    System.out.println("Turma ou curso nulos");
                    return;
                }
                System.out.println("PASSOU PELAS VALIDAÇÕES - MATRICULA -");
                aluno.setCurso(curso);
                try {
                    aluno = alunoDAO.salvar(aluno);
                    System.out.println("Salvou aluno");
                } catch (PersistenceException e) {
                    System.out.println(e.getMessage());
                    System.err.println("NAO SALVOU O ALUNO");
                    aluno = alunoDAO.encontrar(aluno.getId());
                    if (aluno == null) {
                        return;
                    }
                }
                matricula.setAluno(aluno);
                matricula.setTurma(turma);
                matricula.setMedia(Double.parseDouble(dadosMatricula[7]));
                matricula.setNumeroFaltas(Double.parseDouble(dadosMatricula[8]));
                matricula.setSituacao(dadosMatricula[9]);
                try {
                    matriculaDAO.salvar(matricula);
                    System.out.println("Salvou matricula");
                } catch (PersistenceException e) {
                    System.err.println("NAO SALVOU A MATRICULA");
                }
            }
            lerArq.close();
//            ThreadUtil.esperarThreads(tasks);
            System.out.println("FINALIZOU A EXTRAÇÃO DAS MATRÍCULAS DO ARQUIVO " + ano + "." + periodo + "!!");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
