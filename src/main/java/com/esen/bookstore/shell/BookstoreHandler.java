package com.esen.bookstore.shell;

import com.esen.bookstore.service.BookstoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("Bookstore related commands")
@RequiredArgsConstructor
public class BookstoreHandler {

    private final BookstoreService bookstoreService;

    @ShellMethod(key = "create bookstore", value = "Create a new bookstore")
    public void save(String location, Double priceModifier, Double moneyInCashRegister) {
        bookstoreService.save(location, priceModifier, moneyInCashRegister);
    }

    @ShellMethod(key = "list bookstores", value = "list bookstores")
    public String list(){
        return bookstoreService.findAll().stream()
                .map(bookstore -> "ID: %d, priceModifier: %f, moneyInCashRegister: %f, location: %s".formatted(
                        bookstore.getId(),
                        bookstore.getPriceModifier(),
                        bookstore.getMoneyInCashRegister(),
                        bookstore.getLocation()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(key = "delete bookstore", value = "delete bookstore")
    public void deleteBookstore(Long id) {
        bookstoreService.deleteBookstore(id);
    }

    @ShellMethod(value = "Update a bookstore", key = "update bookstore")
    public void updateBook(Long id,
                           @ShellOption(defaultValue = ShellOption.NULL) String location,
                           @ShellOption(defaultValue = ShellOption.NULL) Double priceModifier,
                           @ShellOption(defaultValue = ShellOption.NULL) Double moneyInCashRegister) {
        bookstoreService.updateBookstore(id, location, priceModifier, moneyInCashRegister);
    }

    @ShellMethod(value = "Find prices", key="find prices")
    public String findPrices(Long bookId) {
        return bookstoreService.findPrices(bookId)
                .entrySet()
                .stream()
                .map(entry -> "Bookstore ID: %d, Location: %s, Price: %f Ft".formatted(
                        entry.getKey().getId(),
                        entry.getKey().getLocation(),
                        entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(value = "Get stock", key="get stock")
    public String getStock(Long id) {
        return bookstoreService.getStock(id)
                .entrySet()
                .stream()
                .map(entry -> "%d. %s - %s\t| Copies: %d".formatted(
                        entry.getKey().getId(),
                        entry.getKey().getAuthor(),
                        entry.getKey().getTitle(),
                        entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(value = "Add stock", key = "add stock")
    public void addStock(Long bookstoreId, Long bookId, int amount) {
        bookstoreService.changeStock(bookstoreId, bookId, amount);
    }
}
