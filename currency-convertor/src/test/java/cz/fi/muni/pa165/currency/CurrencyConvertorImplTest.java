package cz.fi.muni.pa165.currency;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {
    private static final Currency CZK = Currency.getInstance("CZK");
    private static final Currency EUR = Currency.getInstance("EUR");

    private CurrencyConvertor convertor;

    @Mock
    private ExchangeRateTable exchangeRateTable;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        convertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException{
        when(exchangeRateTable.getExchangeRate(CZK, EUR)).thenReturn(new BigDecimal("0.01"));
        assertThat(convertor.convert(CZK, EUR, new BigDecimal("0.50"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(convertor.convert(CZK, EUR, new BigDecimal("0.51"))).isEqualTo(new BigDecimal("0.01"));
        assertThat(convertor.convert(CZK, EUR, new BigDecimal("1.50"))).isEqualTo(new BigDecimal("0.02"));
        assertThat(convertor.convert(CZK, EUR, new BigDecimal("1.49"))).isEqualTo(new BigDecimal("0.01"));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        expectedException.expect(IllegalArgumentException.class);
        convertor.convert(null, EUR, new BigDecimal("10.00"));
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        expectedException.expect(IllegalArgumentException.class);
        convertor.convert(CZK, null, new BigDecimal("10.00"));
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        expectedException.expect(IllegalArgumentException.class);
        convertor.convert(CZK, EUR, null);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(CZK, EUR)).thenReturn(null);
        expectedException.expect(UnknownExchangeRateException.class);
        convertor.convert(CZK, EUR, new BigDecimal("10.00"));
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(CZK, EUR)).thenThrow(ExternalServiceFailureException.class);
        expectedException.expect(UnknownExchangeRateException.class);
        convertor.convert(CZK, EUR, new BigDecimal("10.00"));
    }

}
