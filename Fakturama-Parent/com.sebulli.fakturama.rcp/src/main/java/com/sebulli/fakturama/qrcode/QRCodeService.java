package com.sebulli.fakturama.qrcode;

import java.util.Map;

import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.Invoice;

/**
 * Service class for generating various QR codes 
 *
 */
public interface QRCodeService {

    byte[] createSwissCodeQR(Invoice document);

    byte[] createGiroCode(Invoice document, Map<String, Object> params);

    byte[] createVCardQRCode(Document document);

    byte[] createEANCode(String productNumber);

}
