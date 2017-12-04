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
import base_dados.MatrizDisciplinaDAO;
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
import modelo.Aluno;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Matricula;
import modelo.MatrizCurricular;
import modelo.MatrizDisciplina;
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
    private MatrizDisciplinaDAO matrizDisciplinaDAO;

    
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
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\" + System.getProperty("user.name") +"\\Lead-Me-Maven\\instancia\\cursos.csv"), "UTF-8"));
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
            lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\" + System.getProperty("user.name") +"\\Lead-Me-Maven\\instancia\\estruturas-curriculares.csv"), "UTF-8"));
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
            lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\" + System.getProperty("user.name") +"\\Lead-Me-Maven\\instancia\\componentes-curriculares.csv"), "UTF-8"));
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
                  lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\" + System.getProperty("user.name") +"\\Lead-Me-Maven\\instancia\\"+ano+"."+periodo+".csv"), "UTF-8"));
                    String linha = lerArq.readLine();
                    String[] dados;
                    Turma turma;
                    while ((linha = lerArq.readLine()) != null) {
                        linha = prepararLinha(linha);
                        dados = linha.split(SEPARADOR_CSV);
                        System.out.println("Lendo linha da disciplina");
                        if (dados.length <= 3 || !camposValidos(dados, 0, 1, 2)) {
                            continue;
                        }
                        turma = new Turma();
                        turma.setId(Integer.parseInt(dados[0]));
                        turma.setCodigoTurma(dados[1]);
                        
                        Disciplina disciplina = disciplinaDAO.encontrar(Integer.parseInt(dados[2]));
                        if (disciplina == null) {
                            System.out.println("Disciplina para a turma eh nula");
                            return;
                        }
                        turma.setDisciplina(disciplina);
                        turma.setPeriodoLetivo("" + ano + "." + periodo);
                        try{
                        turmaDAO.salvar(turma);
                        System.out.println("Salvou a turma");
                        }catch(PersistenceException e){
                             System.out.println("NAO SALVOU A TURMA");
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
        for (int ano = 2016; ano <= 2017; ano++) {
            for (int periodo = 1; periodo <= 2; periodo++) {
                BufferedReader lerArq;
                try {
                    lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\" + System.getProperty("user.name") +"\\Lead-Me-Maven\\instancia\\matricula-" + ano + "." + periodo + ".csv"), "UTF-8"));
                    System.err.println("INICIANDO LEITURA DO ARQUIVO DE" + ano + "." + periodo);
                    String linha = lerArq.readLine();
                    String[] dados;
                    while ((linha = lerArq.readLine()) != null) {
                        dados = linha.split(SEPARADOR_CSV);
                        Aluno aluno;
                        Matricula matricula;
                        Turma turma;
                        Curso curso;
                        if (dados.length < 10 || !ExtratorUFRN.camposValidos(dados, 0, 1, 2, 3, 4, 5,6)) {
                            return;
                        }
                        if (!dados[5].contains("APROVADO") && !dados[5].contains("REPROVADO")) {
                            return;
                        }
                        aluno = new Aluno();
                        matricula = new Matricula();
                        aluno.setId(dados[1]);//
                        turma = turmaDAO.encontrar(Integer.parseInt(dados[0]));//
                        curso = cursoDAO.encontrar(Integer.parseInt(dados[2]));//
                        if (turma == null) {
                            System.out.println("Turma nula");
                            return;
                        }
                        if (curso == null) {
                            System.out.println("Curso nulo");
                            return;
                        }
                        aluno.setCurso(curso);
                        aluno.setNome(dados[1]);
                        try {
                            aluno = alunoDAO.salvar(aluno);
                            System.out.println("Salvou aluno");
                        } catch (PersistenceException e) {
                            System.out.println(e.getMessage());
                            System.err.println("NAO SALVOU O ALUNO");
                            aluno = alunoDAO.encontrar(aluno.getId());
                            if (aluno == null) {
                                System.err.println(" >>>>> Nao achou o aluno que tbm nao conseguiu inserir!! <<<<<<");
                                return;
                            }
                        }
                        matricula.setId(Integer.parseInt(dados[6]));
                        matricula.setAluno(aluno);
                        matricula.setTurma(turma);
                        matricula.setMedia(Double.parseDouble(dados[3]));
                        matricula.setNumeroFaltas(Double.parseDouble(dados[4]));
                        matricula.setSituacao(dados[5]);
                        try {
                            matriculaDAO.salvar(matricula);
                            System.out.println("Salvou matricula");
                        } catch (PersistenceException e) {
                            System.err.println("NAO SALVOU A MATRICULA");
                        }
                        System.out.println("Lendo a linha do arquivo " + ano + "." + periodo);

                    }
                    lerArq.close();
                    System.out.println("FINALIZOU A EXTRAÇÃO DAS MATRÍCULAS DO ARQUIVO " + ano + "." + periodo + "!!");
                } catch (IOException ioe) {
                    System.out.println(ioe.getLocalizedMessage());
                }

            }
        }
    }

    @Override
    public void atualizarListaDeComponentesDasMatrizes() {
        try {
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\" + System.getProperty("user.name") +"\\Lead-Me-Maven\\instancia\\gradeDireito.csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dadosMatriz;
            MatrizDisciplina md;
            MatrizCurricular curriculo;
            Disciplina disciplina;

            while ((linha = lerArq.readLine()) != null) {
                linha = prepararLinha(linha);
                dadosMatriz = linha.split(SEPARADOR_CSV);
                md = new MatrizDisciplina();
                md.setId(Integer.parseInt(dadosMatriz[0]));
                if (!camposValidos(dadosMatriz, 0, 1, 2, 3, 4)) {
                    return;
                }
                md = new MatrizDisciplina();
                md.setId(Integer.parseInt(dadosMatriz[3]));
                curriculo = matrizCurricularDAO.encontrar(Integer.parseInt(dadosMatriz[4]));//????
                disciplina = disciplinaDAO.encontrar(Integer.parseInt(dadosMatriz[3]));
                if (curriculo == null || disciplina == null) {
//            fecharConexoes();
                    System.out.println("Matriz ou disciplina nula");
                    return;
                }
                md.setMatrizCurricular(curriculo);
                md.setDisciplina(disciplina);
                md.setSemestreIdeal(Integer.parseInt(dadosMatriz[2]));
                md.setNaturezaDisciplina(dadosMatriz[5]);
                try {
                    matrizDisciplinaDAO.salvar(md);
                } catch (PersistenceException e) {
                    System.out.println("Registro nao inserido no banco");
                }
            }
            lerArq.close();
            System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR A LISTA DE COMPONENTES!");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
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
