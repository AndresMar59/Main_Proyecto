package com.BD;

import java.time.LocalDate;

public class Loan {
    private Producto product;
    private LocalDate loanDate;
//    private LocalDate returnDate;

    public Loan(Producto product) {
        if (product == null) {
            throw new IllegalArgumentException(product + " No es un producto");
        } 
        this.product = product;
        this.loanDate = LocalDate.now();
    }

    public Producto getProducto() { return product; }
    public LocalDate getLoanDate() { return loanDate; }
}

