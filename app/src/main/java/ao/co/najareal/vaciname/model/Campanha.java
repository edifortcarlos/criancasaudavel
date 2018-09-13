package ao.co.najareal.vaciname.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity
public class Campanha {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nome;

    @NonNull
    private long dataInicio;

    @NonNull
    private long dataFim;

    private String provincias;

    private String vacinas;

    private String estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    @NonNull
    public long getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(@NonNull long dataInicio) {
        this.dataInicio = dataInicio;
    }

    @NonNull
    public long getDataFim() {
        return dataFim;
    }

    public void setDataFim(@NonNull long dataFim) {
        this.dataFim = dataFim;
    }

    public String getProvincias() {
        return provincias;
    }

    public void setProvincias(String provincias) {
        this.provincias = provincias;
    }

    public String getVacinas() {
        return vacinas;
    }

    public void setVacinas(String vacinas) {
        this.vacinas = vacinas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
