package ao.co.najareal.vaciname.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ao.co.najareal.vaciname.model.TemperaturaCrianca;

@Dao
public interface MedidasCriancaDao {

    @Insert
    void insert(TemperaturaCrianca temperaturaCrianca);

    @Update
    void update(TemperaturaCrianca temperaturaCrianca);

    @Update
    void update(TemperaturaCrianca... vacinas);

    @Delete
    void delete(TemperaturaCrianca temperaturaCrianca);

    @Delete
    void delete(TemperaturaCrianca... vacinas);

    @Query("Select * from TemperaturaCrianca")
    LiveData<List<TemperaturaCrianca>> getAll();

    @Query("Select * from TemperaturaCrianca")
    List<TemperaturaCrianca> getAllList();

    @Query("Select * from TemperaturaCrianca where id = :id")
    TemperaturaCrianca getMedidasCriancaById(int id);

    @Query("Select * from TemperaturaCrianca where idCrianca = :idCrianca")
    LiveData<List<TemperaturaCrianca>> getMedidasCriancaByIdCrianca(int idCrianca);

}
