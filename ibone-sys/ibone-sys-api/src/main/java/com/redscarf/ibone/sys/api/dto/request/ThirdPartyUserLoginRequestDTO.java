package com.redscarf.ibone.sys.api.dto.request;

import com.redscarf.ibone.sys.api.dto.ThirdPartyName;
import lombok.Data;

@Data
public class ThirdPartyUserLoginRequestDTO {
    private String id;

    private ThirdPartyName thirdPartyName;

}
