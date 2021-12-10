package com.example.fitnesstracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BmiCalculator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BmiCalculator extends Fragment {
    LinearLayout layout;
    EditText weight, height;
    TextView Tremark, TBMI;
    Button Calculate;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BmiCalculator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BmiCalculator.
     */
    // TODO: Rename and change types and number of parameters
    public static BmiCalculator newInstance(String param1, String param2) {
        BmiCalculator fragment = new BmiCalculator();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmi_calculator, container, false);
        layout = (LinearLayout) view.findViewById(R.id.viewData);
        height = (EditText) view.findViewById(R.id.height);
        weight = (EditText) view.findViewById(R.id.weight);
        Calculate = (Button) view.findViewById(R.id.saveDetails);
        TBMI = (TextView) view.findViewById(R.id.showbmi);
        Tremark = (TextView) view.findViewById(R.id.showremark);

        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ht = height.getText().toString();
                String wt = weight.getText().toString();
                String Remark=null;
                if (ht.trim().length()>0 && wt.trim().length()>0){
                    layout.setVisibility(View.VISIBLE);
                    double hts = Double.parseDouble(ht);
                    double wts = Double.parseDouble(wt);
                    double BMI = (double) (wts / Math.pow(hts/100,2));
                    if (BMI<16.0) Remark = "Severely Underweight";
                    if (BMI>16.0 && BMI<18.4) Remark = "Underweight";
                    if (BMI>18.5 && BMI<24.9) Remark = "Normal";
                    if (BMI>25.0 && BMI<29.9) Remark = "Overweight";
                    if (BMI>30.0 && BMI<34.9) Remark = "Moderately Obese";
                    if (BMI>35.0 && BMI<39.9) Remark = "Severely Obese";
                    if (BMI>40.0) Remark = "Morbidly Obese";

                   TBMI.setText(String.format("%.2f",BMI));
                    Tremark.setText(Remark);

                }

            }
        });




        return view;
    }
}