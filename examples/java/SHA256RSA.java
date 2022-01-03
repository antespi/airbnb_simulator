import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
 
public class SHA256RSA {
     
    public static void main(String[] args) throws Exception {
        String input = "sample input";
         
        String strPk = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN8z1xpq8EgCZtd88KDeqxM1ydwBIcG9Jujym1bZvsEMWdncu9x9H/j+tMPFwjcnRR5ZTcVIHqmFGE4w273Jz+vQspY53CgtHBRWy+iFMdL9w7LH3Q7urEIBLqChXZE1ckI2xvbH3z9FFbYYNc1J1gJlabqQpGk1kqAEL1PIM7jXAgMBAAECgYEAle9E98IOVtEGNIsBOY9abDUdvGQmQPbHRIR0RQtQUG3zjZ3xtFN5PtIaq8gUhugiThi/XV7s+gMmKKA5fGws9oK0fBWmt22WX6M2F8IbXX6tWQj3xZDqgnB0B3NgkHmUmtVmW3NSyEJRov7YP8HkgwLFgZFN0fJe0VBDWq1g6wECQQDyXcY8ZIVlfpKnCyn6pbbep0LL2sHs9f51r031A37viIcZRviwjY4wUaTmLvv9fbhJklnn2csZQc1i12QBawPHAkEA68IYXOkFs/M2dFA90aiJMwFRYk1MuTu138EObobNIbzEUIaMA5rY03y4kmW6BpYh91HuyPynga2zsyIwV5SCcQJBAJwHcMqKcDJx9NmjX0gkjQD0LxVr5LK3fzSmQgq9UGfGviqwvQN0bhh/RyiFEOcVm6GJnWWcmZrN+Ppr7c7X5RsCQCCvci0/aTj/mScbENcGZK8bmarlSKPNsXNqEpQkQEBTKWx4muTNDQ6VFgnF7LqJCgB97XsT/C0wZd+J9+XMgyECQChQiAVqHlVhYYVe85lHaV6N/AIEwD0vGYitlKvo2uSN5eDNKRRBDG13/x7i9L6CS/5ZD2mN4yL1CdF6L0bDBoA=";
         
        String base64Signature = signSHA256RSA(input,strPk);
        System.out.println("Signature="+base64Signature);
    }
 
    private static String signSHA256RSA(String input, String strPk) throws Exception {
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
}
