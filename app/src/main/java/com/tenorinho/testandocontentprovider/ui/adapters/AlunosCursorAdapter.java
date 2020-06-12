package com.tenorinho.testandocontentprovider.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.tenorinho.testandocontentprovider.R;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.COLUNA_ID;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.COLUNA_NOME;

public class AlunosCursorAdapter extends CursorAdapter {

    public AlunosCursorAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }
    public static class ViewHolder {
        public final TextView txtID;
        public final TextView txtNomeAluno;

        public ViewHolder(View view){
            txtID = view.findViewById(R.id.txtID);
            txtNomeAluno = view.findViewById(R.id.txtNomeAluno);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_alunos, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();

        String nome = cursor.getString(cursor.getColumnIndex(COLUNA_NOME));
        String id = cursor.getString(cursor.getColumnIndex(COLUNA_ID));

        viewHolder.txtID.setText(id);
        viewHolder.txtNomeAluno.setText(nome);
    }
}
