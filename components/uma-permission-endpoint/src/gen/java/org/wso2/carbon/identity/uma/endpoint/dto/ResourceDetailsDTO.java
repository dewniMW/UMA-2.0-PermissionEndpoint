package org.wso2.carbon.identity.uma.endpoint.dto;

import java.util.ArrayList;

import io.swagger.annotations.*;


@ApiModel(description = "")
public class ResourceDetailsDTO extends ArrayList<ResourceDetailsInnerDTO> {
  

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResourceDetailsDTO {\n");
    sb.append("  " + super.toString()).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
