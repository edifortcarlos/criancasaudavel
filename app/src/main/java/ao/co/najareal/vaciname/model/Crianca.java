package ao.co.najareal.vaciname.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Crianca implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(index = true)
    private String nome;

    private float peso;

    @NonNull
    private long dataDeNascimento;

    private String naturalidade;

    private String nacionalidade;

    private String mae;

    private String pai;

    private String morada;

    private String localDoParto;

    private String tipoDeParto; // Cesariana , Normal

    private int apgar; // vai de 1 a 5 e mostra o nivel de atividade fisica da criança ao nascimento

    private String unidadeHOspitalar;

    private String responsavelPeloParto;

    @NonNull
    private String sexo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public long getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(long dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getLocalDoParto() {
        return localDoParto;
    }

    public void setLocalDoParto(String localDoParto) {
        this.localDoParto = localDoParto;
    }

    public String getTipoDeParto() {
        return tipoDeParto;
    }

    public void setTipoDeParto(String tipoDeParto) {
        this.tipoDeParto = tipoDeParto;
    }

    public int getApgar() {
        return apgar;
    }

    public void setApgar(int apgar) {
        this.apgar = apgar;
    }

    public String getUnidadeHOspitalar() {
        return unidadeHOspitalar;
    }

    public void setUnidadeHOspitalar(String unidadeHOspitalar) {
        this.unidadeHOspitalar = unidadeHOspitalar;
    }

    public String getResponsavelPeloParto() {
        return responsavelPeloParto;
    }

    public void setResponsavelPeloParto(String responsavelPeloParto) {
        this.responsavelPeloParto = responsavelPeloParto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crianca crianca = (Crianca) o;
        return getId() == crianca.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
