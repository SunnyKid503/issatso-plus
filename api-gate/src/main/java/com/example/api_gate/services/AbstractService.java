package com.example.api_gate.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService<TResult> {
    public abstract TResult Behave();
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
