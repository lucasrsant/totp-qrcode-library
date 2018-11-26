package br.edu.fei.auth.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayOutputStream;

public class QRCodeGenerator {
    public static byte[] generate(String content) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, 400, 400);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "png", outputStream);

            return outputStream.toByteArray();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return new byte[]{ };
    }
}
