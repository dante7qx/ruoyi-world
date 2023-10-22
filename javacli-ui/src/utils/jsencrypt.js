import forge from 'node-forge'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const pubKey = `
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx2jg/2OJ3dNpXOFvwNKB
kL3Ytm9/NhMxAEVdrt3tPulsV0TzQquIS1C10sMgDgpBHrL8921iwZ8JycM10PhH
UCuBPBitpsTjIg2sNaVGSwevzh7pDAC25QTIBzz4zM7GnoJfa9/fW1jjwocslv9W
4Slj5OCKnt3bBVwVpzhrJURNbgKM+5rYeWoVYO6VqCJmsPM2Ok1ILTyzfwNOhenb
8Xl5+JPHTHVXw5g1vr/XJY1NcYLEGuPyj3juZuPu0dVVkI3cIMSQxSlZTwxbA2Fk
h1qISqdsFsy1m+wVyHIyxsRE2uJUiSYNekM1jz/li4N/0iqAAuLWcu+S5U6JzZEc
MQIDAQAB
-----END PUBLIC KEY-----
`

const priKey = `
-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHaOD/Y4nd02lc
4W/A0oGQvdi2b382EzEARV2u3e0+6WxXRPNCq4hLULXSwyAOCkEesvz3bWLBnwnJ
wzXQ+EdQK4E8GK2mxOMiDaw1pUZLB6/OHukMALblBMgHPPjMzsaegl9r399bWOPC
hyyW/1bhKWPk4Iqe3dsFXBWnOGslRE1uAoz7mth5ahVg7pWoImaw8zY6TUgtPLN/
A06F6dvxeXn4k8dMdVfDmDW+v9cljU1xgsQa4/KPeO5m4+7R1VWQjdwgxJDFKVlP
DFsDYWSHWohKp2wWzLWb7BXIcjLGxETa4lSJJg16QzWPP+WLg3/SKoAC4tZy75Ll
TonNkRwxAgMBAAECggEBALxUyWtqzyK6MKCQKcOrDMNvCeYDl9mydm0TFk+5OgBY
5QHnFPi6mJbJO3A66WNZO/eKhHAK12KWmd/8hi8+zdthu0TO6fK/sOTQlPx1g9YJ
BHlfqCq14gaaZXCwnSqRf/TknMp9Pa5AXZuznuFdduhg9v+LMmCol9qQBcNQ/aDL
Hqt3wz4OFqY5B/N5Hp+FnWp88oDvbshNmtlnduvG499R6lxNNtmYL4XersEtUSFL
xJGatqrtZDlNwhZEH10Ca4pry2OhJkG40pxvNtySq/imvbipxverjz2D9GVz2trO
I4JcQ23/YaMN9XTISbKfWcnrY/vRCQf6Iih6H1fSiCECgYEA4249ZWJXeTj1sxz8
DFYRbkTx6ZMXdvPdfOBmzVK2RaHW0uaMHYLhUkwibE3iK+uIx3m8mQB4ZXiIIUGm
xOJT/RRgmdZFM/TqGRZHaQ/OzG3KkirakhL6ScBOzs0QAA3Ozcu4Nx3x13gTa1yz
VEiUj8uAAYs+RjauKjtQbdIkz9cCgYEA4HWJNHqV1yTcWmetgbUuAENliOGzjUvI
njA4lAvvdf3ieNI0lVgXV5C/H5Gjlx1xUx9zBIhanRwHJ5c70SgLfBm+UyKTLKA1
G6vO0yGO601EbcWjXHDeZ1IFscAW2yUNbXYzY31CG/3SHcQLmP3w55VaIeyWWUW9
FQHjm1QwkzcCgYBOuC2QFgXo84o3wOSM8I1/+WUyT7NjfO1A9/sdPm/Qkj/lJ99/
p3mOHsQbcKnEQfZJ9R0OaFdD6ABWYn7yztP7WT5GgR/a5/7PSCLXfp05rRbcmPCm
duMi++lLMzdCjYTzPnw4t6ipx+oQ0zlPBwBE9U8NOcEIVIzcGUOxf3c2aQKBgAu0
1ZURF6OC3qMwaRIsutt0qGT9MHocC7iEV08RWEnVFeyrOebYE0+T0wP4eom1FWX6
a+s+mUs8XPspngsIhaDRTuXTFc3sBRkOWtkUFPocq4b2GdEGafMFihp9JDQFuK+O
zJtRuxLup/i5csll5RyO44aFakn5oyTnVlGRkH79AoGAIjJS8i34UTfJvooakwcr
lNAzMhUn0j+cqhrkIaxV6DH2hjgxI7M5JlCgt/euAx1dhHm1munx8UTzQWFyp1nH
6MsSBoElZpojbWFLsvauGCbVufBexvep8dyTfPhZq7Tcu6lXcctZFQzZWRVa4Stv
5FBOTjwENTxC6N5SYvMAjOw=
-----END PRIVATE KEY-----
`

// 加密
export function encrypt(txt) {
  const publicKey = forge.pki.publicKeyFromPem(pubKey)
  const encodeTxt = forge.util.encodeUtf8(txt)
  const encrypted = publicKey.encrypt(encodeTxt, 'RSA-OAEP', {
    md: forge.md.sha256.create(),
    mgf: forge.mgf.mgf1.create(forge.md.sha1.create()),
  })
  return forge.util.encode64(encrypted)
}

// 解密
export function decrypt(txt) {
  const privateKey = forge.pki.privateKeyFromPem(priKey);
  const encodeTxt = forge.util.decode64(forge.util.decodeUtf8(txt));
  const decrypted = privateKey.decrypt(encodeTxt, 'RSA-OAEP', {
    md: forge.md.sha256.create(),
    mgf: forge.mgf.mgf1.create(forge.md.sha1.create()),
  })
  return decrypted
}

