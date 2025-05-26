package dev.garage474.mspagamento.usecase;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseUseCaseTest {
    protected static final String MOCK_ERROR = "Mock error";
    protected static final String MOCK_CUSTOMER_CPF = "12345678901";
    protected static final String MOCK_CUSTOMER_EMAIL = "test@example.com";
    protected static final String MOCK_CUSTOMER_PHONE = "81999999999";
    protected static final String MOCK_TRANSACTION_ID = "TRANSACTION_123";
}
