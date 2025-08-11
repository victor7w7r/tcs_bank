package com.tcs.transactions.cuenta.application.port.out;

public interface CuentaMessagingPort {
  Long sendIdReceiveRef(String identificacion);
}
