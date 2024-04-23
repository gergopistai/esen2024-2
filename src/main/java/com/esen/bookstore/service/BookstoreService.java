package com.esen.bookstore.service;

import com.esen.bookstore.model.Book;
import com.esen.bookstore.model.Bookstore;
import com.esen.bookstore.repository.BookstoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookstoreService {

    private final BookstoreRepository bookstoreRepository;

    @Transactional
    public void removeBookFromInventories(Book book) {
        bookstoreRepository.findAll()
                .forEach((bookstore -> {
                    bookstore.getInventory().remove(book);
                    bookstoreRepository.save(bookstore);
                }));
    }

    public List<Bookstore> findAll() {
        return bookstoreRepository.findAll();
    }

    public void save(Bookstore bookstore) {
        bookstoreRepository.save(bookstore);
    }

    public void delete(Long id) {
        var bookstore = bookstoreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find bookstore"));
        bookstoreRepository.delete(bookstore);
    }

    public void updateBookstore(Long id, String location, Double priceModifier, Double moneyInCashRegister) {
        if (Stream.of(location, priceModifier, moneyInCashRegister).allMatch(Objects::isNull)) {
            throw new UnsupportedOperationException("There is nothing to update");
        }

        var bookstore = bookstoreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find bookstore"));

        if (location != null)
            bookstore.setLocation(location);
        if (priceModifier != null)
            bookstore.setPriceModifier(priceModifier);
        if (moneyInCashRegister != null)
            bookstore.setMoneyInCashRegister(moneyInCashRegister);

        bookstoreRepository.save(bookstore);
    }
}
