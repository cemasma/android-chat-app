package com.cemas.chatapp;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.cemas.chatapp.adapter.CustomAdapter;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class ChatActivity extends AppCompatActivity {

    private Socket socketClient;
    private String username;

    private ListView listViewChat;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //chatscreende mesajları alt alta listelemek ve görünümünü özelleştirmek için bir listview ve onun için
        //bir de custom adapter kullanıyoruz ikisini de tanımlayıp adapter'i listview'a atıyoruz.
        listViewChat = (ListView) findViewById(R.id.listViewChat);
        customAdapter = new CustomAdapter(getApplicationContext(), R.layout.listview_item);
        listViewChat.setAdapter(customAdapter);

        //initSocketClient ile bağlanmak istediğimiz chat server'a bağlanıyoruz
        initSocketClient(getResources().getString(R.string.hostUrl));
        this.username = getIntent().getStringExtra("username");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    //burada client olarak nereye bağlanacağız ne yapacağız onu ayarlıyoruz genel olarak
    //chat server'ımızın iletişim kanalını (socket) hostUrl'i tanımlıyoruz
    //ve onNewMessage listener'ını chat server'a gelen mesajları dinlemesi için atıyoruz
    //ardından chat server'ımıza bağlanıyoruz
    private void initSocketClient(String hostUrl) {
        try {
            socketClient = IO.socket(hostUrl);
            socketClient.on("chat message", onNewMessage);
            socketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //initSocketClient'ta atamasını yaptığımız onNewMessage listener'ı chat server'a giden mesajları dinler
    //eğer bir mesaj gönderildiyse harekete geçer mesajı alır ve addMessage methoduna paslar
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String message = args[0].toString();
                    addMessage(message);
                }
            });
        }
    };

    //gönderme butonumuzun action methodu
    //yazdığımız mesajı chatserver'a gönderir
    public void sendMessageButtonAction(View view) {
        socketClient.emit("chat message", getMessagePrefix() + getWritedMessage());
        clearWritedMessage();
    }

    //mesajı listview'a adapter aracılığıyla ekler
    private void addMessage(String message) {
        customAdapter.add(message);
        customAdapter.notifyDataSetChanged();
    }

    //göndermek istediğimiz mesajı messageEditText'ten alır ve return eder
    private String getWritedMessage() {
        return ((EditText) findViewById(R.id.messageEditText)).getText().toString();
    }

    private void clearWritedMessage() {
        if(((EditText) findViewById(R.id.messageEditText)) != null) {
            ((EditText) findViewById(R.id.messageEditText)).setText("");
        }
    }

    //mesajların önünde olmasını istediğimiz text'i burada bir kalıp olarak ayarlıyoruz
    //örneği ben kullanıcı adlarının görünmesini istediğim için bu şekilde ayarladım
    private String getMessagePrefix() {
        return username + ": ";
    }

    //uygulama kapatıldığında chat serverdan çıkıyor ve socketi kapatıyoruz
    @Override
    protected void onDestroy() {
        super.onDestroy();

        socketClient.disconnect();
        socketClient.off("chat message", onNewMessage);
    }
}
