import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const publicKey = `
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcCMq56AI4zdkfXMmL71FJY1rP
+0JYOzoJc8QyMcT0biimOMeHsz6uPXAT4pInJ4cQdTl3d9voaMLrKhtNb6l2ZdqW
0Owow9SRLtuG9OT392y01szN2GyHJENDTOa7+0l9t5yUxiLpNYGWX/uI67NVxZ1F
Co7/oXnhOX++PdbUnQIDAQAB
`

const privateKey = `
MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJwIyrnoAjjN2R9c
yYvvUUljWs/7Qlg7OglzxDIxxPRuKKY4x4ezPq49cBPikicnhxB1OXd32+howusq
G01vqXZl2pbQ7CjD1JEu24b05Pf3bLTWzM3YbIckQ0NM5rv7SX23nJTGIuk1gZZf
+4jrs1XFnUUKjv+heeE5f7491tSdAgMBAAECgYBQMSyPky/hJdpbBgMHij2KrCd2
ELouQnI3fWnuKioBPcRieXgCxBqIzkaV0bIvsV73FGgugEljDsRuvDs/9w1uYL40
v0oaDhh24PxfXNCMoXONW+lpDa3ZwL9xn2r+/vDH+zn28Mh1b2465oyHG5UpCFJT
f8XoHkKdqYLA0Ubi+QJBAMuAiP0WoeEiOIiQ7c/TPHyjqipUULyQKiB19NbTj41K
kZgKSKt9Qe009gpMrCZSNlkDemot7atL6iPlvr2yhXcCQQDESXF1aguuER36V+lh
wJf4WBt5kmYpiOoHq0yN91ZepPx6wz5D98xIUSs8SVpMzZPC6rkVclL5nAF7HFQz
RcuLAkBwtFcqFAbDcPhuahXLK+or/ViY6OluBUnPgISBFdpDHjFUSx3EHDMO9G3v
dldBQfbnhY2ekKE4ZWrfRrZEyUIxAkEAsfN/eBovAp0kF20R+XxYil6ecgYmtqsY
uOE041QEKoPbHhTZ41bJDhsAXoSF6DFML58LMwVVclRcB1e0glNZZwJAWZDEvDEC
rptcSMw2egDVR/rUC2uDoUPN0GC2dWqlB2ZdMP+WYRbJzF8+qBCwPOT5MSVujE44
bxHNPmLCAuEAcA==
`

// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对数据进行加密
}

// 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey) // 设置私钥
  return encryptor.decrypt(txt) // 对数据进行解密
}

