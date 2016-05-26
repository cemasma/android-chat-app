package com.cemas.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //connect butonuna basıldığında çalışır
    //rederChatActivity methoduna girilen kullanıcı adını gönderir ve çalıştırır
    public void connectButtonAction(View view) {
        if(getUsername() != null) {
            renderChatActivity(getUsername());
        }
    }

    //girilen kullanıcı adını da intent'e ekleyerek ChatActivity Activity'sini çalıştırır
    //Böylece chat ekranına geçilir
    private void renderChatActivity(String username) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    //girilen kullanıcı adını getirir
    private String getUsername() {
        return ((EditText) findViewById(R.id.usernameEditText)).getText().toString();
    }
}
