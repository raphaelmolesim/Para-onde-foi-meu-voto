package voto;

public class Chars {

  public static String limpa(final String entrada) {

    if (entrada == null) {
      return null;
    }

    StringBuffer result = new StringBuffer();

    for (char c : entrada.toCharArray()) {
      result.append(charLimpo(c));
    }

    return result.toString();
  }

  private static String charLimpo(final char c) {
    switch (c) {
    case ' ':
      return "";
    case 'à':
      return "a";
    case 'ã':
      return "a";
    case 'á':
      return "a";
    case 'â':
      return "a";
    case 'å':
      return "a";
    case 'ä':
      return "a";
    case 'ª':
      return "a";
    case 'À':
      return "A";
    case 'Ã':
      return "A";
    case 'Á':
      return "A";
    case 'Â':
      return "A";
    case 'Å':
      return "A";
    case 'Ä':
      return "A";
    case 'è':
      return "e";
    case 'é':
      return "e";
    case 'ê':
      return "e";
    case 'ë':
      return "e";
    case 'È':
      return "E";
    case 'É':
      return "E";
    case 'Ê':
      return "E";
    case 'Ë':
      return "E";
    case 'í':
      return "i";
    case 'ì':
      return "i";
    case 'î':
      return "i";
    case 'ï':
      return "i";
    case 'Ì':
      return "I";
    case 'Í':
      return "I";
    case 'Î':
      return "I";
    case 'Ï':
      return "I";
    case 'ò':
      return "o";
    case 'õ':
      return "o";
    case 'ó':
      return "o";
    case 'ô':
      return "o";
    case 'ø':
      return "o";
    case 'ö':
      return "o";
    case 'º':
      return "o";
    case 'Ò':
      return "O";
    case 'Õ':
      return "O";
    case 'Ó':
      return "O";
    case 'Ô':
      return "O";
    case 'Ø':
      return "O";
    case 'Ö':
      return "O";
    case 'ù':
      return "u";
    case 'ú':
      return "u";
    case 'û':
      return "u";
    case 'ü':
      return "u";
    case 'µ':
      return "u";
    case 'Ù':
      return "U";
    case 'Ú':
      return "U";
    case 'Û':
      return "U";
    case 'Ü':
      return "U";
    case 'æ':
      return "ae";
    case 'Æ':
      return "Ae";
    case 'ß':
      return "ss";
    case 'ç':
      return "c";
    case '¢':
      return "c";
    case 'Ç':
      return "C";
    case 'Ğ':
      return "D";
    case 'ğ':
      return "d";
    case 'ñ':
      return "n";
    case 'Ñ':
      return "N";
    case 'Ş':
      return "P";
    case 'ş':
      return "p";
    case 'ÿ':
      return "y";
    case 'ı':
      return "y";
    case 'İ':
      return "Y";
    case '(':
      return "";
    case '-':
      return "";
    case ')':
      return "";
    case '¹':
      return "1";
    case '²':
      return "2";
    case '³':
      return "3";
    default:
      return "" + c;
    }
  }
}
