package ch.usi.inf.bsc.sa4.tripit.converter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/** The type Zoned date time read converter. */
@ReadingConverter
public class ZonedDateTimeReadConverter implements Converter<String, ZonedDateTime> {
  @Override
  public ZonedDateTime convert(String data) {
    return ZonedDateTime.parse(data, DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }
}
