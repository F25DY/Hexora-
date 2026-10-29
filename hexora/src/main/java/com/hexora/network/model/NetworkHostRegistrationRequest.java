package com.hexora.network.model;

import java.util.List;

public record NetworkHostRegistrationRequest(String name, String address, List<Integer> ports) {
}
