package com.example.meucardapio.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UsuarioLogado implements Parcelable {

    public String nomeUsuarioLogado;
    public int mesaUsuarioLogado;
    public int idUsuarioLogado;

    public int getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    public void setIdUsuarioLogado(int idUsuarioLogado) {
        this.idUsuarioLogado = idUsuarioLogado;
    }

    public UsuarioLogado(String nomeUsuarioLogado, int mesaUsuarioLogado, int idUsuarioLogado){
        this.nomeUsuarioLogado=nomeUsuarioLogado;
        this.mesaUsuarioLogado=mesaUsuarioLogado;
        this.idUsuarioLogado= idUsuarioLogado;

    }

    protected UsuarioLogado(Parcel in) {
        nomeUsuarioLogado = in.readString();
        mesaUsuarioLogado = in.readInt();
        idUsuarioLogado= in.readInt();
    }

    public static final Creator<UsuarioLogado> CREATOR = new Creator<UsuarioLogado>() {
        @Override
        public UsuarioLogado createFromParcel(Parcel in) {
            return new UsuarioLogado(in);
        }

        @Override
        public UsuarioLogado[] newArray(int size) {
            return new UsuarioLogado[size];
        }
    };

    public String getNomeUsuarioLogado() {
        return nomeUsuarioLogado;
    }

    public void setNomeUsuarioLogado(String nomeUsuarioLogado) {
        this.nomeUsuarioLogado = nomeUsuarioLogado;
    }

    public int getMesaUsuarioLogado() {
        return mesaUsuarioLogado;
    }

    public void setMesaUsuarioLogado(int mesaUsuarioLogado) {
        this.mesaUsuarioLogado = mesaUsuarioLogado;
    }

    @Override
    public String toString() {
        return "UsuarioLogado{" +
                "nomeUsuarioLogado='" + nomeUsuarioLogado + '\'' +
                ", mesaUsuarioLogado='" + mesaUsuarioLogado + '\'' +
                ", idUsuarioLogado=" + idUsuarioLogado +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomeUsuarioLogado);
        dest.writeInt(mesaUsuarioLogado);
        dest.writeInt(idUsuarioLogado);
    }
}
