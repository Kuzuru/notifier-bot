package urfu.core.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateTimeValidator implements DateValidator {
  private final DateTimeFormatter timeFormatter =
      DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

  private final DateTimeFormatter dateFormatter =
      DateTimeFormatter.ofPattern("dd.MM.yyyy").withResolverStyle(ResolverStyle.STRICT);

  @Override
  public boolean isDateValid(String dateStr) {
    try {
      LocalDate.parse(dateStr, this.dateFormatter);
    } catch (DateTimeParseException e) {
      return false;
    }

    return true;
  }

  @Override
  public boolean isTimeValid(String dateStr) {
    try {
      LocalDate.parse(dateStr, this.timeFormatter);
    } catch (DateTimeParseException e) {
      return false;
    }

    return true;
  }
}
