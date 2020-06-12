package com.tenorinho.testandocontentprovider.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Aluno implements Parcelable {
    private int ID;
    private String Nome;
    private String Sobrenome;
    private String Turma;

    public Aluno(){
        setID(-1);
        setNome("");
        setSobrenome("");
        setTurma("");
    }
    public Aluno(int ID, String Nome,String Sobrenome, String Turma){
       setID(ID);
       setNome(Nome);
       setSobrenome(Sobrenome);
       setTurma(Turma);
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public void setNome(String Nome){
        if(Nome ==null){
            this.Nome = "";
        }else{
            this.Nome = Nome;
        }
    }
    public void setSobrenome(String Sobrenome){
        if(Sobrenome ==null){
            this.Sobrenome = "";
        }else{
            this.Sobrenome = Sobrenome;
        }
    }
    public void setTurma(String Turma){
        if(Turma ==null){
            this.Turma = "";
        }else{
            this.Turma = Turma;
        }
    }

    public int getID(){
        return this.ID;
    }

    public String getNome() {
        return this.Nome;
    }

    public String getSobrenome(){return this.Sobrenome; }
    public String getTurma(){return this.Turma; }


    @Override public int describeContents() {
        return 0;
    }
    @Override public void writeToParcel(Parcel dest, int flags) {
        /*dest.writeInt(getID());
        dest.writeString(getNome());
        dest.writeString(getSobrenome());
        dest.writeString(getTurma());*/
        dest.writeInt(ID);
        dest.writeString(Nome);
        dest.writeString(Sobrenome);
        dest.writeString(Turma);
    }
    public static final Parcelable.Creator<Aluno> CREATOR = new Parcelable.Creator<Aluno>() {
        public Aluno createFromParcel(Parcel in) {
            return new Aluno(in);
        }

        public Aluno[] newArray(int size) {
            return new Aluno[size];
        }
    };
    private Aluno(Parcel in){
        /*setID(in.readInt());
        setNome(in.readString());
        setSobrenome(in.readString());
        setTurma(in.readString());*/
        ID = in.readInt();
        Nome = in.readString();
        Sobrenome = in.readString();
        Turma = in.readString();
    }
}
