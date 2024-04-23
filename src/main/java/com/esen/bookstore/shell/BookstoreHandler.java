package com.esen.bookstore.shell;

import com.esen.bookstore.model.Bookstore;
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

    @ShellMethod(value = "Create bookstore", key = "create bookstore")
    public void createBookstore(String location, Double priceModifier, Double moneyInCashRegister) {
        bookstoreService.save(Bookstore.builder()
                .location(location)
                .priceModifier(priceModifier)
                .moneyInCashRegister(moneyInCashRegister)
                .build());
    }

    @ShellMethod(value = "List bookstores", key = "list bookstores")
    public String listBookstores() {
        return bookstoreService.findAll().stream()
                .map(bookstore -> "ID: %s\t Location: %s\t Price Modifier: %f\t Money: %f Ft".formatted(
                        bookstore.getId(), bookstore.getLocation(), bookstore.getPriceModifier(), bookstore.getMoneyInCashRegister())).collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(value = "Delete bookstore", key = "delete bookstore")
    public void deleteBookstore(Long id) {
        bookstoreService.delete(id);
    }

    @ShellMethod(value = "Update bookstore", key = "update bookstore")
    public void updateBookstore(Long id,
                                @ShellOption(defaultValue = ShellOption.NULL) String location,
                                @ShellOption(defaultValue = ShellOption.NULL) Double priceModifier,
                                @ShellOption(defaultValue = ShellOption.NULL) Double moneyInCashRegister) {
        bookstoreService.updateBookstore(id, location, priceModifier, moneyInCashRegister);
    }
}
