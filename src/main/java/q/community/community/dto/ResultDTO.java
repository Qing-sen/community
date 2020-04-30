package q.community.community.dto;

import lombok.Data;
import q.community.community.exception.CustomizeErrorCode;
import q.community.community.exception.CustomizeException;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorof(Integer code,String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorof(CustomizeErrorCode errorCode) {
        return errorof(CustomizeErrorCode.NO_LOGIN);
    }
    public static ResultDTO errorof(CustomizeException e) {
        return errorof(e.getCode(),e.getMessage()) ;

    }
    public static ResultDTO okof(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功！");
        return resultDTO;
    }


}
