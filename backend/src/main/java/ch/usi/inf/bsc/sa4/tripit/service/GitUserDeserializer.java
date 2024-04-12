package ch.usi.inf.bsc.sa4.tripit.service;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.CreateUserDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

/** Deserializer for Gitlab users. */
public class GitUserDeserializer extends StdDeserializer<CreateUserDTO> {
  /** Initializes a git user deserializer. */
  public GitUserDeserializer() {
    this(null);
  }

  /**
   * Initializes a git user deserializer with the given vc
   *
   * @param vc the vc of the deserializer
   */
  protected GitUserDeserializer(Class<?> vc) {
    super(vc);
  }

  /**
   * Returns a CreateUserDTO representing the git user in the context.
   *
   * @param jp Parsed used for reading JSON content
   * @param ctxt Context that can be used to access information about this deserialization activity.
   * @return A CreateUserDTO representing the git user
   * @throws IOException
   */
  @Override
  public CreateUserDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    final JsonNode node = jp.getCodec().readTree(jp);
    final String name = node.get("name").asText();
    final String gitlabId = node.get("id").asText();
    final String bio = node.get("bio").asText();
    final String email = node.get("email").asText();
    final String picturePath = node.get("avatar_url").asText();
    final String username = node.get("username").asText();
    return new CreateUserDTO(name, gitlabId, bio, email, picturePath, username);
  }
}
