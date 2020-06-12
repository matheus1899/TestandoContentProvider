package com.tenorinho.testandocontentprovider.ui.fragments;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.tenorinho.testandocontentprovider.R;
import com.tenorinho.testandocontentprovider.data.AlunosHelperDB;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.COLUNA_NOME;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.COLUNA_SOBRENOME;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.COLUNA_TURMA;

public class AddAlunoFragment extends Fragment {

    private EditText edtNome;
    private EditText edtSobrenome;
    private EditText edtTurma;
    private Button btnAddAluno;

    private AddAlunoFragment(){}
    public static AddAlunoFragment novaInstancia(){return new AddAlunoFragment();}

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater, parent, savedInstanceState);
        View layout = inflater.inflate(R.layout.fragment_add_update_aluno, parent, false);
        edtNome = layout.findViewById(R.id.edtTxtNome);
        edtSobrenome = layout.findViewById(R.id.edtTxtSobrenome);
        edtTurma = layout.findViewById(R.id.edtTxtTurma);
        btnAddAluno = layout.findViewById(R.id.btnAddAluno);

        btnAddAluno.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                InsertInDB();
            }
        });
        setRetainInstance(true);
        return layout;
    }
    private void InsertInDB(){
        if (IsEditTextNull()){
            Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(COLUNA_NOME, edtNome.getText().toString());
        cv.put(COLUNA_SOBRENOME, edtSobrenome.getText().toString());
        cv.put(COLUNA_TURMA, edtTurma.getText().toString());

        getActivity().getContentResolver().insert(AlunosHelperDB.CONTENT_URI, cv);
        Toast.makeText(getActivity(), "Aluno adicionado", Toast.LENGTH_SHORT).show();
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
