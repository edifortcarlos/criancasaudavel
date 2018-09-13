package ao.co.najareal.vaciname.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"idVacina","idCrianca","dose"},
            foreignKeys = {@ForeignKey(entity = Vacina.class,parentColumns = "id",childColumns = "idVacina"),
                            @ForeignKey(entity = Crianca.class,parentColumns = "id",childColumns = "idCrianca",onDelete = ForeignKey.CASCADE),
            })
public class CriancaVacina {

    @NonNull
    private int idVacina;

    @NonNull
    private int idCrianca;

    @NonNull
    private String dose;

    private long data;

    private String estado;// (Feita, Agendada)

    private String unidadeSanitaria;

    private long dataDaAdministracao; // se o estado for feita , a data so pode ser mudada ate 7 dias depois

    public int getIdVacina() {
        return idVacina;
    }

    public void setIdVacina(int idVacina) {
        this.idVacina = idVacina;
    }

    public int getIdCrianca() {
        return idCrianca;
    }

    public void setIdCrianca(int idCrianca) {
        this.idCrianca = idCrianca;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUnidadeSanitaria() {
        return unidadeSanitaria;
    }

    public void setUnidadeSanitaria(String unidadeSanitaria) {
        this.unidadeSanitaria = unidadeSanitaria;
    }

    public long getDataDaAdministracao() {
        return dataDaAdministracao;
    }

    public void setDataDaAdministracao(long dataDaAdministracao) {
        this.dataDaAdministracao = dataDaAdministracao;
    }
}
