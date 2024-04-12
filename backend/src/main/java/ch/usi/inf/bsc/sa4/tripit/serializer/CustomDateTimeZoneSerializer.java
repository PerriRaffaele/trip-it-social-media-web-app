package ch.usi.inf.bsc.sa4.tripit.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** The type Custom date time zone serializer. */
public class CustomDateTimeZoneSerializer extends StdSerializer<ZonedDateTime> {

  /** Instantiates a new Custom date time zone serializer. */
  public CustomDateTimeZoneSerializer() {
    this(null);
  }

  /**
   * Instantiates a new Custom date time zone serializer.
   *
   * @param t the t
   */
  public CustomDateTimeZoneSerializer(Class<ZonedDateTime> t) {
    super(t);
  }

  @Override
  public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider arg2)
      throws IOException, JsonProcessingException {
    gen.writeString(value.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
  }
}
