package com.js980112.millicalcu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.js980112.millicalcu.R;

public class BeforeFragment extends Fragment {
    EditText etScore,etCount,et1,et2,et3;
    Button btn2;
    TextView tv,tv2;
    public BeforeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_before, container, false);
        etScore=view.findViewById(R.id.etScore);
        etCount=view.findViewById(R.id.etCount);
        et1=view.findViewById(R.id.et1);
        et2=view.findViewById(R.id.et2);
        et3=view.findViewById(R.id.et3);
        tv=view.findViewById(R.id.tv);
        tv2=view.findViewById(R.id.tv2);
        btn2=view.findViewById(R.id.btn2);

        etScore.addTextChangedListener(new WactherListener());
        et1.addTextChangedListener(new WactherListener());
        et2.addTextChangedListener(new WactherListener());
        et3.addTextChangedListener(new WactherListener());

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float count=Integer.parseInt(etCount.getText().toString());
                et1.setText((int)count*4/7+"");
                et2.setText((int)count*2/7+"");
                et3.setText((int)count*1/7+"");
            }
        });
        return view;
    }

    public class WactherListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int point=0;
            int coin=0,a1,a2,a3,count=0;
            try{
                point+=Integer.parseInt(etScore.getText().toString());
                point+=(Integer.parseInt(et1.getText().toString())*537+Integer.parseInt(et2.getText().toString())*1074+Integer.parseInt(et3.getText().toString())*2148);
                count+=Integer.parseInt(et1.getText().toString())*18/17+Integer.parseInt(et1.getText().toString())*18/17*2+Integer.parseInt(et1.getText().toString())*18/17*4;
                coin=Integer.parseInt(et1.getText().toString())*180+Integer.parseInt(et2.getText().toString())*360+Integer.parseInt(et3.getText().toString())*720;
            }catch (Exception e){
                e.printStackTrace();
                point=0;
                count=0;
            }
            tv.setText("점수 : "+point+"\n필요 플레이횟수 : "+count+"\n필요 재화 : "+coin);
            try {
                tv2.setText("판수 : "+(Integer.parseInt(et1.getText().toString()) + Integer.parseInt(et2.getText().toString()) + Integer.parseInt(et3.getText().toString()) )+ " ");
            }catch (Exception e){

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
