package com.tcs.clients.infrastructure.in.messaging;

import com.tcs.clients.application.port.in.ClienteUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClienteMessagingInputAdapterTest {

  @Mock
  private ClienteUseCase clienteUseCase;

  @InjectMocks
  private ClienteMessagingInputAdapter clienteMessagingInputAdapter;

  @Test
  public void clienteMessagingInputAdapterTest_envioClienteRefReturnsClienteId() {
    clienteMessagingInputAdapter.envioClienteRef("1725082786");
    verify(clienteUseCase, times(1)).envioClienteRef("1725082786");
  }

}
