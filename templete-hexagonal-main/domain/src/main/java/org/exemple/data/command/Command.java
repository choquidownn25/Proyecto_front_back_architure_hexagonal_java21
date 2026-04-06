package org.exemple.data.command;

public record Command<T>(String type, Long id, T body) {
}
