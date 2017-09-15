/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_dados;

import excecoes.DataException;
import excecoes.AutenticacaoException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import modelo.Aluno;
import modelo.Curso;
import modelo.Matricula;
import modelo.MatrizDisciplina;
import modelo.Turma;

/**
 *
 * @author rafao
 */
public class AlunoDAO extends AbstractDAO{
    
    private static AlunoDAO instance = new AlunoDAO();
    private CursoDAO cursoDAO;
    
    private AlunoDAO(){
        cursoDAO = CursoDAO.getInstance();
    }
    
    public static AlunoDAO getInstance(){
        return instance;
    }
    
    private boolean existeUsuario(String usuario, String senha) throws DataException {
        boolean existeUsuario = false;
        try {
            Map<String, String> logins = getLoginsESenhas();
            if (logins.containsKey(usuario) && logins.get(usuario).equals(senha)) {
                existeUsuario = true;
            }
        } catch (UnsupportedEncodingException ex) {
            throw new DataException(ex.getMessage());
        } catch (IOException ex) {
            throw new DataException(ex.getMessage());
        }
        return existeUsuario;
    }

    private Map<String, String> getLoginsESenhas() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        Map<String, String> logins = new HashMap<String, String>();
        BufferedReader lerArq;
        String linha;
        String[] dadosLinha;
        lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(DIRETORIO_RECURSOS + "logins/logins.txt"), "UTF-8"));
        while ((linha = lerArq.readLine()) != null) {
            dadosLinha = linha.split(";");
            logins.put(dadosLinha[0], dadosLinha[1]);
        }
        lerArq.close();
        return logins;
    }
    public Aluno carregarAluno(String usuario, String senha) throws DataException, AutenticacaoException{
        Aluno aluno = new Aluno();
        if (!existeUsuario(usuario,senha)){
            throw new AutenticacaoException("Usuário e/ou senha inválidos");
        }
        aluno.setNumeroMatricula(usuario);
        carregarHistoricoAluno(aluno);
        return aluno;
    }
    
    private void carregarHistoricoAluno(Aluno aluno) throws DataException {
        try {
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(DIRETORIO_RECURSOS + "historicos/" + aluno.getNumeroMatricula() + "-historico.txt"), "UTF-8"));
            aluno.setNome(lerArq.readLine());
            lerArq.readLine(); //ignorando a matricula
            String cursoComMatriz = lerArq.readLine();
            String nomeCurso = cursoComMatriz.split(" - ")[0];
            String matrizCurricular = cursoComMatriz.split(" - ")[1];
            Curso curso = cursoDAO.carregarCurso(nomeCurso);
            aluno.setCurso(curso);
            aluno.setMatrizCurricular(matrizCurricular);
            aluno.setIea(Double.parseDouble(lerArq.readLine()));
            aluno.setMcn(Double.parseDouble(lerArq.readLine()));
            String linha;
            //O aluno tem uma lista de matriculas
            Matricula matricula;
            MatrizDisciplina matrizDisciplina;
            Turma turma;
            String[] dadosDisciplina;
            while ((linha = lerArq.readLine()) != null) {
                dadosDisciplina = linha.split(";");
                matrizDisciplina = aluno.getCurso().getDisciplina(matrizCurricular, dadosDisciplina[1]);
                if (matrizDisciplina == null) {
                    continue;
                }
                turma = new Turma(dadosDisciplina[0], matrizDisciplina.getDisciplina());
                matricula = new Matricula(turma, aluno);
                matricula.setMedia(Double.parseDouble(dadosDisciplina[3]));
                matricula.setSituacao(dadosDisciplina[4]);
                //Contando as horas cumpridas  
                if (matricula.getSituacao().contains("APR")) {
                    if (matrizDisciplina.getNaturezaDisciplina().equals("OBRIGATORIO")) {
                        aluno.setCargaObrigatoriaCumprida(aluno.getCargaObrigatoriaCumprida() + matrizDisciplina.getDisciplina().getCargaHoraria());
                    } else {
                        aluno.setCargaOptativaCumprida(aluno.getCargaOptativaCumprida() + matrizDisciplina.getDisciplina().getCargaHoraria());
                    }
                }
                aluno.adicionarMatricula(matricula);
            }

            lerArq.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }
}
