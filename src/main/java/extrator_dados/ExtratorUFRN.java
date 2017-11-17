/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extrator_dados;

import base_dados.CursoDAO;
import base_dados.DisciplinaDAO;
import base_dados.MatrizCurricularDAO;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.persistence.PersistenceException;
import modelo.Curso;
import modelo.Disciplina;
import modelo.MatrizCurricular;
import util.ThreadUtil;

/**
 *
 * @author rafao
 */
public class ExtratorUFRN extends Extrator {

    public static final String PASTA_DADOS_ABERTOS = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\dadosUFRN";
    public static final String SEPARADOR_CSV = ";";
    private CursoDAO cursoDAO;
    private MatrizCurricularDAO matrizCurricularDAO;
    private DisciplinaDAO disciplinaDAO;

    public ExtratorUFRN() {
        cursoDAO = CursoDAO.getInstance();
        matrizCurricularDAO = MatrizCurricularDAO.getInstance();
        disciplinaDAO = DisciplinaDAO.getInstance();
    }

    public static boolean camposValidos(String[] linha, int... indices) {
        for (int i : indices) {
            if (linha[i] == null || linha[i].isEmpty() || linha[i].equals("\"\"")) {
                return false;
            }
        }
        return true;
    }

    public static void prepararCampos(String[] dados, int... indices) {
        for (int i : indices) {
            dados[i] = dados[i].replace("\"", "");
        }
    }

    @Override
    public void atualizarListaDeCursos() {
        try {
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "\\cursos-graduacao.csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dadosCurso;
            Curso curso;
            while ((linha = lerArq.readLine()) != null) {
                dadosCurso = linha.split(SEPARADOR_CSV);
                if (!camposValidos(dadosCurso, 0, 1, 4, 5, 6, 7)) {
                    continue;
                }
                prepararCampos(dadosCurso, 0, 1, 6, 7);
                curso = new Curso();
                curso.setId(Integer.parseInt(dadosCurso[0]));
                curso.setNome(dadosCurso[1]);
                curso.setGrauAcademico(dadosCurso[6]);
                curso.setModalidade(dadosCurso[7]);
                try {
                    cursoDAO.salvar(curso);
                } catch (PersistenceException e) {
                    System.out.println("Registro nao inserido no banco");
                }
            }
            lerArq.close();
            System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR OS CURSOS!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void atualizarListaDeMatrizesCurriculares() {
        BufferedReader lerArq;
        try {
            lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "\\estruturas-curriculares.csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dadosMatriz;
            MatrizCurricular matriz;
            Curso curso;
            while ((linha = lerArq.readLine()) != null) {
                dadosMatriz = linha.split(SEPARADOR_CSV);
                if (!camposValidos(dadosMatriz, 0, 2, 3, 4)) {
                    continue;
                }
                prepararCampos(dadosMatriz, 0, 2, 3, 4);
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
//        finally{
//            cursoDAO.fecharConexao();
//            matrizCurricularDAO.fecharConexao();
//        }
    }

    @Override
    public void atualizarListaDeDisciplinas() {
        BufferedReader lerArq;
        try {
            lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "\\componentes-curriculares-presenciais.csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dados;
            Disciplina disciplina;
            while ((linha = lerArq.readLine())!= null ) {
                dados = linha.split(SEPARADOR_CSV);
                System.out.println("Lendo linha da disciplina");
                if (dados.length <= 19 || !camposValidos(dados, 0, 2, 3, 4, 9)) {
                    continue;
                }

                prepararCampos(dados, 0, 2, 3, 4, 9, 16, 17, 18);
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
        List<Thread> threads = new ArrayList<>();
        Thread threadLeitor;
        for (int ano = 2011; ano <= 2017; ano++) {
            for (int periodo = 1; periodo <= 2; periodo++) {
                System.out.println("Lendo o arquivo de " + ano + "." + periodo);
                threadLeitor = new LeitorArquivoTurmaUFRN(ano, periodo);
                threadLeitor.start();
                threads.add(threadLeitor);
            }
        }
        ThreadUtil.esperarThreads(threads);
        System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR AS TURMAS!");
    }

    @Override
    public void atualizarListaDeAlunos() {
        List<Thread> threads = new ArrayList<>();
        Thread threadLeitor;
        for (int ano = 2011; ano <= 2017; ano++) {
            for (int periodo = 1; periodo <= 2; periodo++) {
                threadLeitor = new LeitorArquivoMatriculaUFRN(ano, periodo);
                threadLeitor.start();
                threads.add(threadLeitor);
            }
        }
        ThreadUtil.esperarThreads(threads);
        System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR OS ALUNOS!");
    }

    @Override
    public void atualizarListaDeComponentesDasMatrizes() {
        Path file = Paths.get(PASTA_DADOS_ABERTOS + "\\curriculo-componente-graduacao.csv");
        try {
            Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8);
            Iterator<String> iterator = lines.iterator();
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            Collection<Future> tasks = new LinkedList<>();
            iterator.next(); //header
            String linha;
            Future submit;
            while (iterator.hasNext()) {
                linha = iterator.next();
                submit = executorService.submit(new MatrizDisciplinaParser(linha));
                tasks.add(submit);
            }
            ThreadUtil.esperarThreads(tasks);
            System.out.println(">>>>>>>>>>>>>>>>>TERMINOU DE CONFERIR AS GRADES!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override // ja foi feito no metodo de atualizar alunos
    public void atualizarListaDeMatriculas() {
    }

}
