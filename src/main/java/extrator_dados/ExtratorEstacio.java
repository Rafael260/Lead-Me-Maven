/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extrator_dados;

import base_dados.AlunoDAO;
import base_dados.CursoDAO;
import base_dados.DisciplinaDAO;
import base_dados.MatriculaDAO;
import base_dados.MatrizCurricularDAO;
import base_dados.TurmaDAO;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import modelo.Curso;
import modelo.Disciplina;
import modelo.MatrizCurricular;
import modelo.Turma;
import util.ThreadUtil;

/**
 *
 * @author rafao
 */
public class ExtratorEstacio extends Extrator{

    public static final String SEPARADOR_CSV = ";";
    private CursoDAO cursoDAO;
    private MatrizCurricularDAO matrizCurricularDAO;
    private DisciplinaDAO disciplinaDAO;
    private TurmaDAO turmaDAO;
    private MatriculaDAO matriculaDAO;
    private AlunoDAO alunoDAO;
    
    public ExtratorEstacio() {
        cursoDAO = CursoDAO.getInstance();
        matrizCurricularDAO = MatrizCurricularDAO.getInstance();
        disciplinaDAO = DisciplinaDAO.getInstance();
        turmaDAO = TurmaDAO.getInstance();
        matriculaDAO = MatriculaDAO.getInstance();
        alunoDAO = AlunoDAO.getInstance();
    }
    
    @Override
    public void atualizarListaDeCursos() {
        try {
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("\\instancia\\cursos.csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dadosCurso;
            Curso curso;
            while ((linha = lerArq.readLine()) != null) {
                linha = prepararLinha(linha);
                dadosCurso = linha.split(SEPARADOR_CSV);
                if (!camposValidos(dadosCurso, 0, 1, 2, 3)) {
                    continue;
                }
                curso = new Curso();
                curso.setNome(dadosCurso[0]);
                curso.setId(Integer.parseInt(dadosCurso[1]));
                curso.setModalidade(dadosCurso[2]);
                curso.setGrauAcademico(dadosCurso[3]);
                try {
                    cursoDAO.salvar(curso);
                } catch (PersistenceException e) {
                    System.out.println("Registro nao inserido no banco");
                }
            }
            lerArq.close();
            System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR OS CURSOS!");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void atualizarListaDeMatrizesCurriculares() {
        BufferedReader lerArq;
        try {
            lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("\\estruturas-curriculares.csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dadosMatriz;
            MatrizCurricular matriz;
            Curso curso;
            while ((linha = lerArq.readLine()) != null) {
                linha = prepararLinha(linha);
                dadosMatriz = linha.split(SEPARADOR_CSV);
                if (!camposValidos(dadosMatriz, 0, 2, 3, 4)) {
                    continue;
                }
                matriz = new MatrizCurricular();
                matriz.setId(Integer.parseInt(dadosMatriz[0]));
                matriz.setNomeMatriz(dadosMatriz[2]);
                curso = cursoDAO.encontrar(Integer.parseInt(dadosMatriz[3]));
                if (curso == null) {
                    System.err.println("Curso não encontrado para essa matriz");
                    continue;
                }
                matriz.setCurso(curso);
                try {
                    matrizCurricularDAO.salvar(matriz);
                } catch (PersistenceException e) {
                    System.out.println("Nao gravou matriz curricular: " + e.getLocalizedMessage());
                }
            }
            System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR AS MATRIZES CURRICULARES!");
            lerArq.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void atualizarListaDeDisciplinas() {
        BufferedReader lerArq;
        try {
            lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("\\componentes-curriculares.csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dados;
            Disciplina disciplina;
            while ((linha = lerArq.readLine()) != null) {
                linha = prepararLinha(linha);
                dados = linha.split(SEPARADOR_CSV);
                System.out.println("Lendo linha da disciplina");
                if (dados.length <= 7 || !camposValidos(dados, 0, 1, 2, 3, 4, 5, 6)) {
                    continue;
                }

                disciplina = new Disciplina();
                disciplina.setNome(dados[0]);
                disciplina.setCargaHoraria(Integer.parseInt(dados[1]));
                disciplina.setCodigo(dados[2]);
                disciplina.setEquivalencias(dados[3] == null ? "" : dados[3]);
                disciplina.setPreRequisitos(dados[4] == null ? "" : dados[4]);
                disciplina.setCoRequisitos(dados[5] == null ? "" : dados[5]);
                disciplina.setId(Integer.parseInt(dados[6]));
                try {
                    disciplinaDAO.salvar(disciplina);
                    System.out.println("Salvou a disciplina");
                } catch (PersistenceException e) {
                    System.out.println("NAO SALVOU A DISCIPLINA");
                }
            }
            lerArq.close();
            System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR AS DISCIPLINAS!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    

    @Override
    public void atualizarListaDeTurmas() {
        for (int ano = 2016; ano <= 2017; ano++) {
            for (int periodo = 1; periodo <= 2; periodo++) {
                BufferedReader lerArq;
             try {
                  lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("\\"+ano+"."+periodo+".csv"), "UTF-8"));
                    String linha = lerArq.readLine();
                    String[] dados;
                    Turma turma;
                    while ((linha = lerArq.readLine()) != null) {
                        linha = prepararLinha(linha);
                        dados = linha.split(SEPARADOR_CSV);
                        System.out.println("Lendo linha da disciplina");
                        if (dados.length <= 7 || !camposValidos(dados, 0, 1, 2, 3, 4, 5, 6)) {
                            continue;
                        }
                        turma = new Turma();
                        turma.setPeriodoLetivo(dados[0]);
                        turma.setId(Integer.parseInt(dados[0]));
                        turma.setCodigoTurma(dados[2]);
                        disciplina.setEquivalencias(dados[3] == null ? "" : dados[3]);
                        disciplina.setPreRequisitos(dados[4] == null ? "" : dados[4]);
                        disciplina.setCoRequisitos(dados[5] == null ? "" : dados[5]);
                        disciplina.setId(Integer.parseInt(dados[6]));
                     try {
                    turmaDAO.salvar(turma);
                    System.out.println("Salvou a disciplina");
                     } catch (PersistenceException e) {
                    System.out.println("NAO SALVOU A DISCIPLINA");
                }
            }
            lerArq.close();
            System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR AS DISCIPLINAS!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
            }
        }

        System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR AS TURMAS!");
    }

    @Override
    public void atualizarListaDeAlunos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizarListaDeComponentesDasMatrizes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizarListaDeMatriculas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static boolean camposValidos(String[] linha, int... indices) {
        for (int i : indices) {
            if (linha[i] == null || linha[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }
    public static String prepararLinha(String linha) {
        return linha.replace("\"", "");
    }
}