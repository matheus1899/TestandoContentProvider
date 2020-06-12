package com.tenorinho.testandocontentprovider.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class AlunosHelperDB extends SQLiteOpenHelper {
    private static final String BANCO = "alunos.db";
    private static final int VERSAO_DB = 1;

    public static final String AUTHORITY = "com.tenorinho.testandocontentprovider.providers";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String TABELA = "alunos";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_SOBRENOME = "sobrenome";
    public static final String COLUNA_TURMA = "turma";

    public AlunosHelperDB(Context ctx){
        super(ctx, BANCO, null, VERSAO_DB);
    }

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABELA).build();
    @Override public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE "+TABELA+" ("+
                COLUNA_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUNA_NOME+" TEXT NOT NULL, "+
                COLUNA_SOBRENOME+" TEXT NOT NULL, "+
                COLUNA_TURMA+" TEXT NOT NULL)";
        db.execSQL(sql);
    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
