package com.js980112.millicalcu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TheaterFragment extends Fragment {
    public TheaterFragment(){

    }
    EditText etEvent,etDate,etNowS,etNowT,etLive;
    NumberPicker npF1,npFf1,npFF1,np1,np2,np3;
    TextView tvDay,etPan,etNeedT,etTotal,etTicket,etStamina;
    Button btn1,btn2;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    int pan,needT;
    NumberPicker[] nps=new NumberPicker[6];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theater, container, false);

        preferences=getActivity().getSharedPreferences("theater", Context.MODE_PRIVATE);

        etEvent=view.findViewById(R.id.etEvent);
        etDate=view.findViewById(R.id.etDate);
        etPan=view.findViewById(R.id.etPan);
        etNeedT=view.findViewById(R.id.etNeedT);
        etNowS=view.findViewById(R.id.etNowS);
        etNowT=view.findViewById(R.id.etNowT);
        etTicket=view.findViewById(R.id.etTicket);
        etLive=view.findViewById(R.id.etLive);
        etStamina=view.findViewById(R.id.etStamina);
        etTotal=view.findViewById(R.id.etTotal);

        npF1=view.findViewById(R.id.etF1);
        npFf1=view.findViewById(R.id.etFf1);
        npFF1=view.findViewById(R.id.etFF1);

        np1=view.findViewById(R.id.et1);
        np2=view.findViewById(R.id.et2);
        np3=view.findViewById(R.id.et3);

        tvDay=view.findViewById(R.id.tvDay);

        btn1=view.findViewById(R.id.btn1);
        btn2=view.findViewById(R.id.btn2);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg=new AlertDialog.Builder(getActivity());
                dlg.setTitle("정말 초기화 합니까?");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor=preferences.edit();
                        editor.clear();
                        editor.apply();
                        refresh();
                    }
                });
                dlg.setNegativeButton("아니오",null);
                dlg.show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor=preferences.edit();

                editor.putInt("F1",npF1.getValue());
                editor.putInt("Ff1",npFf1.getValue());
                editor.putInt("FF1",npFF1.getValue());

                editor.putInt("np1",np1.getValue());
                editor.putInt("np2",np2.getValue());
                editor.putInt("np3",np3.getValue());

                editor.putString("name",etEvent.getText().toString());
                editor.putString("date",etDate.getText().toString());

                editor.putInt("nowS",Integer.parseInt(etNowS.getText().toString()));
                editor.putInt("nowT",Integer.parseInt(etNowT.getText().toString()));

                editor.putInt("live",Integer.parseInt(etLive.getText().toString()));

                editor.putBoolean("save",true);

                editor.apply();
            }
        });

        nps[0]=np1;
        nps[1]=np2;
        nps[2]=np3;
        nps[3]=npF1;
        nps[4]=npFf1;
        nps[5]=npFF1;

        npF1.setValueChangedListener(new NumPickerListener2());
        npFf1.setValueChangedListener(new NumPickerListener2());
        npFF1.setValueChangedListener(new NumPickerListener2());

        np1.setValueChangedListener(new NumPickerListener(1));
        np2.setValueChangedListener(new NumPickerListener(2));
        np3.setValueChangedListener(new NumPickerListener(4));

        etLive.addTextChangedListener(new TextChange());
        etNowS.addTextChangedListener(new TextChange());

        etDate.addTextChangedListener(new TextChange2());

        Button btnDate=view.findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), listener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();
            }
        });

        if(preferences.getBoolean("save",false)){
            npF1.setValue(preferences.getInt("F1",0));
            npFf1.setValue(preferences.getInt("Ff1",0));
            npFF1.setValue(preferences.getInt("FF1",0));
            np1.setValue(preferences.getInt("np1",0));
            np2.setValue(preferences.getInt("np2",0));
            np3.setValue(preferences.getInt("np3",0));

            etEvent.setText(preferences.getString("name",""));
            etDate.setText(preferences.getString("date",""));

            etNowS.setText(preferences.getInt("nowS",0)+"");
            etNowT.setText(preferences.getInt("nowT",0)+"");

            etLive.setText(preferences.getInt("live",0)+"");
        }


        return view;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String mon=(monthOfYear+1)+"";
            String day=dayOfMonth+"";
            if(monthOfYear<10)
                mon="0"+mon;
            if(dayOfMonth<10)
                day="0"+day;

            etDate.setText(year+"-"+mon+"-"+day);
        }
    };
    private void refresh(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }

    private class NumPickerListener implements ValueChangedListener {
        int k;
        public NumPickerListener(int k){
            this.k=k;
        }
        @Override
        public void valueChanged(int value, ActionEnum action) {
            pan=0;
            needT=0;

            pan+=nps[0].getValue();
            pan+=nps[1].getValue();
            pan+=nps[2].getValue();

            needT+=nps[0].getValue()*180;
            needT+=nps[1].getValue()*360;
            needT+=nps[2].getValue()*720;

            etPan.setText("총 "+pan+"판");
            etNeedT.setText("필요 재화 : "+needT);

            setTotal();
        }
    }
    private class NumPickerListener2 implements ValueChangedListener {
        @Override
        public void valueChanged(int value, ActionEnum action) {
            setTotal();

            double stamina=Integer.parseInt(etLive.getText().toString())*30+npF1.getValue()*30+npFf1.getValue()*150+npFF1.getValue()*300;
            double tickets=Integer.parseInt(etLive.getText().toString())+npF1.getValue()*60+npFf1.getValue()*298+npFF1.getValue()*595;

            etStamina.setText("스테미나 : " + stamina);
            etTicket.setText("재화 : " + tickets + "개");
            etTicket.append("\n날짜 획득 : "+diffDays2*360+"개");
        }
    }
    private void setTotal(){
        int total=0;

        total+=Integer.parseInt(etNowS.getText().toString());

        total+=Integer.parseInt(etLive.getText().toString())*85;

        total+=nps[0].getValue()*537;
        total+=nps[1].getValue()*1074;
        total+=nps[2].getValue()*2148;

        total+=nps[3].getValue()*537;
        total+=nps[4].getValue()*298;
        total+=nps[5].getValue()*595;

        etTotal.setText("총 점수 : "+total+"점");
    }
    private class TextChange implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                setTotal();
                double stamina=Integer.parseInt(etLive.getText().toString())*30+npF1.getValue()*30+npFf1.getValue()*150+npFF1.getValue()*300;
                double tickets=Integer.parseInt(etLive.getText().toString())+npF1.getValue()*60+npFf1.getValue()*298+npFF1.getValue()*595;

                etStamina.setText("스테미나 : " + stamina);
                etTicket.setText("재화 : " + tickets + "개");
                etTicket.append("\n날짜 획득 : "+diffDays2*360+"개");
            }catch (Exception e){

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    float diffDays2;
    private class TextChange2 implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            long diff2;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date todayDate = new Date();
            try {
                Date endDate = formatter.parse(etDate.getText().toString());
                diff2 = endDate.getTime() - todayDate.getTime();
                diffDays2 = diff2 / (24 * 60 * 60 * 1000)+1;
                tvDay.setText("D-"+(int)diffDays2);
                etTicket.append("\n날짜 획득 : "+diffDays2*360+"개");
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
