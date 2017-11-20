package modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={    
    @UniqueConstraint(columnNames = {"turma_id", "aluno_id"})})
public class Matricula implements Serializable{

    @Id
    @GeneratedValue
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;
    
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    
    private Double numeroFaltas;

    private Double media;
    private String situacao;

    public Matricula() {
    }

    public Matricula(Turma turma, Aluno aluno) {
        this.turma = turma;
        this.aluno = aluno;
        numeroFaltas = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
    public Double getNumeroFaltas() {
        return numeroFaltas;
    }

    public void setNumeroFaltas(Double numeroPresencas) {
        this.numeroFaltas = numeroPresencas;
    }
    
    //TODO remover metodo e usar somente o metodo do service
    public boolean situacaoAprovada(){
        return situacao.startsWith("APR");
    }
}
