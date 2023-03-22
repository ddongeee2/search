package io.github.ddongeee.search.port.output;

import lombok.Getter;
import io.github.ddongeee.search.contract.RestApiType;

@Getter
public class Clause {
    private final RestApiType restApiType;

    public Clause(RestApiType restApiType) {
        this.restApiType = restApiType;
    }
}
