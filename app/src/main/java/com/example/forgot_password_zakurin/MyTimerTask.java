package com.example.forgot_password_zakurin;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    public int Time = 30; // Время таймера
    public Activity Activity; // Ссылка на активность
    public TextView tvText, tvSendMail; // Текстовое поля таймера, и текстовое поле кнопки повторно отправить код
    // Конструктор
    public MyTimerTask(Activity activity, TextView tvText, TextView tvSendMail) {
        this.Activity = activity; // Запоминаем активность
        this.tvText = tvText; // Запоминаем ссылку на текст с отсчётом
        this.tvSendMail = tvSendMail; // Запоминаем ссылку на кнопку "Отправить"
    }
    // Выполнение таймера
    @Override
    public void run() {
        Time--; // уменьшаем время
        if(Time == 0) this.cancel(); // если время вышло, завершаем выполнение таймера
        // Выполнение в потоке UI
        Activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String Second = Time > 10 ? String.valueOf(Time) : "0" + String.valueOf(Time); // добавляем к секундам 0
                tvText.setText("00:" + Second); // отображаем секунды

                if(Time == 0) { // если время вышло
                    tvText.setVisibility(View.GONE); // скрываем таймер
                    tvSendMail.setVisibility(View.VISIBLE); // отображаем кнопку
                }
            }
        });
    }
}
