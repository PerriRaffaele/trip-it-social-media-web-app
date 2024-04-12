package ch.usi.inf.bsc.sa4.tripit.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.ZonedDateTime;

/** The type Custom date time zone deserializer. */
public class CustomDateTimeZoneDeserializer extends StdDeserializer<ZonedDateTime> {

  /** Instantiates a new Custom date time zone deserializer. */
  public CustomDateTimeZoneDeserializer() {
    this(null);
  }

  /**
   * Instantiates a new Custom date time zone deserializer.
   *
   * @param vc the vc
   */
  public CustomDateTimeZoneDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public ZonedDateTime deserialize(JsonParser jsonparser, DeserializationContext context)
      throws IOException, JsonProcessingException {
    final String date = jsonparser.getText();
    return ZonedDateTime.parse(date);
  }
}
