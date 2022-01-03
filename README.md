## API simulator for Airbnb

[TODO]

## Generate a private key for webhook rsa-sha256 signature

Generate a private key

```
openssl genrsa -out key.pem 2048
```

Generate a public key

```
openssl rsa -in key.pem -pubout -outform PEM -out pub_key.pem
```

Convert the private key to PKCS#8 format

```
openssl pkcs8 -topk8 -inform PEM -in key.pem -out key_pkcs8.pem -nocrypt
```
