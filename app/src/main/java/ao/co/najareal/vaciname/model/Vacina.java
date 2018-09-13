package ao.co.najareal.vaciname.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Vacina {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nome;

    @NonNull
    private String dose;

    @NonNull
    private String idadeRecomendada; // por extenso, meses ou anos

    @NonNull
    private long idadeRecomendadaint; // em meses, para efeito de calculos

    @NonNull
    private String categoria; // Plano Nacional, Vacina Extra, fora de uso

    private String informacoes;

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

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getIdadeRecomendada() {
        return idadeRecomendada;
    }

    public void setIdadeRecomendada(String idadeRecomendada) {
        this.idadeRecomendada = idadeRecomendada;
    }

    public long getIdadeRecomendadaint() {
        return idadeRecomendadaint;
    }

    public void setIdadeRecomendadaint(long idadeRecomendadaint) {
        // 2.592.000.000L é o valor de um mes em milissegundos
        this.idadeRecomendadaint = idadeRecomendadaint ;//* 2_592_000_000L;
    }

    @NonNull
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NonNull String categoria) {
        this.categoria = categoria;
    }

    public String getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }
}
