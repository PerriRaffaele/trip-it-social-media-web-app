// package ch.usi.inf.bsc.sa4.tripit.service;
//
// import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.springframework.test.util.AssertionErrors.assertNotNull;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Nested;
// import org.junit.jupiter.api.Test;
//
// @DisplayName("Password Hashing Service Tests")
// public class PasswordHashingServiceTest {
//   private PasswordHashingService phs;
//
//   @Nested
//   class hashingPasswords {
//     @DisplayName("Hash the password and match the password")
//     @Test
//     void hashingAndMatching() {
//       phs = PasswordHashingService.getInstance();
//       try {
//         phs.hashPassword(null);
//       } catch (IllegalArgumentException e) {
//         assertThat(e).hasMessage("Password cannot be null.");
//       }
//       String pass = "helloWorld";
//       String hashed = phs.hashPassword(pass);
//       String falsePass = "HELLOWORLD";
//       assertNotNull("hashed successfully", hashed);
//       assertEquals(phs.passwordMatches(pass, hashed), true);
//       assertEquals(phs.passwordMatches(falsePass, hashed), false);
//     }
//   }
// }
