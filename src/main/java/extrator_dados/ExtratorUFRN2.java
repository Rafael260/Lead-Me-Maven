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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
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
public class ExtratorUFRN2 extends Extrator {

    public static final String PASTA_DADOS_ABERTOS = "dadosBTI";
    public static final String SEPARADOR_CSV = ";";
    private CursoDAO cursoDAO;
    private MatrizCurricularDAO matrizCurricularDAO;
    private DisciplinaDAO disciplinaDAO;
    private TurmaDAO turmaDAO;
    private MatriculaDAO matriculaDAO;
    private AlunoDAO alunoDAO;
    private MatrizDisciplinaDAO matrizDisciplinaDAO;

    public ExtratorUFRN2() {
        cursoDAO = CursoDAO.getInstance();
        matrizCurricularDAO = MatrizCurricularDAO.getInstance();
        disciplinaDAO = DisciplinaDAO.getInstance();
        turmaDAO = TurmaDAO.getInstance();
        matriculaDAO = MatriculaDAO.getInstance();
        alunoDAO = AlunoDAO.getInstance();
        matrizDisciplinaDAO = MatrizDisciplinaDAO.getInstance();
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

    @Override
    public void atualizarListaDeCursos() {
        try {
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "/cursos.csv"), "UTF-8"));
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
                curso.setId(Integer.parseInt(dadosCurso[0]));
                curso.setNome(dadosCurso[1]);
                curso.setGrauAcademico(dadosCurso[2]);
                curso.setModalidade(dadosCurso[3]);
                try {
                    cursoDAO.salvar(curso);
                } catch (PersistenceException e) {
                    System.err.println("Curso nao inserido no banco");
                }
            }
            lerArq.close();
            System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR OS CURSOS!");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void atualizarListaDeMatrizesCurriculares() {
        BufferedReader lerArq;
        try {
            lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "/estruturas-curriculares.csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dadosMatriz;
            MatrizCurricular matriz;
            Curso curso;
            while ((linha = lerArq.readLine()) != null) {
                linha = prepararLinha(linha);
                dadosMatriz = linha.split(SEPARADOR_CSV);
                if (!camposValidos(dadosMatriz, 0, 1, 2)) {
                    continue;
                }
                matriz = new MatrizCurricular();
                matriz.setId(Integer.parseInt(dadosMatriz[0]));
                matriz.setNomeMatriz(dadosMatriz[1]);
                curso = cursoDAO.encontrar(Integer.parseInt(dadosMatriz[2]));
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
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void atualizarListaDeDisciplinas() {
        BufferedReader lerArq;
        try {
            lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "/componentes-curriculares-presenciais.csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dados;
            Disciplina disciplina;
            while ((linha = lerArq.readLine()) != null) {
                linha = prepararLinha(linha);
                dados = linha.split(SEPARADOR_CSV);
                System.out.println("Lendo linha da disciplina");
                if (dados.length <= 19 || !camposValidos(dados, 0, 2, 3, 4, 9)) {
                    continue;
                }
                //So interessa as disciplinas de graduacao
                if (!dados[3].toUpperCase().equals("G")) {
                    continue;
                }
                disciplina = new Disciplina();
                disciplina.setId(Integer.parseInt(dados[0]));
                disciplina.setCodigo(dados[2]);
                disciplina.setNome(dados[4]);
                disciplina.setCargaHoraria(Integer.parseInt(dados[9]));
                disciplina.setEquivalencias(dados[16] == null ? "" : dados[16]);
                disciplina.setPreRequisitos(dados[17] == null ? "" : dados[17]);
                disciplina.setCoRequisitos(dados[18] == null ? "" : dados[18]);
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
        String[] periodos = new String[]{"2014.1", "2014.2", "2015.1", "2015.2", "2016.1", "2016.2", "2017.1"};
        for (String periodo : periodos) {
            BufferedReader lerArq;
            try {
                lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "/turmas/turmas-" + periodo + ".csv"), "UTF-8"));
                String linha = lerArq.readLine();
                String[] dadosTurma;
                while ((linha = lerArq.readLine()) != null) {
                    linha = prepararLinha(linha);
                    dadosTurma = linha.split(SEPARADOR_CSV);
                    Turma turma;
                    Disciplina disciplina;
                    if (dadosTurma.length < 13 || !camposValidos(dadosTurma, 0, 1, 4, 5, 6, 7, 9, 11, 12)) {
                        continue;
                    }
                    if (!dadosTurma[5].equals("GRADUAÇÃO") || !dadosTurma[11].equals("CONSOLIDADA") || !dadosTurma[12].equals("Presencial")) {
                        continue;
                    }
                    turma = new Turma();
                    turma.setId(Integer.parseInt(dadosTurma[0]));
                    turma.setCodigoTurma(dadosTurma[1]);
                    disciplina = disciplinaDAO.encontrar(Integer.parseInt(dadosTurma[4]));
                    if (disciplina == null) {
                        System.out.println("Disciplina para a turma eh nula");
                        continue;
                    }
                    turma.setDisciplina(disciplina);
                    turma.setPeriodoLetivo("" + dadosTurma[6] + "." + dadosTurma[7]);
                    try {
                        turmaDAO.salvar(turma);
                        System.out.println("Salvou a turma");
                    } catch (PersistenceException e) {
                        System.out.println("NAO SALVOU A TURMA");
                    }
                }
                lerArq.close();
                System.out.println("FINALIZOU A EXTRAÇÃO DAS TURMAS!!");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR AS TURMAS!");
    }

    @Override
    public void atualizarListaDeComponentesDasMatrizes() {
        Path file = Paths.get(PASTA_DADOS_ABERTOS + "/curriculo-componente-graduacao.csv");
        try {
            Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8);
            Iterator<String> iterator = lines.iterator();
            iterator.next(); //header
            String linha;
            String[] dados;
            while (iterator.hasNext()) {
                linha = iterator.next();
                linha = prepararLinha(linha);
                dados = linha.split(SEPARADOR_CSV);
                MatrizDisciplina md;
                MatrizCurricular curriculo;
                Disciplina disciplina;
                if (!camposValidos(dados, 0, 1, 2, 3, 4)) {
                    continue;
                }
                md = new MatrizDisciplina();
                md.setId(Integer.parseInt(dados[0]));
                curriculo = matrizCurricularDAO.encontrar(Integer.parseInt(dados[1]));
                disciplina = disciplinaDAO.encontrar(Integer.parseInt(dados[2]));
                if (curriculo == null || disciplina == null) {
                    System.out.println("Matriz ou disciplina nula");
                    continue;
                }
                md.setMatrizCurricular(curriculo);
                md.setDisciplina(disciplina);
                md.setSemestreIdeal(Integer.parseInt(dados[4]));
                if(md.getSemestreIdeal().equals(0)){
                    md.setSemestreIdeal(Integer.MAX_VALUE);
                }
                md.setNaturezaDisciplina(dados[3]);
                try {
                    matrizDisciplinaDAO.salvar(md);
                } catch (PersistenceException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR AS GRADES!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void atualizarListaDeAlunos() {
        String[] periodos = new String[]{"2014.1", "2014.2", "2015.1", "2015.2", "2016.1", "2016.2", "2017.1"};
        for (String periodo : periodos) {
            BufferedReader lerArq;
            try {
                lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "/matriculas/matricula-" + periodo + ".csv"), "UTF-8"));
                String linha = lerArq.readLine();
                String[] dadosMatricula;
                while ((linha = lerArq.readLine()) != null) {
                    linha = prepararLinha(linha);
                    dadosMatricula = linha.split(SEPARADOR_CSV);
                    Aluno aluno;
                    Matricula matricula;
                    Turma turma;
                    Curso curso;
                    if (dadosMatricula.length < 5 || !camposValidos(dadosMatricula, 0, 1, 2)) {
                        continue;
                    }
                    if (!dadosMatricula[4].contains("APROVADO") && !dadosMatricula[4].contains("REPROVADO")) {
                        continue;
                    }
                    aluno = new Aluno();
                    matricula = new Matricula();
                    aluno.setId(dadosMatricula[1]);
                    turma = turmaDAO.encontrar(Integer.parseInt(dadosMatricula[0]));
                    curso = cursoDAO.encontrar(92127264);
                    if (turma == null) {
                        System.out.println("Turma nula");
                        continue;
                    }
                    if (curso == null) {
                        System.out.println("Curso nulo");
                        continue;
                    }
                    aluno.setCurso(curso);
                    try {
                        aluno = alunoDAO.salvar(aluno);
                        System.out.println("Salvou aluno");
                    } catch (PersistenceException e) {
                        System.out.println(e.getMessage());
                        System.err.println("NAO SALVOU O ALUNO");
                        aluno = alunoDAO.encontrar(aluno.getId());
                        if (aluno == null) {
                            System.err.println(" >>>>> Nao achou o aluno que tbm nao conseguiu inserir!! <<<<<<");
                            continue;
                        }
                    }
                    matricula.setAluno(aluno);
                    matricula.setTurma(turma);
                    matricula.setMedia(Double.parseDouble(dadosMatricula[2]));
                    matricula.setNumeroFaltas(Double.parseDouble(dadosMatricula[3]));
                    matricula.setSituacao(dadosMatricula[4]);
                    try {
                        matriculaDAO.salvar(matricula);
                        System.out.println("Salvou matricula");
                    } catch (PersistenceException e) {
                        System.err.println("NAO SALVOU A MATRICULA");
                    }
                }
                lerArq.close();
            } catch (IOException ioe) {
                System.out.println(ioe.getLocalizedMessage());
            }
        }
    }

    @Override
    public void atualizarListaDeMatriculas() {
    }
}
