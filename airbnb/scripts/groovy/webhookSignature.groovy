import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

def signSHA256RSA(input, strPk) 
{
    String realPK = strPk.replaceAll("-----END PRIVATE KEY-----", "")
                         .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                         .replaceAll("\n", "");

    byte[] b1 = Base64.getDecoder().decode(realPK);
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
    KeyFactory kf = KeyFactory.getInstance("RSA");

    Signature privateSignature = Signature.getInstance("SHA256withRSA");
    privateSignature.initSign(kf.generatePrivate(spec));
    privateSignature.update(input.getBytes("UTF-8"));
    byte[] s = privateSignature.sign();
    return Base64.getEncoder().encodeToString(s);
}

def webhookSignature(host, url, method, date, content_type, body, pk_file)
{
  File filePk = new File(_context.getValue(pk_file));
  String strPk = filePk.text;

  String data = _context.getValue(host) + "|" +
                _context.getValue(url) + "|" +
                _context.getValue(method) + "|" +
                _context.getValue(date) + "|" +
                _context.getValue(content_type) + "|" +
                _context.getValue(body);

  return 'Signature keyId="booking_web_hooks",' +
         'algorithm="rsa-sha256",'+
         'headers="host url method date content-type body",' +
         'delimiter="|",' +
         'signature=' + signSHA256RSA(data, strPk);
}
