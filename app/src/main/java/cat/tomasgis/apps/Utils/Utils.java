package cat.tomasgis.apps.Utils;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by TomasGiS on 20/7/16.
 */
public class Utils {
    public static String getCurrencySymbol(Locale locale) {
        System.out.println("Locale: " + locale.getDisplayName());
        Currency currency = Currency.getInstance(locale);
        System.out.println("Currency Code: " + currency.getCurrencyCode());
        System.out.println("Symbol: " + currency.getSymbol());
        return currency.getSymbol();
    }
    public static String getCurrencyCode(Locale locale) {
        System.out.println("Locale: " + locale.getDisplayName());
        Currency currency = Currency.getInstance(locale);
        System.out.println("Currency Code: " + currency.getCurrencyCode());
        System.out.println("Symbol: " + currency.getSymbol());
        return currency.getCurrencyCode();
    }
}
