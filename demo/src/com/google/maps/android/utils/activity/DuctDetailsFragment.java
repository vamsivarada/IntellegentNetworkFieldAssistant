package com.google.maps.android.utils.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.maps.android.utils.demo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by raj.a.natarajan on 5/4/2017.
 */
/*
This activity creates a form which must be filled by the network field surveyor while surveying the field
*/

public class DuctDetailsFragment extends Fragment{
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    private Spinner ownerSpinner,cableSpinner;
    public static String ductid;
    public static String selectedRadioIdvalue;
    private static final String[]owners = {"NBN", "TELSTRA"};
    int cableloc = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        return inflater.inflate(R.layout.fragment_duct_details, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            updateDetailsView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            updateDetailsView(mCurrentPosition);
        }
    }

    public void updateDetailsView(final int position) {

        ownerSpinner = (Spinner)getActivity().findViewById(R.id.ownerSpinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,owners);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownerSpinner.setAdapter(adapter);

        int temp=position * 8;
        EditText ductIdText = (EditText) getActivity().findViewById(R.id.ductIdText);
        EditText ductLengthText = (EditText) getActivity().findViewById(R.id.ductLengthText);
        EditText ductSizeText = (EditText) getActivity().findViewById(R.id.ductSizeText);
        EditText ductMaterialText = (EditText) getActivity().findViewById(R.id.ductMaterialText);
        EditText ductCapacityText = (EditText) getActivity().findViewById(R.id.ductCapacityText);
        EditText maxMandrelText = (EditText) getActivity().findViewById(R.id.maxMandrelText);
        EditText matrixCodeText = (EditText) getActivity().findViewById(R.id.matrixCodeText);
        final EditText cableTypeText = (EditText) getActivity().findViewById(R.id.cableTypeText);
        cableTypeText.setKeyListener(null);
        ductIdText.setText(ListItems.DuctId.get(position));
        ductIdText.setKeyListener(null);
        ductid=ListItems.DuctId.get(position);
        ductLengthText.setText(ListItems.Details.get(temp));
        ductSizeText.setText(ListItems.Details.get(++temp));
        ductMaterialText.setText(ListItems.Details.get(++temp));
        ductCapacityText.setText(ListItems.Details.get(++temp));
        maxMandrelText.setText(ListItems.Details.get(++temp));
        matrixCodeText.setText(ListItems.Details.get(++temp));
        if (ListItems.Details.get(++temp).equals("TELSTRA")){
            ownerSpinner.setSelection(1);
        } else
        {
            ownerSpinner.setSelection(0);

        }
        int cablepos = position;


        for(int i=0;i<cablepos;i++){
            cableloc=((Integer.parseInt(ListItems.CableCount.get(i)))*2)+cableloc;
        }
        final List<String> tmplist=new ArrayList<String>();
        tmplist.clear();
        int cap=cableloc+(Integer.parseInt(ListItems.CableCount.get(cablepos))*2);
        for(int i=cableloc;i<cap;i=i+2){
            tmplist.add(ListItems.CableIDs.get(i));
        }

        cableSpinner = (Spinner)getActivity().findViewById(R.id.proposedCableIDSpinner);
        ArrayAdapter<String> adp= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,tmplist);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cableSpinner.setAdapter(adp);

        cableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int displaycable=cableSpinner.getSelectedItemPosition();
                cableTypeText.setText(ListItems.CableIDs.get(cableloc+(displaycable*2)+1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for(int i = 0; i< ListItems.DuctId.size(); i++){
            System.out.println(ListItems.DuctId.get(i));
        }

        ImageButton incident_button = (ImageButton) getActivity().findViewById(R.id.incidentButton);

        final DialogInterface.OnClickListener dialogClickListener_incident = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent myIntent = new Intent(getActivity(), RaiseAssetIncidentActivity.class);
                        myIntent.putExtra("AssetID", ListItems.DuctId.get(position));
                        getActivity().startActivity(myIntent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        incident_button.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Incident");
                alert.setMessage("Do you want to report an incident?");
                alert.setPositiveButton("YES",dialogClickListener_incident);
                alert.setNegativeButton("NO",dialogClickListener_incident);
                alert.show();
            }
        });

        Button uploadButton = (Button) getActivity().findViewById(R.id.nextButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RadioGroup radioDecissionGroup = (RadioGroup)getActivity().findViewById(R.id.radioDecission);
                int selectedRadioId = radioDecissionGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)getActivity().findViewById(selectedRadioId);
                selectedRadioIdvalue = (String) radioButton.getText();
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Save");
                alert.setMessage("Are you sure to proceed?");
                alert.setPositiveButton("YES",dialogClickListener);
                alert.setNegativeButton("NO",dialogClickListener);
                alert.show();

            }
        });
        mCurrentPosition = position;
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent myIntent = new Intent(getActivity(), CaptureImageActivity.class);
                    myIntent.putExtra("AssetId", ductid);
                    myIntent.putExtra("selectedValue", selectedRadioIdvalue);
                    getActivity().startActivity(myIntent);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION, mCurrentPosition);
    }



}
