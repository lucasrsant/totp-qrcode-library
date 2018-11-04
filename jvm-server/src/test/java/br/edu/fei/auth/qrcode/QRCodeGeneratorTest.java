package br.edu.fei.auth.qrcode;

import org.junit.Test;

public class QRCodeGeneratorTest {
    @Test
    public void shouldGenerateQRCode() {
        QRCodeGenerator.generate("blah");
    }
}
