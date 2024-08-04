package io.github.jbweber.jlox;

import java.util.List;

class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Environment closure;

    LoxFunction(final Stmt.Function declaration, Environment closure) {
        this.closure = closure;
        this.declaration = declaration;
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment env = new Environment(closure);

        for (int i = 0; i < declaration.params.size(); ++i) {
            env.define(declaration.params.get(i).getLexeme(), arguments.get(i));
        }

        try {
            interpreter.executeBlock(declaration.body, env);
        } catch (Return returnValue) {
            return returnValue.value;
        }

        return null;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.getLexeme() + ">";
    }
}
