var input = document.getElementById('input');
var output = document.getElementById('output');
var posicoes = document.getElementById('posicoes');
var criptografa = document.getElementById('criptografa');
var descriptografa = document.getElementById('descriptografa');
var asciiValid = [[65, 90], [97, 122]];
var cifra = parseInt(posicoes.value);

posicoes.onkeyup = function(ev) {
    cifra = parseInt(posicoes.value);
    output.value = encryptText(input.value);
}

posicoes.onmouseup = function(ev) {
    cifra = parseInt(posicoes.value);
    output.value = encryptText(input.value);
}

criptografa.onchange = function(ev) {
    cifra = cifra * (-1);
    output.value = encryptText(input.value);
}

descriptografa.onchange = function(ev) {
    cifra = cifra * (-1);
    output.value = encryptText(input.value);
}

var timeout;
input.onkeyup = function(ev) {
    clearTimeout(timeout);
    timeout = setTimeout(function() {
        output.value = encryptText(input.value);
    }, 300);
}

function validCharacter(keyCode, key) {
    return (keyCode >= 32 && keyCode <= 126) && (key.length == 1);
}

function validLetter(keyCode, key) {
    return (keyCode >= asciiValid[0][0] && keyCode <= asciiValid[0][1]) || (keyCode >= asciiValid[1][0] && keyCode <= asciiValid[1][1]);
}

function isLetterUpper(keyCode, key) {
    return (keyCode >= asciiValid[0][0] && keyCode <= asciiValid[0][1]);
}

function isLetterLower(keyCode, key) {
    return (keyCode >= asciiValid[1][0] && keyCode <= asciiValid[1][1]);
}

function processChar(ascii, key) {
    if(validCharacter(ascii, key)) {
        if (validLetter(ascii, key)) {
            return encryptChar(ascii, key);
        }
        return key;
    }
    if (ascii === 10 || ascii === 13) {
        return '\n';
    }
    if (ascii === 32) {
        return ' ';
    }
    return '';
}

function encryptChar(ascii, key) {

    if (isLetterUpper(ascii, key)) {
        ascii += cifra;
        if (ascii > asciiValid[0][1]) {
            ascii = ascii - asciiValid[0][1] + asciiValid[0][0] - 1;
        }
        if (ascii < asciiValid[0][0]) {
            ascii = ascii - asciiValid[0][0] + asciiValid[0][1] + 1;
        }
    } else if (isLetterLower(ascii, key)) {
        ascii += cifra;
        if (ascii > asciiValid[1][1]) {
            ascii = ascii - asciiValid[1][1] + asciiValid[1][0] - 1;
        }
        if (ascii < asciiValid[1][0]) {
            ascii = ascii - asciiValid[1][0] + asciiValid[1][1] + 1;
        }
    }

    if (key === key.toUpperCase()) {
        return String.fromCharCode(ascii).toUpperCase();
    }

    return String.fromCharCode(ascii).toLowerCase();
}

function encryptText(str) {
    var strEncrypt = '';
    for (var i = 0; i < str.length; i++) {
        strEncrypt += processChar(str.charCodeAt(i), str.charAt(i));
    }
    return strEncrypt;
}
