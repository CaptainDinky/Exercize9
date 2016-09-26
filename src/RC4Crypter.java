import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jitse on 21-Sep-16.
 */
public class RC4Crypter {
    byte[] key;
    int keyLength;
    int[] cipher = new int[256];
    public RC4Crypter(String key, InputStream in) throws IOException {
        this.key = key.getBytes();
        this.keyLength = key.length();
        if(keyLength < 5 || keyLength >  32) {
            System.err.println("Invalid key");
        } else{
            initArray();
            scheduleKey();
            try {
                //pseudoRandomGeneration(in);

                testCrypter("í(\u0017Ö7M\u009A\u008Eq/\u007Fà\u009Eú\u0011Õ\u0012T~yÊ\u0012\u008A2ÕZq0 \u009FÒ#¸ÁáM§z,AF\u0006ü}¯\f(\u0083¥ÅÊÖ\u001BÈÌåír\u0018 \u001D6<\u001Bò]\u009As3]\u000FÐ·ãm¸z´1eäï \f0\u0094;gî\u0080áW\u009B?#<R,¸F\u0014ÒØïF");
               // testCrypter("This is an example to test encryption this might get a wrong result but it might not we will have to see.");
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        }

    }

    private void pseudoRandomGeneration(InputStream in) throws IOException {
        int nextInt;
        int n = 0;
        int o = 0;
        int i = 0;
        int p;
        String cryptedText = "";
        while (in.available() > 0) {
            nextInt = in.read();
            //System.out.println(nextInt +" " + (char)nextInt);
            n = (n+1)%256;
            o = (o+ cipher[n])%256;
            cipher = swap(cipher, n, o);
            System.out.println((cipher[n] + cipher[o]));
            p = cipher[(cipher[n] + cipher[o]) % 256];
            int temp = (nextInt^p);
            cryptedText += (char)temp;
            //cryptedText += ":";
            // += temp;
            //cryptedText += " ";
            //System.out.println(temp + ", " + (nextInt ^ p));
            i++;

        }
        System.out.println("i:" +i);

        System.out.println(cryptedText);
    }

    private void testCrypter(String text) throws IOException {
        int nextInt;
        int n = 0;
        int o = 0;
        int i = 0;
        int p;
        String cryptedText = "";
        for(i = 0; i< text.length(); i++) {
            //nextInt = in.read();
            //System.out.println(nextInt +" " + (char)nextInt);
            n = (n + 1) % 256;
            o = (o + cipher[i]) % 256;
            cipher = swap(cipher, n, o);
            System.out.println((cipher[i] + cipher[o]));
            p = cipher[(cipher[i] + cipher[o]) % 256];
            int temp = ((int)text.charAt(i) ^ p);
            cryptedText += (char) temp;
            //cryptedText += ":";
            // += temp;
            //cryptedText += " ";
            //System.out.println(temp + ", " + (nextInt ^ p));
        }
        System.out.println(cryptedText);
    }

    private void scheduleKey() {
        int n = 0;
        for(int i =0; i<256; i++) {
            n = ((n + cipher[i] +(int)key[i%keyLength])%256);
            cipher = swap(cipher, n, i);
        }
    }

    public int[] swap(int[] array, int a, int b){
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
        return array;
    }

    private void initArray() {
        for(int i = 0; i<256; i++){
            cipher[i] = i;
        }
    }

    public static void main(String args[]) {
        try {
            File file = new File("input.txt");
            System.setIn(new FileInputStream(file));
            new RC4Crypter("pwd12", System.in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
