package ao.co.najareal.vaciname.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ao.co.najareal.vaciname.model.Vacina;

@Dao
public interface VacinaDao {

    @Insert
    void insert(Vacina vacina);

    @Update
    void update(Vacina vacina);

    @Update
    void update(Vacina... vacinas);

    @Delete
    void delete(Vacina vacina);

    @Delete
    void delete(Vacina... vacinas);

    @Query("Select * from vacina")
    LiveData<List<Vacina>> getAll();

    @Query("Select * from vacina")
    List<Vacina> getAllList();

    @Query("Select * from vacina where id = :id")
    Vacina getVacinaById(int id);

    @Query("Select * from vacina where nome like :nome")
    LiveData<List<Vacina>> getVacinaByNome(String nome);

}
