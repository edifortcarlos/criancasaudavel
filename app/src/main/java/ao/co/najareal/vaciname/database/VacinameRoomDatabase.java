package ao.co.najareal.vaciname.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import ao.co.najareal.vaciname.model.AlturaCrianca;
import ao.co.najareal.vaciname.model.Campanha;
import ao.co.najareal.vaciname.model.Crianca;
import ao.co.najareal.vaciname.model.CriancaVacina;
import ao.co.najareal.vaciname.model.PesoCrianca;
import ao.co.najareal.vaciname.model.TemperaturaCrianca;
import ao.co.najareal.vaciname.model.Vacina;

@Database(entities = {Crianca.class, Vacina.class,
        CriancaVacina.class, Campanha.class,
        TemperaturaCrianca.class, PesoCrianca.class, AlturaCrianca.class}, version = 1)
public abstract class VacinameRoomDatabase extends RoomDatabase {

    private static VacinameRoomDatabase INSTANCE;
    public static boolean CARREGADO = false;

    public static VacinameRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VacinameRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VacinameRoomDatabase.class, "vaciname_database")
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new DadosIniciais.Populate(INSTANCE).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            CARREGADO = true;
        }
    };


    public abstract VacinaDao vacinaDao();

    public abstract CampanhaDao campanhaDao();

    public abstract MedidasCriancaDao medidasCriancaDao();

    public abstract CriancaDao criancaDao();
}
