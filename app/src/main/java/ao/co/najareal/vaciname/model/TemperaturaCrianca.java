package ao.co.najareal.vaciname.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = Crianca.class,parentColumns = "id",childColumns = "idCrianca",onDelete = ForeignKey.CASCADE))
public class TemperaturaCrianca {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private int idCrianca;

    private long data;

    private float temperatura;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public int getIdCrianca() {
        return idCrianca;
    }

    public void setIdCrianca(@NonNull int idCrianca) {
        this.idCrianca = idCrianca;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }
}
