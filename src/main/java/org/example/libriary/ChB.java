package org.example.libriary;

public class ChB {
    private String studentId;
    private String studentName;
    private String bookId;
    private String bookName;
    private String takeDate;
    private String backDate;

    public ChB(String studentId, String studentName, String bookId, String bookName, String takeDate, String backDate) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.bookId = bookId;
        this.bookName = bookName;
        this.takeDate = takeDate;
        this.backDate = backDate;
    }

    // Getters и Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getTakeDate() {
        return takeDate;
    }

    public void setTakeDate(String takeDate) {
        this.takeDate = takeDate;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }
}
