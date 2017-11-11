package org.wso2.carbon.identity.uma.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class PTFailDTO  {
  
  
  @NotNull
  private String error = null;
  
  
  private String errorDescription = null;
  
  
  private String errorUri = null;

  
  /**
   * A single error code.
   **/
  @ApiModelProperty(required = true, value = "A single error code.")
  @JsonProperty("error")
  public String getError() {
    return error;
  }
  public void setError(String error) {
    this.error = error;
  }

  
  /**
   * Human-readable text providing additional information.
   **/
  @ApiModelProperty(value = "Human-readable text providing additional information.")
  @JsonProperty("errorDescription")
  public String getErrorDescription() {
    return errorDescription;
  }
  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }

  
  /**
   * A URI identifying a human-readable web page with information about the error.
   **/
  @ApiModelProperty(value = "A URI identifying a human-readable web page with information about the error.")
  @JsonProperty("errorUri")
  public String getErrorUri() {
    return errorUri;
  }
  public void setErrorUri(String errorUri) {
    this.errorUri = errorUri;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class PTFailDTO {\n");
    
    sb.append("  error: ").append(error).append("\n");
    sb.append("  errorDescription: ").append(errorDescription).append("\n");
    sb.append("  errorUri: ").append(errorUri).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
