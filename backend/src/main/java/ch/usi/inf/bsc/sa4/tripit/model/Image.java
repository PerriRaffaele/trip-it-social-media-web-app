package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.ImageDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
public class Image {
  @Id private String id;
  private String idImage;
  private int width;
  private int height;
  private String description;
  private String url;
  private String cropping;

  public Image() {}

  public Image(
      String idImage, int width, int height, String description, String url, String cropping) {
    this.idImage = idImage;
    this.width = width;
    this.height = height;
    this.description = description;
    this.url = url;
    this.cropping = cropping;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIdImage() {
    return idImage;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getDescription() {
    return description;
  }

  public String getUrl() {
    return url;
  }

  public String getCropping() {
    return cropping;
  }

  public void setIdImage(String idImage) {
    this.idImage = idImage;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setCropping(String cropping) {
    this.cropping = cropping;
  }

  public ImageDTO toImageDTO() {
    System.out.println("Image toImageDTO : " + this.id);
    return new ImageDTO(
        this.id, this.idImage, this.width, this.height, this.description, this.url, this.cropping);
  }
}
