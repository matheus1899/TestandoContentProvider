package com.tenorinho.testandocontentprovider.ui.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import com.tenorinho.testandocontentprovider.INavigation;
import com.tenorinho.testandocontentprovider.R;
import com.tenorinho.testandocontentprovider.data.AlunosHelperDB;
import com.tenorinho.testandocontentprovider.models.Aluno;
import com.tenorinho.testandocontentprovider.ui.adapters.AlunosCursorAdapter;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.CONTENT_URI;

public class AlunosFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lista;
    private AlunosCursorAdapter adapter;
    private AlunosFragment(){}
    public static AlunosFragment novaInstancia(){return new AlunosFragment();}
    @Override public void onCreate(Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        getActivity().getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add:
                Log.i("TAG", "onOptionsItemSelected");
                Activity act = getActivity();
                if(act instanceof INavigation){
                    INavigation listener = (INavigation) act;
                    listener.NavigateTo_AddFragment();
                }
                break;
            default:
                Toast.makeText(getActivity(), "Default", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    @Override public void onActivityCreated(Bundle savedInstanceState){
        getLoaderManager().initLoader(0,null, this);
        super.onActivityCreated(savedInstanceState);
    }
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater, parent, savedInstanceState);
        adapter = new AlunosCursorAdapter(getActivity(), null, 0);
        View v = inflater.inflate(R.layout.fragment_list_alunos, parent, false);
        lista = v.findViewById(R.id.listView);
        registerForContextMenu(lista);
        lista.setAdapter(adapter);
        setRetainInstance(true);
        return v;
    }
    @Override public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.fragment_alunos_context_menu, menu);
    }
    @Override public boolean onContextItemSelected(@NonNull MenuItem item){
        AdapterView.AdapterContextMenuInfo info;
        Cursor obj;
        switch (item.getItemId()){
            case R.id.action_excluir:
                Log.i("TAG", "onContextItemSelected");
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                obj = (Cursor)lista.getItemAtPosition(info.position);
                int id = obj.getInt(obj.getColumnIndex(AlunosHelperDB.COLUNA_ID));
                getContext().getContentResolver().delete(CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build(), null, null);
                Toast.makeText(getActivity(), "Excluido com sucesso", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_editar:
                Log.i("TAG", "onContextItemSelected");
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                obj = (Cursor)lista.getItemAtPosition(info.position);
                Aluno aluno = new Aluno(
                        obj.getInt(obj.getColumnIndex(AlunosHelperDB.COLUNA_ID)),
                        obj.getString(obj.getColumnIndex(AlunosHelperDB.COLUNA_NOME)),
                        obj.getString(obj.getColumnIndex(AlunosHelperDB.COLUNA_SOBRENOME)),
                        obj.getString(obj.getColumnIndex(AlunosHelperDB.COLUNA_TURMA))
                );
                Bundle b = new Bundle();
                b.putParcelable("args", aluno);
                Activity act = getActivity();
                if(act instanceof INavigation){
                    INavigation listener = (INavigation) act;
                    listener.NavigateTo_UpdateFragment(b);
                }
        }
        return super.onContextItemSelected(item);
    }
    @NonNull @Override public Loader onCreateLoader(int id, @Nullable Bundle args) {
        Log.i("TAG", "onCreateLoader");
        return new CursorLoader(getActivity(),
                CONTENT_URI,
                null,
                null,
                null,
                null);

    }
    @Override public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.i("TAG", "onLoadFinished");
        adapter.swapCursor(data);
    }
    @Override public void onLoaderReset(@NonNull Loader loader) {
        Log.i("TAG", "onLoaderReset");
        adapter.swapCursor(null);
    }
}