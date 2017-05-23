package com.google.maps.android.utils.demo;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MaintenanceFragment extends Fragment {

    private boolean eqpupkeepclickstate = false;
    private boolean faultremediationclickstate = false;
    EditText SamCode;

    public MaintenanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maintenance, container, false);
        ImageButton fault_button = (ImageButton) view.findViewById(R.id.fault_remediation_img_button);
        ImageButton upkeep_button = (ImageButton) view.findViewById(R.id.eqp_upkeep_img_button);

        fault_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("FAULT MAINTENANCE");
                alertDialog.setMessage("Enter SAM CODE");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String samcode = input.getText().toString();
                                if ((samcode.trim()).equals("3KGP-01")) {
                                    Intent intent = new Intent(getActivity(), MaintenanceActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(),"No details found for this SAM.. Enter a different SAM Code", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();

            }
        });

        return view;
    }
}
