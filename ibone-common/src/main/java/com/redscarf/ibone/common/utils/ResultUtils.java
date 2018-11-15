package com.redscarf.ibone.common.utils;

import com.redscarf.ibone.common.ui.result.ListResult;
import com.redscarf.ibone.common.ui.result.ModelResult;
import com.redscarf.ibone.common.ui.result.Result;
import com.redscarf.ibone.common.ui.result.ResultStatus;

import java.util.Collection;

public class ResultUtils {

    public static ListResult wrapSuccess(long total, Collection rows){
        ListResult listResult = new ListResult(ResultStatus.SUCCESS,total,rows);
        return listResult;
    }

    public static ListResult wrapListFail(String msg){
        return new ListResult(ResultStatus.FAIL.getStatus(),msg);
    }

    public static ModelResult wrapSuccess(Object data){
        return new ModelResult(ResultStatus.SUCCESS,data);
    }

    public static Result wrapSuccess(){
        return new Result(ResultStatus.SUCCESS);
    }

    public static Result wrapFail(){
        return new Result(ResultStatus.FAIL);
    }

    public static Result wrapFail(String msg){
        return new Result(ResultStatus.FAIL.getStatus(),msg);
    }


    public static Result wrapFail(int status,String msg){
        return new Result(status,msg);
    }


}
