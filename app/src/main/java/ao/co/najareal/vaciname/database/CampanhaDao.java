package ao.co.najareal.vaciname.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ao.co.najareal.vaciname.model.Campanha;

@Dao
public interface CampanhaDao {

    @Insert
    void insert(Campanha campanha);

    @Update
    void update(Campanha campanha);

    @Update
    void update(Campanha... vacinas);

    @Delete
    void delete(Campanha campanha);

    @Delete
    void delete(Campanha... vacinas);

    @Query("Select * from campanha")
    LiveData<List<Campanha>> getAll();

    @Query("Select * from campanha order by estado,dataInicio,dataFim")
    List<Campanha> getAllList();

    @Query("Select * from campanha where id = :id")
    Campanha getCampanhaById(int id);

    @Query("Select * from campanha where estado like :estado")
    List<Campanha> getCampanhaByEstado(String estado);

}
