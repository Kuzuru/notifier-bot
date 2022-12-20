package urfu.core.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateTimeValidator {
  public boolean isDateValid(String dateStr) {
    DateTimeFormatter dateFormatter =
        DateTimeFormatter.ofPattern("dd.MM.yyyy").withResolverStyle(ResolverStyle.STRICT);

    try {
      LocalDate.parse(dateStr, dateFormatter);
    } catch (DateTimeParseException e) {
      return false;
    }

    return true;
  }

  public boolean isTimeValid(String dateStr) {
    DateTimeFormatter timeFormatter =
        DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

    try {
      LocalDate.parse(dateStr, timeFormatter);
    } catch (DateTimeParseException e) {
      return false;
    }

    return true;
  }
}
