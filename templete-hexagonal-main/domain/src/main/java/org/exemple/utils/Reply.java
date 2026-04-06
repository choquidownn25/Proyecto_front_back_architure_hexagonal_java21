package org.exemple.utils;

public record Reply<T>(String status, String message, T data) {
}
