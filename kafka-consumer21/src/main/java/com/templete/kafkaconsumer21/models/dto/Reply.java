package com.templete.kafkaconsumer21.models.dto;

public record Reply<T>(String status, String message, T data) {
}
