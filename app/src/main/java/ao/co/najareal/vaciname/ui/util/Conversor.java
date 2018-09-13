package ao.co.najareal.vaciname.ui.util;

import android.content.res.Resources;

import java.util.Date;

import ao.co.najareal.vaciname.R;

public class Conversor {

    public static String longParaIdade(long dataLong) {

        long anos = dataLong / 31_536_000_000L;
        long anosResto = dataLong % 31_536_000_000L;
        long meses = anosResto / 2_628_000_000L;
        long mesesResto = anosResto % 2_628_000_000L;
        long dias = mesesResto / 86_400_000L;
        long diasResto = mesesResto % 86_400_000L;
        long horas = diasResto / 3_600_000L;
        long horasResto = diasResto % 3_600_000L;
        long minutos = horasResto / 60_000L;
        long minutosResto = horasResto % 60_000L;
        long segundos = minutosResto / 1000;
        long milissegundo = minutosResto % 1000;

        String idade = "";

        if (anos >= 1) {
            idade += anos + " a ";
        }
        if (meses >= 1) {
            idade += meses + " m ";
        }
        if (dias >= 1) {
            idade += dias + " d ";
        }
        if (horas >= 1) {
            idade += horas + " h ";
        }
        if (minutos >= 1) {
            idade += minutos + " min ";
        }
        /*
        if (segundos >= 1) {
            idade += segundos + " seg ";
        }
        if (milissegundo >= 1) {
            idade += milissegundo + " miliseg ";
        }
*/
        return idade;
    }

    public static String longParaIdade(Date data1, Date data2) {
        if (data1.after(data2)) {
            return longParaIdade(data1.getTime() - data2.getTime());
        } else {
            return longParaIdade(data2.getTime() - data1.getTime());
        }
    }


    public static String longParaIdade(long data1, long data2) {
        if (data1 > (data2)) {
            return longParaIdade(data1 - data2);
        } else {
            return longParaIdade(data2 - data1);
        }
    }

    public static String longParaIdade(Date data, long data2) {
        long data1 = data.getTime();
        if (data1 > (data2)) {
            return longParaIdade(data1 - data2);
        } else {
            return longParaIdade(data2 - data1);
        }
    }

    public static String longParaIdade(long data2, Date data) {
        return longParaIdade(data, data2);
    }

    public static String mesNumeroMesTexto(int mes, Resources res) {
        mes++;
        switch (mes) {
            case 1:
                return res.getString(R.string.janeiro);
            case 2:
                return res.getString(R.string.fevereiro);
            case 3:
                return res.getString(R.string.marco);
            case 4:
                return res.getString(R.string.abril);
            case 5:
                return res.getString(R.string.maio);
            case 6:
                return res.getString(R.string.junho);
            case 7:
                return res.getString(R.string.julho);
            case 8:
                return res.getString(R.string.agosto);
            case 9:
                return res.getString(R.string.setembro);
            case 10:
                return res.getString(R.string.outubro);
            case 11:
                return res.getString(R.string.novembro);
            case 12:
                return res.getString(R.string.dezembro);
            default:
                return mes + " Mês invalido";
        }
    }

    public static int calcularIdadeEmMeses(long dataDeNascimentoEmMilissegundos){
        return (int)((new Date().getTime() - dataDeNascimentoEmMilissegundos)/2_628_000_000L);
    }

    public static long calcularIdadeMilisegundos(long dataDeNascimentoEmMilissegundos){
        return ((new Date().getTime() - dataDeNascimentoEmMilissegundos));
    }

}
