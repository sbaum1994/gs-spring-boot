package hello;

import io.opentracing.Scope;
import io.opentracing.ScopeManager;
import io.opentracing.Span;

public class CustomScopeManager implements ScopeManager {
    final ThreadLocal<CustomScope> tlsScope = new ThreadLocal();
    private final String name;

    CustomScopeManager(String name) {
        this.name = name;
    }

    public Scope activate(Span span, boolean finishOnClose) {
        return new CustomScope(this, span, finishOnClose);
    }

    public Scope active() {
        System.out.print("Using custom scope manager!");
        return (Scope)this.tlsScope.get();
    }
}

