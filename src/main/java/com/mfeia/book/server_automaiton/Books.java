package com.mfeia.book.server_automaiton;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Books {
    private long bookId;
    private String bookName;
    private String authorName;
    private String bookImg;
    private String categoryColor;

    public Books() {
    }

    public Books(long bookId,
                 String bookName,
                 String authorName,
                 String categoryColor
    ) {
        this.bookName = bookName;
        this.bookId = bookId;
        this.authorName = authorName;
        this.bookImg = AutomationUtils.getCheckRules(AutomationUtils.BOOK_COVER,
                bookId).toString();
        this.categoryColor = categoryColor;
    }

    public Map<String, Object> getCheckMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("bookId", this.bookId);
        map.put("bookName", this.bookName);
        map.put("authorName", this.authorName);
        map.put("introduction", AutomationUtils.getCheckRules(
                AutomationUtils.BOOK_INTRO
        ));
        map.put("categoryName", AutomationUtils.getCheckRules(
                AutomationUtils.BOOK_CATEGORY_NAME
        ));
        map.put("wordCount", AutomationUtils.getCheckRules(
                AutomationUtils.BOOK_WORD_COUNT
        ));
        map.put("categoryColor", this.categoryColor);
        return map;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }


    @Override
    public String toString() {
        return "Books{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", bookImg='" + bookImg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return bookId == books.bookId &&
                Objects.equals(bookName, books.bookName) &&
                Objects.equals(authorName, books.authorName) &&
                Objects.equals(bookImg, books.bookImg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, bookName, authorName, bookImg);
    }
}
