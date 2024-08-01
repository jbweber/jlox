package io.github.jbweber.jlox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString(of ={"type", "lexeme", "literal"})
public class Token {
    private final TokenType type;
    private final String lexeme;
    private final Object literal;
    private final int line;
}
