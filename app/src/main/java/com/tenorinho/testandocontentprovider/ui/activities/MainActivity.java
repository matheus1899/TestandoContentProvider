package com.tenorinho.testandocontentprovider.ui.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import com.tenorinho.testandocontentprovider.INavigation;
import com.tenorinho.testandocontentprovider.R;
import com.tenorinho.testandocontentprovider.ui.fragments.AddAlunoFragment;
import com.tenorinho.testandocontentprovider.ui.fragments.AlunosFragment;
import com.tenorinho.testandocontentprovider.ui.fragments.UpdateAlunoFragment;

public class MainActivity extends AppCompatActivity implements INavigation {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFragment(savedInstanceState);

    }
    private void initializeFragment(Bundle savedInstanceState){
        if(savedInstanceState == null){
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.container, AlunosFragment.novaInstancia(),"AlunosFragment").commit();
        }else{
            return;
        }
    }

    @Override public void NavigateTo_AddFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.container, AddAlunoFragment.novaInstancia(), "AddAlunoFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override public void NavigateTo_UpdateFragment(Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        UpdateAlunoFragment fragment = UpdateAlunoFragment.novaInstancia();
        fragment.setArguments(args);
        fm.beginTransaction()
                .replace(R.id.container, fragment, "AddAlunoFragment")
                .addToBackStack(null)
                .commit();
    }
}
