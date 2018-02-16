import base64

from Crypto import Random

from Crypto.Cipher import AES

from znap.settings import key

BS = 16

def _pad(s):
    return s + (BS - len(s) % BS) * chr(BS - len(s) % BS)

def _unpad(s):
    return s[:-ord(s[len(s) - 1:])]

def encryption(message):
    message = message.encode('utf-8')
    message = _pad(message)
    obj = AES.new(key)
    ciphertext = obj.encrypt(message)
    return base64.b64encode(ciphertext)

def decryption(ciphertext):
    ciphertext = base64.b64decode(ciphertext)
    obj = AES.new(key)
    plaintext = obj.decrypt(ciphertext)
    plaintext = _unpad(plaintext)
    return plaintext


"""
def encryption2(message):
    print message
    message = _pad(message)
    print message
    iv = Random.new().read(AES.block_size)
    cipher = AES.new('This is a key123', AES.MODE_CBC, iv)
    return base64.b64encode(iv+cipher.encrypt(message))
"""
