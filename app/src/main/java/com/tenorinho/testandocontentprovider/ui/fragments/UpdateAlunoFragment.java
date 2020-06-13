package com.tenorinho.testandocontentprovider.ui.fragments;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.tenorinho.testandocontentprovider.R;
import com.tenorinho.testandocontentprovider.data.AlunosHelperDB;
import com.tenorinho.testandocontentprovider.models.Aluno;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.COLUNA_NOME;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.COLUNA_SOBRENOME;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.COLUNA_TURMA;

public class UpdateAlunoFragment extends Fragment {

    private EditText edtNome;
    private TextView txtTitle;
    private EditText edtSobrenome;
    private EditText edtTurma;
    private Button btnAddAluno;
    private int id;

    private UpdateAlunoFragment(){}
    public static UpdateAlunoFragment novaInstancia(){return new UpdateAlunoFragment();}

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater, parent, savedInstanceState);
        View layout = inflater.inflate(R.layout.fragment_add_update_aluno, parent, false);
        txtTitle = layout.findViewById(R.id.txtTitle);
        edtNome = layout.findViewById(R.id.edtTxtNome);
        edtSobrenome = layout.findViewById(R.id.edtTxtSobrenome);
        edtTurma = layout.findViewById(R.id.edtTxtTurma);
        btnAddAluno = layout.findViewById(R.id.btnAddAluno);
        txtTitle.setText("Atualizar");
        btnAddAluno.setText("ATUALIZAR ALUNO");
        Aluno a = this.getArguments().getParcelable("args");
        id = a.getID();
        edtNome.setText(a.getNome());
        edtSobrenome.setText(a.getSobrenome());
        edtTurma.setText(a.getTurma());
        btnAddAluno.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                UpdateInDB();
            }
        });
        setRetainInstance(true);
        return layout;
    }
    private void UpdateInDB(){
        if (IsEditTextNull()){
            Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(COLUNA_NOME, edtNome.getText().toString());
        cv.put(COLUNA_SOBRENOME, edtSobrenome.getText().toString());
        cv.put(COLUNA_TURMA, edtTurma.getText().toString());

        getActivity().getContentResolver().update(AlunosHelperDB.CONTENT_URI
                .buildUpon()
                .appendPath(String.valueOf(id))
                .build(), cv, null, null);
        Toast.makeText(getActivity(), "Aluno atualizado", Toast.LENGTH_SHORT).show();
    }
    private boolean IsNullOrWhiteSpace(String value){
        String res = value.trim();
        return res.length() <= 0;
    }
    private boolean IsEditTextNull(){
        String v1 = edtNome.getText().toString();
        String v2 = edtSobrenome.getText().toString();
        String v3 = edtTurma.getText().toString();

        if(IsNullOrWhiteSpace(v1)){
            return true;
        }
        if(IsNullOrWhiteSpace(v2)){
            return true;
        }
        if(IsNullOrWhiteSpace(v3)){
            return true;
        }
        return false;
    }
}
