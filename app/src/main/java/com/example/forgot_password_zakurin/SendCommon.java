package com.example.forgot_password_zakurin;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SendCommon extends AsyncTask<Void, Void, Void> {
    // Переменная содержащая адрес куда будет отправлятся запрос, Code - содержит ответ полученный от сервера
    public String Url = "http://192.168.0.109:5000/api/CommonController/Send", Code;
    public EditText TbEmail; // Текстовое поле с логином пользователя
    CallbackResponse CallbackResponse, CallbackError; // интерфейсы для того чтобы вернуть полученный ответ или ошибку
    // Конструктор
    public SendCommon(EditText tbEmail, CallbackResponse callbackResponse, CallbackResponse callbackError) {
        this.TbEmail = tbEmail; // Запоминаем поле
        this.CallbackResponse = callbackResponse; // Запоминаем куда возвращать ответ
        this.CallbackError = callbackError; // Запоминаем куда возвращать ошибку
    }
    // Выполнение запроса в потоке
    @Override
    protected Void doInBackground(Void... Voids) {
        try {
            Document Response = Jsoup.connect(Url + "?Email=" + TbEmail.getText()) // Выполняем запрос
                    .ignoreContentType(true) // Игнорируем тип контента
                    .get(); // выполняем GET запрос
            Code = Response.text(); // Если запрос выполнился, запоминаем код
        } catch (IOException e) {
            Log.e("Errors", e.getMessage()); // если запрос не выполнился, выводим ошибку
        }

        return null;
    }
    // По окончанию выполнения
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(Code == null) CallbackError.returner("Error"); // Если код не получен, возвращаем ошибку
        else CallbackResponse.returner(Code); // Если код получен, сообщаем ответ что код получен
    }
}
