package ao.co.najareal.vaciname.ui.util;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataPickerListnerData implements DatePickerDialog.OnDateSetListener, View.OnFocusChangeListener {

    private EditText txtData;
    private Date date;
    private Date dataObtida;

    public Date getDataObtida() {
        if (dataObtida == null) {
            return new Date();
        }
        return dataObtida;
    }

    public DataPickerListnerData(EditText txtData, Date data) {
        this.txtData = txtData;
        this.date = data;
    }

    public DataPickerListnerData(EditText txtData, long date) {
        this.txtData = txtData;
        this.date = new Date(date);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        dataObtida = calendar.getTime();

        //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        //txtData.setText(sdf.format(dataObtida));
        txtData.setText(sdf.format(dataObtida));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {

            Date data = new Date();

            int ano;

            if (this.date != null) {
                data = date;
                ano = data.getYear()+1900;
            }else{
                ano = data.getYear() + 1900;
            }

            int mes = data.getMonth();
            int dia = data.getDate();

            DatePickerDialog dpd = new DatePickerDialog(v.getContext(), this, ano, mes, dia);
            //dpd.getDatePicker().setMinDate(new Date().getTime()-3784320000000L);// data actual - 120 anos
            dpd.getDatePicker().setMaxDate(new Date().getTime());

            dpd.show();
        }
        //new DatePickerDialog(AutomovelActivity.this, this, 2008, 02, 29).show();
    }
}

