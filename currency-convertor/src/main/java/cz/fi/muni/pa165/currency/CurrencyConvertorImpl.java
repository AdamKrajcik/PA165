package cz.fi.muni.pa165.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        logger.trace(String.format("Invocation of convert with params (%s, %s, %s)",
                sourceCurrency, targetCurrency, sourceAmount));

        if (sourceCurrency == null) {
            throw new IllegalArgumentException("sourceCurrency is null.");
        }

        if (targetCurrency == null) {
            throw new IllegalArgumentException("targetCurrency is null.");
        }

        if (sourceAmount == null) {
            throw new IllegalArgumentException("sourceAmount is null.");
        }

        try {
            BigDecimal rate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);

            if (rate == null) {
                logger.warn(String.format("Unknown exchange rate with currencies (%s, %s)",
                        sourceCurrency, targetCurrency));
                throw new UnknownExchangeRateException("Unknown exchange rate.");
            }

            return rate.multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN);
        } catch (ExternalServiceFailureException ex) {
            logger.error("Error connecting to exchange rate table", ex);
            throw new UnknownExchangeRateException("ExternalServiceFailureException", ex);
        }
    }

}
