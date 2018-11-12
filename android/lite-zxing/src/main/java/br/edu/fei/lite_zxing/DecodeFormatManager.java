/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.edu.fei.lite_zxing;

import android.content.Intent;

import com.google.zxing.BarcodeFormat;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final class DecodeFormatManager {

    private static final Map<String, Set<BarcodeFormat>> FORMATS_FOR_MODE;

    static {
        FORMATS_FOR_MODE = new HashMap<>();
    }

    private DecodeFormatManager() {
    }

    static Set<BarcodeFormat> parseDecodeFormats(Intent intent) {
        Iterable<String> scanFormats = Arrays.asList("QR_CODE");
        return parseDecodeFormats(scanFormats, intent.getStringExtra(Intents.Scan.MODE));
    }

    private static Set<BarcodeFormat> parseDecodeFormats(Iterable<String> scanFormats, String decodeMode) {
        if (scanFormats != null) {
            Set<BarcodeFormat> formats = EnumSet.noneOf(BarcodeFormat.class);
            try {
                for (String format : scanFormats) {
                    formats.add(BarcodeFormat.valueOf(format));
                }
                return formats;
            } catch (IllegalArgumentException iae) {
                // ignore it then
            }
        }
        if (decodeMode != null) {
            return FORMATS_FOR_MODE.get(decodeMode);
        }
        return null;
    }

}
