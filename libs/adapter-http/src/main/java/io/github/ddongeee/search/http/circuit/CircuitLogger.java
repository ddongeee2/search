package io.github.ddongeee.search.http.circuit;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CircuitLogger implements RegistryEventConsumer<CircuitBreaker> {
    @Override
    public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
        entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event -> {
            if (event instanceof CircuitBreakerOnStateTransitionEvent) {
                whenStateTransitionEvent(event);
            }
        });
    }

    private void whenStateTransitionEvent(CircuitBreakerEvent event) {
        var transitionEvent = (CircuitBreakerOnStateTransitionEvent) event;
        log.info("서킷 상태가 변경되었습니다. name={}, transition={}",
                transitionEvent.getCircuitBreakerName(),
                transitionEvent.getStateTransition()
        );
    }

    @Override
    public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
        log.info("CIRCUIT - onEntryRemovedEvent");
    }

    @Override
    public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
        log.info("CIRCUIT - onEntryReplacedEvent");
    }
}
