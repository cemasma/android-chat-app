# android-chat-app

## Gerekenler
1. Node.js
2. Android Studio

## Çalıştırmak için

1. Projeyi Android Studio'da açın.
2. Socket Server'ı çalıştırmak için **chat-app-server** dizininde **node index** komutu ile index.js script'ini çalıştırın.
3. Bu aşamada Socket Server çalıştırıldığı makinenin IP adresini alacaktır. Bunun için makinenin IP adresini öğrenip **res/values/strings.xml** kısmından hostURL string değişkeninin değerini serverın çalıştırıldığı ip adresi ile değiştirin.
4. Ardından projeyi aynı ağa bağlı olan herhangi bir android cihazda, emulatorde çalıştırın.
