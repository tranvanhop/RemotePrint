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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hp.mss.print.R;
import com.hp.mss.print.helper.SessionManager;

public class TabFragmentProductListLayout extends Fragment {

    EditText edtName;
    EditText edtAddress;
    EditText edtCashier;
    EditText edtVersion;

    Button btnName;
    Button btnAddress;
    Button btnCashier;
    Button btnVersion;

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

        btnName = (Button) inflatedView.findViewById(R.id.btnName);
        btnAddress = (Button) inflatedView.findViewById(R.id.btnAddress);
        btnCashier = (Button) inflatedView.findViewById(R.id.btnCashier);
        btnVersion = (Button) inflatedView.findViewById(R.id.btnVersion);

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

        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtName.isEnabled()) {
                    btnName.setText(R.string.text_cancel);
                    edtName.setBackgroundColor(getActivity().getResources().getColor(R.color.HPLightGreyBackground));
                }
                else {
                    btnName.setText(R.string.text_edit);
                    edtName.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                }

                edtName.setEnabled(!edtName.isEnabled());
                edtName.setTextColor(getActivity().getResources().getColor(R.color.input_login));
            }
        });

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtAddress.isEnabled()) {
                    btnAddress.setText(R.string.text_cancel);
                    edtAddress.setBackgroundColor(getActivity().getResources().getColor(R.color.HPLightGreyBackground));
                }
                else {
                    btnAddress.setText(R.string.text_edit);
                    edtAddress.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                }

                edtAddress.setEnabled(!edtAddress.isEnabled());
                edtAddress.setTextColor(getActivity().getResources().getColor(R.color.input_login));
            }
        });

        btnCashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtCashier.isEnabled()) {
                    btnCashier.setText(R.string.text_cancel);
                    edtCashier.setBackgroundColor(getActivity().getResources().getColor(R.color.HPLightGreyBackground));
                }
                else {
                    btnCashier.setText(R.string.text_edit);
                    edtCashier.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                }

                edtCashier.setEnabled(!edtCashier.isEnabled());
                edtCashier.setTextColor(getActivity().getResources().getColor(R.color.input_login));
            }
        });

        btnVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtVersion.isEnabled()) {
                    btnVersion.setText(R.string.text_cancel);
                    edtVersion.setBackgroundColor(getActivity().getResources().getColor(R.color.HPLightGreyBackground));
                }
                else {
                    btnVersion.setText(R.string.text_edit);
                    edtVersion.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                }

                edtVersion.setEnabled(!edtVersion.isEnabled());
                edtVersion.setTextColor(getActivity().getResources().getColor(R.color.input_login));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText(session.getKeyCompanyName());
                edtAddress.setText(session.getKeyCompanyAddress());
                edtCashier.setText(session.getKeyCompanyCashier());
                edtVersion.setText(session.getKeyCompanyVersion());

                btnName.setText(R.string.text_edit);
                btnAddress.setText(R.string.text_edit);
                btnCashier.setText(R.string.text_edit);
                btnVersion.setText(R.string.text_edit);

                edtName.setEnabled(false);
                edtAddress.setEnabled(false);
                edtCashier.setEnabled(false);
                edtVersion.setEnabled(false);

                edtName.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtAddress.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtCashier.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtVersion.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

                edtName.setTextColor(getActivity().getResources().getColor(R.color.input_login));
                edtAddress.setTextColor(getActivity().getResources().getColor(R.color.input_login));
                edtCashier.setTextColor(getActivity().getResources().getColor(R.color.input_login));
                edtVersion.setTextColor(getActivity().getResources().getColor(R.color.input_login));

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

                btnName.setText(R.string.text_edit);
                btnAddress.setText(R.string.text_edit);
                btnCashier.setText(R.string.text_edit);
                btnVersion.setText(R.string.text_edit);

                edtName.setEnabled(false);
                edtAddress.setEnabled(false);
                edtCashier.setEnabled(false);
                edtVersion.setEnabled(false);

                edtName.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtAddress.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtCashier.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                edtVersion.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

                edtName.setTextColor(getActivity().getResources().getColor(R.color.input_login));
                edtAddress.setTextColor(getActivity().getResources().getColor(R.color.input_login));
                edtCashier.setTextColor(getActivity().getResources().getColor(R.color.input_login));
                edtVersion.setTextColor(getActivity().getResources().getColor(R.color.input_login));

                Toast.makeText(getActivity(), "Lưu thành công !", Toast.LENGTH_LONG).show();
            }
        });
    }
}
