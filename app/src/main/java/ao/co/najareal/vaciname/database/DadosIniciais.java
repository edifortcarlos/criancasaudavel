package ao.co.najareal.vaciname.database;

import android.os.AsyncTask;

import java.util.Date;

import ao.co.najareal.vaciname.model.Campanha;
import ao.co.najareal.vaciname.model.Vacina;
import ao.co.najareal.vaciname.model.util.Categoria;
import ao.co.najareal.vaciname.model.util.EstadoCampanha;

public class DadosIniciais {

    protected static class Populate extends AsyncTask<Void, Void, Void> {

        VacinameRoomDatabase db;

        public Populate(VacinameRoomDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            VacinameRoomDatabase.CARREGADO = false;

            Vacina v = new Vacina();
            v.setNome("Polio");
            v.setDose("Zero Dose");
            v.setIdadeRecomendada("Ao Nascer");
            v.setIdadeRecomendadaint(0);
            v.setCategoria(Categoria.PNV);
            v.setInformacoes("A vacina contra a poliomielite, também conhecida como VIP ou VOP, é uma vacina que torna a criança protegida contra 3 tipos diferentes do vírus que causa esta doença, conhecida popularmente como paralisia infantil.");
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("BCG");
            v.setDose("Dose Unica");
            v.setIdadeRecomendada("Ao Nascer");
            v.setIdadeRecomendadaint(0);
            v.setCategoria(Categoria.PNV);
            v.setInformacoes("A vacina BCG protege os pequenos contra a tuberculose, principalmente contra as formas mais graves de tuberculose, como tuberculose miliar e meningite tuberculosa.");
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Hipatite B");
            v.setDose("Dose Unica");
            v.setIdadeRecomendada("Ao Nascer");
            v.setIdadeRecomendadaint(0);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Polio");
            v.setDose("1ª Dose");
            v.setIdadeRecomendada("2 meses");
            v.setIdadeRecomendadaint(2);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Pentavalente");
            v.setDose("1ª Dose");
            v.setIdadeRecomendada("2 meses");
            v.setIdadeRecomendadaint(2);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Pneumo");
            v.setDose("1ª Dose");
            v.setIdadeRecomendada("2 meses");
            v.setIdadeRecomendadaint(2);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("RotaVirus");
            v.setDose("1ª Dose");
            v.setIdadeRecomendada("2 meses");
            v.setIdadeRecomendadaint(2);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);


            v = new Vacina();
            v.setNome("Polio");
            v.setDose("2ª Dose");
            v.setIdadeRecomendada("4 meses");
            v.setIdadeRecomendadaint(4);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);


            v = new Vacina();
            v.setNome("Pentavalente");
            v.setDose("2ª Dose");
            v.setIdadeRecomendada("4 meses");
            v.setIdadeRecomendadaint(4);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);


            v = new Vacina();
            v.setNome("Pneumo");
            v.setDose("2ª Dose");
            v.setIdadeRecomendada("4 meses");
            v.setIdadeRecomendadaint(4);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("RotaVirus");
            v.setDose("2ª Dose");
            v.setIdadeRecomendada("4 meses");
            v.setIdadeRecomendadaint(4);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Polio");
            v.setDose("3ª Dose");
            v.setIdadeRecomendada("6 meses");
            v.setIdadeRecomendadaint(6);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Pentavalente");
            v.setDose("3ª Dose");
            v.setIdadeRecomendada("6 meses");
            v.setIdadeRecomendadaint(6);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Pneumo");
            v.setDose("3ª Dose");
            v.setIdadeRecomendada("6 meses");
            v.setIdadeRecomendadaint(6);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Vitamina A");
            v.setDose("1ª Dose");
            v.setIdadeRecomendada("6 meses");
            v.setIdadeRecomendadaint(6);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Sarampo");
            v.setDose("1ª Dose");
            v.setIdadeRecomendada("9 meses");
            v.setIdadeRecomendadaint(9);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);


            v = new Vacina();
            v.setNome("Febre Amarela");
            v.setDose("Dose Unica");
            v.setIdadeRecomendada("9 meses");
            v.setIdadeRecomendadaint(9);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Vitamina A");
            v.setDose("2ª Dose");
            v.setIdadeRecomendada("9 meses");
            v.setIdadeRecomendadaint(9);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);

            v = new Vacina();
            v.setNome("Sarampo");
            v.setDose("2ª Dose");
            v.setIdadeRecomendada("15 meses");
            v.setIdadeRecomendadaint(15);
            v.setCategoria(Categoria.PNV);
            db.vacinaDao().insert(v);




            Campanha campanha = new Campanha();
            Date dataInicio = new Date();
            dataInicio.setDate(dataInicio.getDate() - 100);
            Date dataFim =  new Date();
            dataFim.setDate(dataFim.getDate() - 97);
            campanha.setNome("Campanha Abril");
            campanha.setDataInicio(dataInicio.getTime());
            campanha.setDataFim(dataFim.getTime());
            campanha.setEstado(EstadoCampanha.FEITA);
            campanha.setVacinas("Polio , Sarampo");
            campanha.setProvincias("Luanda, Malanje, Bié");
            db.campanhaDao().insert(campanha);

            campanha = new Campanha();
            dataInicio = new Date();
            dataInicio.setDate(dataInicio.getDate() - 50);
            dataFim = new Date();
            dataFim.setDate(dataFim.getDate() - 47);
            campanha.setNome("Campanha Junho");
            campanha.setDataInicio(dataInicio.getTime());
            campanha.setDataFim(dataFim.getTime());
            campanha.setEstado(EstadoCampanha.FEITA);
            campanha.setVacinas("Polio 1, Varicela");
            campanha.setProvincias("Luanda, Moxico, Bié");
            db.campanhaDao().insert(campanha);

            campanha = new Campanha();
            dataInicio = new Date();
            dataInicio.setDate(dataInicio.getDate()-5);
            dataFim =  new Date();
            dataFim.setDate(dataFim.getDate() - 2);
            campanha.setNome("Campanha Julho");
            campanha.setDataInicio(dataInicio.getTime());
            campanha.setDataFim(dataFim.getTime());
            campanha.setEstado(EstadoCampanha.EM_CURSO);
            campanha.setVacinas("Febre Amarela , Varicela");
            campanha.setProvincias("Luanda, Moxico, Bié");
            db.campanhaDao().insert(campanha);


            VacinameRoomDatabase.CARREGADO = true;
            return null;
        }
    }
}
