package com.marlonluan.anuncieseucarro.carro;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.marlonluan.anuncieseucarro.*;

public class CarroDialogFragment extends DialogFragment
        implements TextView.OnEditorActionListener {
    private static final String DIALOG_TAG = "editDialog";
    private static final String EXTRA_Carro = "carro";
    private EditText txtNome;
    private EditText txtEndereco;
    private RatingBar rtbEstrelas;
    private EditText txtValor;
    private Carro mCarro;
    public static CarroDialogFragment newInstance(Carro carro) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_Carro, carro);
        CarroDialogFragment dialog = new CarroDialogFragment();
        dialog.setArguments(bundle);
        return dialog;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCarro = (Carro)getArguments().getSerializable(EXTRA_Carro);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(
                R.layout.fragment_dialog_carro, container, false);
        txtNome = (EditText) layout.findViewById(R.id.txtNome);
        txtNome.requestFocus();
        txtEndereco = (EditText)layout.findViewById(R.id.txtEndereco);
        txtEndereco.setOnEditorActionListener(this);
        rtbEstrelas = (RatingBar)layout.findViewById(R.id.rtbEstrelas);
        txtValor = (EditText)layout.findViewById(R.id.txtValor);
        if (mCarro != null) {
            txtNome.setText(mCarro.nome);
            txtEndereco.setText(mCarro.endereco);
            rtbEstrelas.setRating(mCarro.estrelas);
            txtValor.setText(String.valueOf(mCarro.valor));
        }
        // Exibe o teclado virtual ao exibir o Dialog
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(R.string.acao_novo);
        return layout;
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            Activity activity = getActivity();
            if (activity instanceof AoSalvarCarro) {
                if (mCarro == null) {
                    mCarro = new Carro(
                            txtNome.getText().toString(),
                            txtEndereco.getText().toString(),
                            rtbEstrelas.getRating(),
                            Double.valueOf(txtValor.getText().toString()));
                } else {
                    mCarro.nome = txtNome.getText().toString();
                    mCarro.endereco = txtEndereco.getText().toString();
                    mCarro.estrelas = rtbEstrelas.getRating();
                    mCarro.valor = Double.valueOf(txtValor.getText().toString());
                }
                AoSalvarCarro listener = (AoSalvarCarro) activity;
                listener.salvouCarro(mCarro);
                // Feche o dialog
                dismiss();
                return true;
            }
        }
        return false;
    }
    public void abrir(FragmentManager fm) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG);
        }
    }
    public interface AoSalvarCarro {
        void salvouCarro(Carro carro);
    }
}

