package com.example.monsheeppractica.mytools;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class GenerarQR {

    public Bitmap createQRcodeImage(String url, int w,int h) {


        // Juzgar la legalidad de la URL
        if (url == null || "".equals(url) || url.length() < 1) {
            return null;
        }
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // Conversión de datos de imagen, usando conversión matricial
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, w, h, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int[] pixels = new int[w * h];
        // A continuación, siga el algoritmo del código QR para generar las imágenes del código QR una por una,
        // Los dos bucles for son el resultado del escaneo horizontal de la imagen
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * w + x] = 0xff000000;
                } else {
                    pixels[y * w + x] = 0xffffffff;
                }
            }
        }
        // Genere el formato de la imagen del código QR, use ARGB_8888
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        // Mostrar en nuestro ImageView

        return bitmap;

    }
}
