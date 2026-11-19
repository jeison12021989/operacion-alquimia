package com.alquimia.domain.model;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
public class MessageReconstructor {
    public static String reconstruct(List<String> fragments) {
        List<String> validWords = fragments.stream()
            .filter(Objects::nonNull)
            .map(String::trim)
            .filter(word -> !word.isEmpty())
            .collect(Collectors.toList());
        return String.join(" ", validWords);
    }
}