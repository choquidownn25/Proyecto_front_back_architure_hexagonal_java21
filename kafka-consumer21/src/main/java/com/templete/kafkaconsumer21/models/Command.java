package com.templete.kafkaconsumer21.models;

public record Command<T>(String type, Long id, T body) {
}
