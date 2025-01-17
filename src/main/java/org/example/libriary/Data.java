package org.example.libriary;

import javax.swing.*;

public class Data {
    public boolean checkBook(String book) {
        return true;
    }
    public String getBook(String book) {
        return null;
    }
}



class FIO {
    public static boolean check(String fio) {
        if ( fio != null && !fio.isBlank() ) {
            String[] fioArr = fio.trim().split("\\s+");
            if (fioArr.length == 3) {
                for (String word : fioArr) {
                    if (!word.matches("^[а-яА-ЯёЁ\\.]+$") ) {
                        return false;
                    }
                }
                System.out.println("True");
                return true;
            }
        }
        System.out.println("False");
        return false;
    }
    public static String get(String fio) {
        String[] fioArr = fio.trim().split("\\s+");
        return fioArr[0].substring(0, 1).toUpperCase() + fioArr[0].substring(1).toLowerCase()
        + fioArr[1].substring(0, 1).toUpperCase() + fioArr[1].substring(1).toLowerCase()
        + fioArr[2].substring(0, 1).toUpperCase() + fioArr[2].substring(1).toLowerCase();
    }
}
class Grade {
    public static boolean check(String number) {
        if (number == null || number.length() != 6) {
            return false;
        }
        return number.matches("\\d{6}");
    }
    public static String get(String number) {
        return number;
    }
}
class BookID {
    public static boolean check(String id) {
        if (id == null || id.length() != 14) {
            return false;
        }
        return id.matches("\\d{14}");
    }
    public static String get(String id) {
        return id;
    }
}
class Shelf {
    public static boolean check(String number) {
        if (number == null || number.length() != 4) {
            return false;
        }
        return number.matches("^[а-яА-ЯёЁ]\\d{3}$");
    }
    public static String get(String number) {
        return number.toUpperCase();
    }

}

class Size {
    public static boolean check(String size) {
        return size != null && size.matches("^[1-9][0-9]*$");
    }

    public static String get(String size) {
            return size;
    }
}

class Genre {
    public static boolean check(String genre) {
        return genre != null && !genre.isBlank() && genre.matches("^[а-яА-ЯёЁ]+$");
    }
    public static String get(String genre) {
        return genre.toLowerCase();
    }
}
class Name {
    public static boolean check(String name) {
        return name != null && !name.isBlank() && name.matches("^[а-яА-ЯёЁ0-9.,_ -]+$");
    }
    public static String get(String name) {
        return name;
    }
}
