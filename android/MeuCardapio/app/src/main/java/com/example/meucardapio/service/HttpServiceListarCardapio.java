package com.example.meucardapio.service;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.RetrofitClient;
import com.example.meucardapio.model.AddMesa;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpServiceListarCardapio extends AsyncTask<Void, Void, Cardapio> {


    private static final String TAG = "MyActivity";
    static String retorno_api;
    private List<Cardapio> listaCardapio = new List<Cardapio>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Cardapio> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(Cardapio cardapio) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends Cardapio> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends Cardapio> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public Cardapio get(int index) {
            return null;
        }

        @Override
        public Cardapio set(int index, Cardapio element) {
            return null;
        }

        @Override
        public void add(int index, Cardapio element) {

        }

        @Override
        public Cardapio remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<Cardapio> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Cardapio> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<Cardapio> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    Gson gson = new Gson();
    JsonObject jsonConversao ;
    CodeStatus codeStatus;
    private String json;


    @Override
    protected Cardapio doInBackground(Void... voids) {

        Call<List<Cardapio>> call = RetrofitClient.getInstance().getApi().getCardapio();
        call.enqueue(new Callback<List<Cardapio>>() {
            @Override
            public void onResponse(Call<List<Cardapio>> call, Response<List<Cardapio>> response) {

                Log.i(TAG, getClasseName() + "Sucesso CONVERSÃO JSON body --------------->" + response.getClass().getSimpleName());
                listaCardapio = (List<Cardapio>) response.body();
                Log.i(TAG, getClasseName() + "Sucesso CONVERSÃO JSON body --------------->" + listaCardapio.toString());
            }

            @Override
            public void onFailure(Call<List<Cardapio>> call, Throwable t) {

            }
        });

        return (Cardapio) listaCardapio;
    }

    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
