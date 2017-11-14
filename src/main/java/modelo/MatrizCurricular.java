package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MatrizCurricular {

    @Id
    private Integer id;
    
    private String nomeMatriz;
    private Curso curso;
    
    @OneToMany
    private List<MatrizDisciplina> disciplinasNaMatriz;
    
    public MatrizCurricular(String nomeMatriz) {
        this.nomeMatriz = nomeMatriz;
        this.disciplinasNaMatriz = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeMatriz() {
        return nomeMatriz;
    }

    public void setNomeMatriz(String nomeMatriz) {
        this.nomeMatriz = nomeMatriz;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<MatrizDisciplina> getDisciplinasNaMatriz() {
        return disciplinasNaMatriz;
    }

    public void setDisciplinasNaMatriz(List<MatrizDisciplina> disciplinasNaMatriz) {
        this.disciplinasNaMatriz = disciplinasNaMatriz;
    }

    public void adicionarDisciplina(Disciplina disciplina, String natureza, Integer semestreIdeal) {
        MatrizDisciplina disciplinaRelacionada = new MatrizDisciplina(this, disciplina);
        disciplinaRelacionada.setNaturezaDisciplina(natureza);
        disciplinaRelacionada.setSemestreIdeal(semestreIdeal);
        this.disciplinasNaMatriz.add(disciplinaRelacionada);
        disciplina.adicionarMatrizRelacionada(disciplinaRelacionada);
    }
    
    public MatrizDisciplina getDisciplina(String codigo){
        for(MatrizDisciplina matrizDisciplina: this.disciplinasNaMatriz){
            if(matrizDisciplina.getDisciplina().getCodigo().equals(codigo)){
                return matrizDisciplina;
            }
        }
        return null;
    }
}
