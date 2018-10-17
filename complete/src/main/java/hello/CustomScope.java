package hello;

import io.opentracing.Scope;
import io.opentracing.Span;

public class CustomScope implements Scope {
    private final CustomScopeManager scopeManager;
    private final Span wrapped;
    private final boolean finishOnClose;
    private final CustomScope toRestore;

    CustomScope(CustomScopeManager scopeManager, Span wrapped, boolean finishOnClose) {
        this.scopeManager = scopeManager;
        this.wrapped = wrapped;
        this.finishOnClose = finishOnClose;
        this.toRestore = scopeManager.tlsScope.get();
        scopeManager.tlsScope.set(this);
    }

    public void close() {
        if (this.scopeManager.tlsScope.get() == this) {
            if (this.finishOnClose) {
                this.wrapped.finish();
            }

            this.scopeManager.tlsScope.set(this.toRestore);
        }
    }

    public Span span() {
        return this.wrapped;
    }
}
