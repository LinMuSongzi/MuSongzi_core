package com.musongzi.test;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class Enter {

    private static final String TAG = "Enter_now";


//    public static void jiekai(InputStream inputStream, OutputStream outputStream) throws IOException {
//        Log.i(TAG, "jiekai: 解密");
//        int read;
////        Log.i(TAG, "jiekai: do");
//        byte[] bytes = new byte[10];
//        do {
//            read = inputStream.read(bytes);
//            int re2 = read;
//            if (read == 0) {
//                continue;
//            }
//            if (read == -1) {
//                break;
//            }
//            for (int index = 0; index < 10; index++) {
//                byte bss = bytes[index];
//                if (bss == 0) {
//                    continue;
//                }
//
//                boolean flag = bss > 0;
//                int thisByte = bss;
//                //0011 0001 = 49
//                //0000 1110 = 11
//                Log.i(TAG, "jiami: thisByte  " + thisByte);
//                int low3 = thisByte & LOW_3_MARK;
//                Log.i(TAG, "jiami: low3 = " + low3);
//
//                int center3 = thisByte & LOW_3_3_MARK;
//                Log.i(TAG, "jiami: center3 = " + center3);
//
//                int top2 = re2 & ~(LOW_3_MARK | LOW_3_3_MARK);
//                Log.i(TAG, "jiami: top2  " + top2);
//
//                re2 = top2 | (low3 << 3) | (center3 >> 3);
//                Log.i(TAG, "jiami: re2 " + re2 + " , (low3 << 3) = " + (low3 << 3) + " , (center3 >> 3) = " + (center3 >> 3));
//                Log.i(TAG, "\n");
//                outputStream.write(bss);
//            }
//
//        } while (read != -1);
////        fileOutputStream.write("\n".getBytes());
//        outputStream.close();
//        inputStream.close();
//        Log.i(TAG, "jiekai: 结束");
//    }

    static final byte bThr = 3;
    //    static int _1111_1111 = ((1 << 8) - 1) << 8;
    //0000_0000 0000_0000 0000_0000 1111_1111
    static int LOW_3_MARK = ((1 << bThr) - 1);
    //0000_0000 0000_0000 1111_1111 0000_0000
    static int LOW_3_3_MARK = LOW_3_MARK << bThr;

    public static void jiami(InputStream inputStream, OutputStream outputStream) throws Exception {


        int read;
        //1000 0111;
//0000_0000 0000_0000 0000_0000 1111_1111
//        int low8 = re2 & LOW_8_MARK;
//        //0000_0000 0000_0000 1111_1111 0000_0000
//        int low16 = re2 & LOW_8_8_MARK;
//        re2 &= ~(LOW_8_MARK | LOW_8_8_MARK);
//        re2 |= (low8 << 8) | (low16 >>> 8);
        byte[] bytes = new byte[10];

        do {
            read = inputStream.read(bytes);
            int re2 = read;
            if (read == 0) {
                continue;
            }
            if (read == -1) {
                break;
            }
            for (int index = 0; index < 10; index++) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(TAG);
                byte bss = bytes[index];
                if (bss == 0) {
                    continue;
                }

//                boolean flag = bss > 0;
                System.out.println();
                stringBuffer.append("\n thisByte  " + bss);

                int thisByte = bss;//!flag ? bss * -1 : bss;
                //0000 1011
                //0000

                int low3 = thisByte & LOW_3_MARK;
                stringBuffer.append(" \n low3 = " + low3);

                int center3 = thisByte & LOW_3_3_MARK;
                stringBuffer.append(" \n center3 = " + center3);
                //0011 1110 = 8+16+32
                re2 = (3 << 6) & thisByte;
                stringBuffer.append(" \n top2  " + re2);

                re2 |= (low3 << 3) | (center3 >> 3);
//                if (!flag) {
//                    re2 *= -1;
//                }
                stringBuffer.append(" \n re2 " + re2 + " , (low3 << 3) = " + (low3 << 3) + " , (center3 >> 3) = " + (center3 >> 3));
                Log.i(TAG, stringBuffer.toString() + "\n\n");

                outputStream.write(re2);
            }


        } while (true);
//        fileOutputStream.write("\n".getBytes());
        outputStream.close();
        inputStream.close();
        //Log.i(TAG, "jiami: 结束 " + stringBuffer.toString());
    }

    static Random random = new Random();

    private static int randon() {
        return random.nextInt(16);
    }


}
