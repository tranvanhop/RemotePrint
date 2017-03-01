/*
 * Hewlett-Packard Company
 * All rights reserved.
 *
 * This file, its contents, concepts, methods, behavior, and operation
 * (collectively the "Software") are protected by trade secret, patent,
 * and copyright laws. The use of the Software is governed by a license
 * agreement. Disclosure of the Software to third parties, in any form,
 * in whole or in part, is expressly prohibited except as authorized by
 * the license agreement.
 */

package com.hp.mss.print.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hp.mss.print.R;
import com.hp.mss.print.helper.SessionManager;

public class TabFragmentSystemInfoLayout extends Fragment implements View.OnFocusChangeListener{

    EditText edtName;
    EditText edtAddress;
    EditText edtCashier;
    EditText edtVersion;

    Button btnCancel;
    Button btnSave;

    SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_system_info_layout, container, false);

        edtName = (EditText) inflatedView.findViewById(R.id.edtName);
        edtAddress = (EditText) inflatedView.findViewById(R.id.edtAddress);
        edtCashier = (EditText) inflatedView.findViewById(R.id.edtCashier);
        edtVersion = (EditText) inflatedView.findViewById(R.id.edtVersion);

        btnCancel = (Button) inflatedView.findViewById(R.id.btnCancel);
        btnSave = (Button) inflatedView.findViewById(R.id.btnSave);

        session = new SessionManager(getActivity());

        init();

        return inflatedView;
    }

    private void init(){
        edtName.setText(session.getKeyCompanyName());
        edtAddress.setText(session.getKeyCompanyAddress());
        edtCashier.setText(session.getKeyCompanyCashier());
        edtVersion.setText(session.getKeyCompanyVersion());

        edtName.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        edtAddress.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        edtCashier.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        edtVersion.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

        edtName.setTextColor(getActivity().getResources().getColor(R.color.input_login));
        edtAddress.setTextColor(getActivity().getResources().getColor(R.color.input_login));
        edtCashier.setTextColor(getActivity().getResources().getColor(R.color.input_login));
        edtVersion.setTextColor(getActivity().getResources().getColor(R.color.input_login));

        edtName.setOnFocusChangeListener(this);
        edtAddress.setOnFocusChangeListener(this);
        edtCashier.setOnFocusChangeListener(this);
        edtVersion.setOnFocusChangeListener(this);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText(session.getKeyCompanyName());
                edtAddress.setText(session.getKeyCompanyAddress());
                edtCashier.setText(session.getKeyCompanyCashier());
                edtVersion.setText(session.getKeyCompanyVersion());

                edtName.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtAddress.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtCashier.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtVersion.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

                Toast.makeText(getActivity(), "Đã hủy thành công !", Toast.LENGTH_LONG).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setKeyCompanyName(edtName.getText().toString());
                session.setKeyCompanyAddress(edtAddress.getText().toString());
                session.setKeyCompanyCashier(edtCashier.getText().toString());
                session.setKeyCompanyVersion(edtVersion.getText().toString());

                edtName.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtAddress.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtCashier.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtVersion.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

                Toast.makeText(getActivity(), "Lưu thành công !", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus)
            v.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        else {
            v.setBackgroundColor(getActivity().getResources().getColor(R.color.HPLightGreyBackground));
        }
    }
}
