package com.hexora.network.model;

import java.util.List;

public record NetworkScanDeletionRequest(List<Long> scanIds) {
}
