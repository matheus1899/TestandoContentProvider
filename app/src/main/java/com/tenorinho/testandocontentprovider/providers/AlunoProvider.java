package com.tenorinho.testandocontentprovider.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;
import com.tenorinho.testandocontentprovider.data.AlunosHelperDB;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.AUTHORITY;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.COLUNA_ID;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.CONTENT_URI;
import static com.tenorinho.testandocontentprovider.data.AlunosHelperDB.TABELA;

public class AlunoProvider extends ContentProvider {

    private AlunosHelperDB helper;
    private UriMatcher uriMatcher = getUriMatcher();
    private static final int TODOS_ALUNOS = 1;
    private static final int ALUNO_ESPECIFICO = 2;

    public AlunoProvider(){}
    private UriMatcher getUriMatcher(){
        UriMatcher uri = new UriMatcher(UriMatcher.NO_MATCH);
        uri.addURI(AUTHORITY, TABELA, TODOS_ALUNOS);
        uri.addURI(AUTHORITY, TABELA+"/#", ALUNO_ESPECIFICO);
        return uri;
    }
    @Override public boolean onCreate() {
        helper = new AlunosHelperDB(getContext());
        return true;
    }
    @Override public String getType(Uri uri){ return null;}
    //
    @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        int res;
        switch (match){
            case ALUNO_ESPECIFICO:
                res = helper.getReadableDatabase().delete(TABELA, COLUNA_ID +" = ?", new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida: "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        Log.i("TAG", "delete: "+res);
        return res;
    }
    @Override public Uri insert(Uri uri, ContentValues values) {
        int match = uriMatcher.match(uri);
        Uri res;
        switch (match){
            case TODOS_ALUNOS:
                long id = helper.getReadableDatabase().insert(TABELA, null, values);
                if(id > -1){
                    res = ContentUris.withAppendedId(CONTENT_URI, id);
                }else{
                    throw new SQLiteException("Erro ao inserir: "+uri);
                }
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida: "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        Log.i("TAG", "insert: "+res);
        return res;
    }
    @Override public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = uriMatcher.match(uri);
        Cursor res;
        switch (match){
            case TODOS_ALUNOS:
                res = helper.getReadableDatabase().query(
                        TABELA,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case ALUNO_ESPECIFICO:
                res = helper.getReadableDatabase().query(
                        TABELA,
                        projection,
                        AlunosHelperDB.COLUNA_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida: "+ uri);
        }
        res.setNotificationUri(getContext().getContentResolver(), CONTENT_URI);
        return res;
    }
    @Override public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        int linhas;
        switch (match){
            case ALUNO_ESPECIFICO:
                linhas = helper.getWritableDatabase().update(
                        TABELA,
                        values,
                        COLUNA_ID+" = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida: "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return linhas;
    }
}
