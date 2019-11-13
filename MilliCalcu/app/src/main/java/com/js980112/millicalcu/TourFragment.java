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
import android.widget.Toast;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TourFragment extends Fragment {
    public TourFragment(){

    }
    EditText etEvent,etDate,etNowS,etNowT,etYoung,etLive;
    NumberPicker npF1,npFf1,npFF1,npF2,npFf2,npFF2,npF3,npFf3,npFF3;
    TextView tvDay,etPan,etNeedT,etTotal,etTicket,etStamina;
    Button btn1,btn2;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int pan,needT;
    NumberPicker[] nps=new NumberPicker[9];
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour, container, false);

        preferences=getActivity().getSharedPreferences("tour", Context.MODE_PRIVATE);

        etEvent=view.findViewById(R.id.etEvent);
        etDate=view.findViewById(R.id.etDate);
        etPan=view.findViewById(R.id.etPan);
        etNeedT=view.findViewById(R.id.etNeedT);
        etNowS=view.findViewById(R.id.etNowS);
        etNowT=view.findViewById(R.id.etNowT);
        etYoung=view.findViewById(R.id.etYoung);
        etTicket=view.findViewById(R.id.etTicket);
        etLive=view.findViewById(R.id.etLive);
        etStamina=view.findViewById(R.id.etStamina);
        etTotal=view.findViewById(R.id.etTotal);

        npF1=view.findViewById(R.id.etF1);
        npFf1=view.findViewById(R.id.etFf1);
        npFF1=view.findViewById(R.id.etFF1);

        npF2=view.findViewById(R.id.etF2);
        npFf2=view.findViewById(R.id.etFf2);
        npFF2=view.findViewById(R.id.etFF2);

        npF3=view.findViewById(R.id.etF3);
        npFf3=view.findViewById(R.id.etFf3);
        npFF3=view.findViewById(R.id.etFF3);

        tvDay=view.findViewById(R.id.tvDay);

        btn1=view.findViewById(R.id.btn1);
        btn2=view.findViewById(R.id.btn2);

        npF1.setValueChangedListener(new NumPickerListener());
        npFf1.setValueChangedListener(new NumPickerListener());
        npFF1.setValueChangedListener(new NumPickerListener());

        npF2.setValueChangedListener(new NumPickerListener());
        npFf2.setValueChangedListener(new NumPickerListener());
        npFF2.setValueChangedListener(new NumPickerListener());

        npF3.setValueChangedListener(new NumPickerListener());
        npFf3.setValueChangedListener(new NumPickerListener());
        npFF3.setValueChangedListener(new NumPickerListener());

        nps[0]=npF1;
        nps[1]=npFf1;
        nps[2]=npFF1;
        nps[3]=npF2;
        nps[4]=npFf2;
        nps[5]=npFF2;
        nps[6]=npF3;
        nps[7]=npFf3;
        nps[8]=npFF3;

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
                editor.putInt("F2",npF2.getValue());
                editor.putInt("Ff2",npFf2.getValue());
                editor.putInt("FF2",npFF2.getValue());
                editor.putInt("F3",npF3.getValue());
                editor.putInt("Ff3",npFf3.getValue());
                editor.putInt("FF3",npFF3.getValue());

                editor.putString("name",etEvent.getText().toString());
                editor.putString("date",etDate.getText().toString());

                editor.putInt("nowS",Integer.parseInt(etNowS.getText().toString()));
                editor.putInt("nowT",Integer.parseInt(etNowT.getText().toString()));

                editor.putInt("live",Integer.parseInt(etLive.getText().toString()));
                editor.putInt("young",Integer.parseInt(etYoung.getText().toString()));

                editor.putBoolean("save",true);

                editor.apply();
            }
        });

        etLive.addTextChangedListener(new TextChange());
        etYoung.addTextChangedListener(new TextChange());
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
            npF2.setValue(preferences.getInt("F2",0));
            npFf2.setValue(preferences.getInt("Ff2",0));
            npFF2.setValue(preferences.getInt("FF2",0));
            npF3.setValue(preferences.getInt("F3",0));
            npFf3.setValue(preferences.getInt("Ff3",0));
            npFF3.setValue(preferences.getInt("FF3",0));

            etEvent.setText(preferences.getString("name",""));
            etDate.setText(preferences.getString("date",""));

            etNowS.setText(preferences.getInt("nowS",0)+"");
            etNowT.setText(preferences.getInt("nowT",0)+"");

            etLive.setText(preferences.getInt("live",0)+"");
            etYoung.setText(preferences.getInt("young",0)+"");
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

    private class NumPickerListener implements ValueChangedListener{
        @Override
        public void valueChanged(int value, ActionEnum action) {
            pan=0;
            needT=0;
            for(int i=0;i<nps.length;i++) {
                pan += nps[i].getValue();
                if(i<3)
                    needT+=nps[i].getValue();
                else if(i<6)
                    needT+=nps[i].getValue()*2;
                else if(i<9)
                    needT+=nps[i].getValue()*3;
            }
            etPan.setText("총 "+pan+"판");
            etNeedT.setText("필요 티켓 : "+needT);

            setTotal();
        }
    }
    private void setTotal(){
        int total=0;

        total+=Integer.parseInt(etNowS.getText().toString());

        total+=Integer.parseInt(etLive.getText().toString())*140;
        total+=Integer.parseInt(etYoung.getText().toString())*68;

        total+=nps[0].getValue()*556;
        total+=nps[1].getValue()*648;
        total+=nps[2].getValue()*720;
        total+=nps[3].getValue()*556*2;
        total+=nps[4].getValue()*648*2;
        total+=nps[5].getValue()*720*2;
        total+=nps[6].getValue()*556*3;
        total+=nps[7].getValue()*648*3;
        total+=nps[8].getValue()*720*3;

        etTotal.setText("총 점수 : "+total+"점");
    }
    private class TextChange implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                setTotal();
                etStamina.setText("스테미나 : " + (Integer.parseInt(etLive.getText().toString()) + Integer.parseInt(etYoung.getText().toString())) * 30);
                etTicket.setText("티켓 : " + (Double.parseDouble(etLive.getText().toString()) + Double.parseDouble(etYoung.getText().toString()) )/ 6 + "개");
                etTicket.append("\n날짜 획득 : "+diffDays2*2+"개");
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
                etTicket.append("\n날짜 획득 : "+diffDays2*2+"개");
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
