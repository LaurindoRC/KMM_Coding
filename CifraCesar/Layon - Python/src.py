#!/usr/bin/env python
# coding=utf-8
'''
How to use: python caesar.py <input> [<shift>]

<input>
	/foo/bar/file - Text file to be ciphered/deciphered
<shift>
	0..n - Integer value for the key. If present, the <input> will be ciphered. If not, it will be dechipered.
'''
import sys
import string

def caesar(text, shift):
    alphabet = string.digits + string.letters + string.punctuation
    shifted = alphabet[shift:] + alphabet[:shift]
    table = string.maketrans(alphabet, shifted)
    return text.translate(table)

def save(data):
    data = data.encode('utf-8')
    file('c4es4r.txt', 'w').write(data)

def main(textfile, shift):
    with open(textfile, 'r') as tempfile:
        data = tempfile.read()
    result = caesar(data, shift)
    save(result)

if __name__ == '__main__':
	if len(sys.argv) < 2:
		print __doc__
		print '\nNothing was done.'
	else:
		if len(sys.argv) == 2:
			s = 0
		else:
			s = int(sys.argv[2])
		main(sys.argv[1], s)
		print 'Complete.\nThe result is stored in the current directory as \"c4es4r.txt\" (UTF-8).'