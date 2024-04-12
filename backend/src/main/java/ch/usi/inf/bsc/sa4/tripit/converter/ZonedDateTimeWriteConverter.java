package ch.usi.inf.bsc.sa4.tripit.converter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/** The type Zoned date time write converter. */
@WritingConverter
public class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, String> {
  @Override
  public String convert(ZonedDateTime source) {
    return source.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }
}
