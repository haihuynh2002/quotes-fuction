package com.polarbookshop.quotes_function.domain;

public record Quote(
    String content,
    String author,
    Genre genre
) {
}
