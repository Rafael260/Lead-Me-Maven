/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes_unidade;

import excecoes.AutenticacaoException;
import excecoes.DataException;
import modelo.Aluno;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import service.LoginService;

/**
 *
 * @author rafao
 */
public class AlunoTest {

    private Aluno aluno;
     private static LoginService loginService;

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws DataException, AutenticacaoException {
        loginService = LoginService.getInstance();
         aluno = loginService.autenticar("201602345", "ciclano");
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

//    @Ignore @Test
//    public void testDisciplinasPagas() {
//        Disciplina disciplina = new Disciplina();
//        disciplina.setCodigo("IMD0038");
//        assertTrue(aluno.pagouMateria(disciplina,true));
//        disciplina.setCodigo("IMD0040");
//        assertTrue(aluno.pagouMateria(disciplina,true));
//    }

    @Test
    public void testMatrizCurricular() {
//        assertEquals(aluno.getMatrizCurricular(), "sig");
    }

//    @Ignore @Test
//    public void testPreRequisito() {
//        Disciplina disciplina = aluno.getCurso().getDisciplina(aluno.getMatrizCurricular(), "IMD0040").getDisciplina();
//        assertFalse(aluno.podePagar(disciplina));
//        disciplina = aluno.getCurso().getDisciplina(aluno.getMatrizCurricular(), "DIM0600").getDisciplina();
//        assertTrue(aluno.podePagar(disciplina));
//    }
}
