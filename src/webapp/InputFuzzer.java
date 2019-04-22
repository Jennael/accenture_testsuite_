package webapp;

import java.util.Random;

public class InputFuzzer {

    static String KEYBOARD = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz`~!@#$%^&*()-_=+[]{}\\|;:'\",<.>/?";
    /*
    Char - [0, 51]
    Upper - [0, 51], i%2 == 0
    Lower - [0, 51], i%2 != 0
    Symbols - [52, 83]
    */

    static int LENGTH;
    static int MODE;
    static int CAPS; //0 - both, 1 - lower, 2 - upper

    /*
    modes: {
        1: alphabets,
        2: int,
        3: alnum,
        4: symbols,
        5: alnum+symbols
        6: number
    }
    */

    static Random rand = new Random();

    public static void main(String[] args){
        System.out.println(generate(3, 5, 0));

    }

    public static String generate(int len, int mod, int caps){
        LENGTH = len;
        MODE = mod;
        CAPS = caps;

        String result = "";
        for (int i = 0; i < LENGTH; i++){

            switch (MODE){
                case 1: //alpha
                    if (CAPS == 0){
                        result += randChar(); //both
                    } else if (CAPS == 1){
                        result += randLower();//upper
                    } else if (CAPS == 2){
                        result += randUpper();//lower
                    }
                    break;
                case 2: //integers
                    result += randInt();
                    break;
                case 3: //alnum
                    result += randAlnum();
                    break;
                case 4: //symb
                    result += randSym();
                    break;
                case 5: //alphanum + symb
                    int n = rand.nextInt(94);
                    if (n<84){
                        result += randAlnumSym();
                    }else{
                        result+= randInt();
                    }
                    break;
                case 6:
                    String max = "1";
                    for (int j = 0; i<LENGTH; j++){
                        max += "0";
                    }
                    result += randNum(0, Integer.parseInt(max)-1);
                    break;
            }
        }
        return result;
    }

    /*
    * Return random character
    */
    public static String randChar(){
        int n = rand.nextInt(52);
        return String.valueOf(KEYBOARD.charAt(n));
    }

    /*
     * Return random character
     */
    public static String randUpper(){
        int n = rand.nextInt(26)*2;
        return String.valueOf(KEYBOARD.charAt(n));
    }

    /*
     * Return random character
     */
    public static String randLower(){
        int n = rand.nextInt(26)*2 + 1;
        return String.valueOf(KEYBOARD.charAt(n));
    }

    /*
     * Return random number within a bound, inclusive
     */
    public static String randNum(int min, int max){
        return String.valueOf(rand.nextInt(max - min + 1) + min);
    }

    /*
     * Return random integer between 0 to 9
     */
    public static String randInt(){
        return String.valueOf(rand.nextInt(10));
    }

    /*
     * Return random alnum
     */
    public static String randAlnum(){
        int n;
        String result = null;
        switch (CAPS){
            case 0:
                n = rand.nextInt(62);
                if (n>51){
                    result = String.valueOf(n-52);
                }else {
                    result =  randChar(); //both
                }
                break;
            case 1:
                n = rand.nextInt(36);
                if (n>25){
                    result =  String.valueOf(n-26);
                }else {
                    result =  randLower(); //lower
                }
                break;
            case 2:
                n = rand.nextInt(36);
                if (n>25){
                    result =  String.valueOf(n-26);
                }else {
                    result =  randUpper(); //upper
                }
                break;
        }
        return result;
    }

    /*
     * Return random alnum
     */
    public static String randAlnumSym(){
        int n;
        String result = null;
        switch (CAPS){
            case 0:
                n = rand.nextInt(94);
                if (n>83){
                    result = String.valueOf(n-84);
                }else {
                    result = String.valueOf(KEYBOARD.charAt(rand.nextInt(84))); //both
                }
                break;
            case 1:
                n = rand.nextInt(68);
                if (n>57){
                    result =  String.valueOf(n-58);
                }else if (n > 25){
                    result =  randSym();
                }else {
                    result = randLower();
                }
                break;
            case 2:
                n = rand.nextInt(68);
                if (n>57){
                    result =  String.valueOf(n-58);
                }else if (n > 25){
                    result =  randSym();
                }else {
                    result = randUpper();
                }
                break;
        }
        return result;
    }

    /*
     * Return random symbol
     */
    public static String randSym(){
        int n = rand.nextInt(rand.nextInt(83 - 52 + 1) + 52);
        return String.valueOf(KEYBOARD.charAt(n));
    }


}
