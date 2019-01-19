/*
 * Api documentation
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.getfood.data.swagger.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Family
 */

public class Family {

  @SerializedName("_id")
  private String _id = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("qrCode")
  private String qrCode = null;

  @SerializedName("users")
  private List<User> users = null;

  @SerializedName("createdBy")
  private User createdBy = null;
  public Family _id(String _id) {
    this._id = _id;
    return this;
  }

  

  /**
  * Get _id
  * @return _id
  **/
  @Schema(description = "")
  public String getId() {
    return _id;
  }
  public void setId(String _id) {
    this._id = _id;
  }
  public Family name(String name) {
    this.name = name;
    return this;
  }

  

  /**
  * Get name
  * @return name
  **/
  @Schema(example = "John Doe Family", required = true, description = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Family qrCode(String qrCode) {
    this.qrCode = qrCode;
    return this;
  }

  

  /**
  * Get qrCode
  * @return qrCode
  **/
  @Schema(description = "")
  public String getQrCode() {
    return qrCode;
  }
  public void setQrCode(String qrCode) {
    this.qrCode = qrCode;
  }
  public Family users(List<User> users) {
    this.users = users;
    return this;
  }

  public Family addUsersItem(User usersItem) {
    if (this.users == null) {
      this.users = new ArrayList<User>();
    }
    this.users.add(usersItem);
    return this;
  }

  /**
  * Get users
  * @return users
  **/
  @Schema(description = "")
  public List<User> getUsers() {
    return users;
  }
  public void setUsers(List<User> users) {
    this.users = users;
  }
  public Family createdBy(User createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  

  /**
  * Get createdBy
  * @return createdBy
  **/
  @Schema(description = "")
  public User getCreatedBy() {
    return createdBy;
  }
  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Family family = (Family) o;
    return Objects.equals(this._id, family._id) &&
        Objects.equals(this.name, family.name) &&
        Objects.equals(this.qrCode, family.qrCode) &&
        Objects.equals(this.users, family.users) &&
        Objects.equals(this.createdBy, family.createdBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_id, name, qrCode, users, createdBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Family {\n");

    sb.append("    _id: ").append(toIndentedString(_id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    qrCode: ").append(toIndentedString(qrCode)).append("\n");
    sb.append("    users: ").append(toIndentedString(users)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}