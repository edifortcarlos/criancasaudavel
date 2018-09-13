package ao.co.najareal.vaciname.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ao.co.najareal.vaciname.model.AlturaCrianca;
import ao.co.najareal.vaciname.model.Crianca;
import ao.co.najareal.vaciname.model.CriancaVacina;
import ao.co.najareal.vaciname.model.PesoCrianca;
import ao.co.najareal.vaciname.model.TemperaturaCrianca;

@Dao
public interface CriancaDao {
    
    @Insert
    long insert(Crianca crianca);
    
    @Update
    void update(Crianca crianca);
    
    @Update
    void update(Crianca... vacinas);
    
    @Delete
    void delete(Crianca crianca);

    @Delete
    void delete(Crianca... vacinas);

    @Query("Select * from crianca order by nome")
    LiveData<List<Crianca>> getAll();

    @Query("Select * from crianca where id = :id")
    Crianca getCriancaById(int id);

    // --------------------------------- CriancaVacina -------------------------------

    @Insert
    void insert(CriancaVacina crianca);

    @Update
    void update(CriancaVacina crianca);

    @Delete
    void delete(CriancaVacina crianca);

    @Delete
    void delete(CriancaVacina... vacinas);

    @Query("Select * from criancavacina where idCrianca =:idCrianca ")
    List<CriancaVacina> getAllVacinas(int idCrianca);

    // Traz as criancas com vacinas que esteja atrasadas
    // para as proximas o parametro data deve ser a data actual + os dias de alerta da cofiguração
    // Para as atrasadas o parametro data deve ser a data actual apenas.
    @Query("Select distinct(idCrianca) from criancavacina where dataDaAdministracao <:dataActual and estado = :estado")
    List<Integer> getAllCriancaComVacinasAtrasadas(long dataActual,String estado);

    // Traz as vacinas que esteja atrasadas
    // para as proximas o parametro data deve ser a data actual + os dias de alerta da cofiguração
    // Para as atrasadas o parametro data deve ser a data actual apenas.
    // Para ser vacina atrasada o estado deve ser Agendada
    @Query("Select * from criancavacina where idCrianca =:idCrianca and dataDaAdministracao < :dataActual and estado =:estado")
    List<CriancaVacina> getAllVacinasAtrasadas(int idCrianca,long dataActual,String estado);

    @Query("Select * from criancavacina where idCrianca =:idCrianca and dataDaAdministracao < :dataActual and estado =:estado")
    LiveData<List<CriancaVacina>> getAllVacinasAtrasadasLiveData(int idCrianca,long dataActual,String estado);

    @Query("Select count(*) from criancavacina where idCrianca =:idCrianca and dataDaAdministracao < :dataActual and estado =:estado")
    LiveData<Integer> getQtdVacinasAtrasadasLiveData(int idCrianca,long dataActual,String estado);

    @Query("Select count(*) from criancavacina where idCrianca =:idCrianca and dataDaAdministracao < :dataActual and estado =:estado")
    Integer getQtdVacinasAtrasadas(int idCrianca,long dataActual,String estado);

    @Query("Select * from criancavacina where idCrianca =:idCrianca ")
    LiveData<List<CriancaVacina>> getAllVacinasLiveData(int idCrianca);

    @Query("Select * from criancavacina where idCrianca =:idCrianca and idVacina = :idVacina")
    CriancaVacina getCriancaVacina(int idCrianca, int idVacina);

    @Query("Select * from criancavacina where idCrianca =:idCrianca and idVacina =:idVacina and dose =:dose")
    CriancaVacina criancaVacinaDose(int idCrianca,int idVacina, String dose);

    @Query("Select * from criancavacina where idCrianca =:idCrianca and idVacina not in (:idsVacina)")
    LiveData<List<CriancaVacina>> getVacinasForaDaLista(int idCrianca,List<Integer> idsVacina); // vacinas que a criança nao tem na lista

    @Query("Select idVacina from criancavacina where idCrianca =:idCrianca")
    List<Integer> getIdsVacinasDaLista(int idCrianca); // vacinas que a criança nao tem na lista

    @Query("Select idVacina from criancavacina where idCrianca =:idCrianca")
    LiveData<List<Integer>> getIdsVacinasDaListaLiveData(int idCrianca); // vacinas que a criança nao tem na lista

    @Query("Select * from criancavacina where idCrianca =:idCrianca and estado = :estado")
    LiveData<List<CriancaVacina>> getAllVacinasByEstado(int idCrianca, String estado);

    // --------------------------------- Peso ---------------------------------------

    @Insert
    long insert(PesoCrianca obj);

    @Update
    void update(PesoCrianca obj);

    @Delete
    void delete(PesoCrianca obj);

    @Delete
    void delete(PesoCrianca ... obj);

    @Query("Select * from pesocrianca where idCrianca =:idCrianca order by data desc ")
    LiveData<List<PesoCrianca>> getPesosById(int idCrianca);

    @Query("Select * from pesocrianca where idCrianca =:idCrianca order by data asc ")
    List<PesoCrianca> getPesosByIdGrafico(int idCrianca);

    @Query("Select * from pesocrianca where idCrianca =:idCrianca and data = (Select max(p.data) from pesocrianca p where p.idCrianca =:idCrianca)")
    PesoCrianca getUltimoPeso(int idCrianca);

    @Query("Select * from pesocrianca where idCrianca =:idCrianca and data = (Select max(p.data) from pesocrianca p where p.idCrianca =:idCrianca)")
    LiveData<PesoCrianca> getUltimoPesoLiveData(int idCrianca);





    // --------------------------------- Altura ---------------------------------------

    @Insert
    long insert(AlturaCrianca obj);

    @Update
    void update(AlturaCrianca obj);

    @Delete
    void delete(AlturaCrianca obj);

    @Delete
    void delete(AlturaCrianca ... obj);

    @Query("Select * from alturacrianca where idCrianca =:idCrianca  order by data asc")
    List<AlturaCrianca> getAlturasById(int idCrianca);

    @Query("Select * from alturacrianca where idCrianca =:idCrianca  order by data asc")
    AlturaCrianca getUltimaAlturaRegistada(int idCrianca);


    @Query("Select * from alturacrianca where idCrianca =:idCrianca and data = (select max(a.data) from alturacrianca a where a.idCrianca = :idCrianca and a.data <:dataActual)")
    AlturaCrianca getAlturaAnterior(int idCrianca,long dataActual);


    @Query("Select * from alturacrianca where idCrianca =:idCrianca and altura = (Select max(altura) from alturacrianca where idCrianca =:idCrianca) ")
    AlturaCrianca getAlturaMaxima(int idCrianca);


    // --------------------------------- Temperatura ---------------------------------------

    @Insert
    long insert(TemperaturaCrianca obj);

    @Update
    void update(TemperaturaCrianca obj);

    @Delete
    void delete(TemperaturaCrianca obj);

    @Delete
    void delete(TemperaturaCrianca ... obj);

    @Query("Select * from temperaturacrianca where idCrianca =:idCrianca  order by data asc")
    List<TemperaturaCrianca> getTemperaturasById(int idCrianca);
}
