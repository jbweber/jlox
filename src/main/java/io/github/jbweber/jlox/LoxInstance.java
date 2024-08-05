package io.github.jbweber.jlox;

import java.util.HashMap;
import java.util.Map;

class LoxInstance {
    private LoxClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    LoxInstance(final LoxClass klass) {
        this.klass = klass;
    }

    Object get(final Token name) {
        if (fields.containsKey(name.getLexeme())) {
            return fields.get(name.getLexeme());
        }

        LoxFunction method = klass.findMethod(name.getLexeme());
        if (method != null) {
            return method.bind(this);
        }

        throw new RuntimeError(name, "Undefined property '%s'.".formatted(name.getLexeme()));
    }

    void set(Token name, Object value) {
        fields.put(name.getLexeme(), value);
    }

    @Override
    public String toString() {
        return "%s instance".formatted(klass.name);
    }
}
