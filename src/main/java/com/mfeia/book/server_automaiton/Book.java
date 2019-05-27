package com.mfeia.book.server_automaiton;

import net.sf.json.JSONObject;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Book {
    private String bookId;
    private String bookName;
    private String authorName;
    private Pattern bookImg;
    private String categoryColor;
    public Book() {
    }

    public Book(String bookId,
                String bookName,
                String authorName,
                String categoryColor
    ) {
        this.bookName = bookName.trim();
        this.bookId = bookId;
        this.authorName = authorName;
        this.bookImg =AutomationUtils.cast(AutomationUtils.getCheckRules(AutomationUtils.BOOK_COVER,
                bookId));
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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Pattern getBookImg() {
        return bookImg;
    }

    public void setBookImg(Pattern bookImg) {
        this.bookImg = bookImg;
    }


    @Override
    public String toString() {
        return "Book{" +
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
        Book books = (Book) o;
        return bookId == books.bookId &&
                Objects.equals(bookName, books.bookName) &&
                Objects.equals(authorName, books.authorName) &&
                Objects.equals(bookImg, books.bookImg);
    }

    public boolean equals(JSONObject jsObject) {
        if (jsObject == null || jsObject.isEmpty()) return false;

        try {
            String bookImg;
            if (jsObject.containsKey("bookImg")) {
                bookImg = jsObject.getString("bookImg");
            } else {
                bookImg = jsObject.getString(AutomationUtils.BOOK_COVER);
            }
            return this.bookName.equals(
                    jsObject.getString(AutomationUtils.BOOK_NAME)) &&
                    this.bookId .equals(
                            jsObject.getString(AutomationUtils.BOOK_ID)) &&
                    this.authorName.equals(
                            jsObject.getString(AutomationUtils.BOOK_AUTHOR_NAME)) &&
                    this.bookImg.matcher(bookImg).matches();

        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public int hashCode() {
        return Objects.hash(bookId, bookName, authorName, bookImg);
    }
}
