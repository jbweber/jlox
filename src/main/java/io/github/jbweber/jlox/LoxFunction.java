package io.github.jbweber.jlox;

import java.util.List;

class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Environment closure;

    private final boolean isInitializer;

    LoxFunction(final Stmt.Function declaration, Environment closure, boolean isInitializer) {
        this.closure = closure;
        this.declaration = declaration;
        this.isInitializer = isInitializer;
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    LoxFunction bind(LoxInstance instance) {
        Environment env = new Environment(closure);
        env.define("this", instance);
        return new LoxFunction(declaration, env, isInitializer);
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
            if (isInitializer) {
                return closure.getAt(0, "this");
            }
            
            return returnValue.value;
        }

        if (isInitializer) {
            return closure.getAt(0, "this");
        }

        return null;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.getLexeme() + ">";
    }
}
