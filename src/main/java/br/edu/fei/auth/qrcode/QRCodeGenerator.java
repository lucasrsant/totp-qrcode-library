package br.edu.fei.auth.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.FileSystems;

public class QRCodeGenerator {
    public static void generate() {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix matrix = writer.encode("7aNC0qMgiBkUDZYexrE3Yl0gK3gwokGjVq/G+evU6/o=", BarcodeFormat.QR_CODE, 400, 400);
            MatrixToImageWriter.writeToPath(matrix, "png", FileSystems.getDefault().getPath("image.png"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
