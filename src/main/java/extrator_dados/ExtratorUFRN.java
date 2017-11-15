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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import modelo.Curso;
import modelo.Disciplina;
import modelo.MatrizCurricular;

/**
 *
 * @author rafao
 */
public class ExtratorUFRN extends Extrator {

    public static final String PASTA_DADOS_ABERTOS = "dadosUFRN";
    public static final String SEPARADOR_CSV = ";";
    private CursoDAO cursoDAO;
    private MatrizCurricularDAO matrizCurricularDAO;
    private DisciplinaDAO disciplinaDAO;
    
    public ExtratorUFRN() {
        cursoDAO = CursoDAO.getInstance();
        matrizCurricularDAO = MatrizCurricularDAO.getInstance();
        disciplinaDAO = DisciplinaDAO.getInstance();
    }

    private boolean camposValidos(String[] linha, int... indices) {
        for (int i : indices) {
            if (linha[i] == null || linha[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void prepararCampos(String[] dados, int... indices) {
        for (int i : indices) {
            dados[i] = dados[i].replace("\"", "");
        }
    }

    @Override
    public void atualizarListaDeCursos() {
        try {
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "/cursos-graduacao.csv"), "UTF-8"));
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
                    System.out.println(e.getLocalizedMessage());
                    System.out.println("Registro nao inserido no banco");
                }
            }
            lerArq.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
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
                dadosMatriz = linha.split(SEPARADOR_CSV);
                if(!camposValidos(dadosMatriz, 0, 2, 3, 4)){
                    continue;
                }
                prepararCampos(dadosMatriz, 0, 2, 3, 4);
                matriz = new MatrizCurricular();
                matriz.setId(Integer.parseInt(dadosMatriz[0]));
                matriz.setNomeMatriz(dadosMatriz[2]);
                curso = cursoDAO.encontrar(Integer.parseInt(dadosMatriz[3]));
                if(curso == null){
                    System.err.println("Curso não encontrado para essa matriz");
                    continue;
                }
                matriz.setCurso(curso);
                matrizCurricularDAO.salvar(matriz);
            }
            lerArq.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void atualizarListaDeDisciplinas() {
        BufferedReader lerArq;
        try {
            lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(PASTA_DADOS_ABERTOS + "/componentes-curriculares-presenciais.csv"), "UTF-8"));
            String linha = lerArq.readLine();
            String[] dadosDisciplina;
            Disciplina disciplina;
            while ((linha = lerArq.readLine()) != null) {
                dadosDisciplina = linha.split(SEPARADOR_CSV);
                if(dadosDisciplina.length <= 19){
                    continue;
                }
                if(!camposValidos(dadosDisciplina, 0, 2, 3, 4, 9)){
                    continue;
                }
                
                prepararCampos(dadosDisciplina, 0, 2, 3, 4, 9, 16, 17, 18);
                //So interessa as disciplinas de graduacao
                if(!dadosDisciplina[3].toUpperCase().equals("G")){
                    continue;
                }
                disciplina = new Disciplina();
                disciplina.setId(Integer.parseInt(dadosDisciplina[0]));
                disciplina.setCodigo(dadosDisciplina[2]);
                disciplina.setNome(dadosDisciplina[4]);
                disciplina.setCargaHoraria(Integer.parseInt(dadosDisciplina[9]));
                disciplina.setEquivalencias(dadosDisciplina[16] == null ? "" : dadosDisciplina[16]);
                disciplina.setPreRequisitos(dadosDisciplina[17] == null ? "" : dadosDisciplina[17]);
                disciplina.setCoRequisitos(dadosDisciplina[18] == null ? "" : dadosDisciplina[18]);
                disciplinaDAO.salvar(disciplina);
            }
            lerArq.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExtratorUFRN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void atualizarListaDeTurmas() {
    }

    @Override
    public void atualizarListaDeAlunos() {
    }

    @Override
    public void atualizarListaDeComponentesDasMatrizes() {
    }

    @Override
    public void atualizarListaDeMatriculas() {
    }

}
