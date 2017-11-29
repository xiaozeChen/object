// BookManager.aidl
package com.cxz.aidlservice;
import com.cxz.aidlservice.Book;
// Declare any non-default types here with import statements

interface BookManager {
    List<Book> getBooks();
    void addBook(in Book book);
}
