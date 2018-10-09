package hello;

import io.opentracing.ScopeManager;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lightstep.tracer.shared.Options;
import com.lightstep.tracer.jre.JRETracer;

import java.net.MalformedURLException;

@Configuration
public class TracingConfiguration {
    @Bean
    public io.opentracing.Tracer tracer() {
        try {
            Options options = new Options
                    .OptionsBuilder()
                    .withAccessToken("425c9b9734e6cd039b41689aa83937cd")
                    .withVerbosity(4)
                    .withCollectorPort(443)
                    .withCollectorHost("collector-grpc.lightstep.com")
                    .withCollectorProtocol("https")
                    .withComponentName("MySpringApp")
                    .build();

            return new JRETracer(options);
        } catch (MalformedURLException e) {
            System.out.print("Exception instantiating tracer, creating noop tracer.");
            return new Tracer() {
                @Override
                public ScopeManager scopeManager() {
                    return null;
                }

                @Override
                public Span activeSpan() {
                    return null;
                }

                @Override
                public SpanBuilder buildSpan(String s) {
                    return null;
                }

                @Override
                public <C> void inject(SpanContext spanContext, Format<C> format, C c) {

                }

                @Override
                public <C> SpanContext extract(Format<C> format, C c) {
                    return null;
                }
            };
        }
    }
}