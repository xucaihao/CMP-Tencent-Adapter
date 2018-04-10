package com.cmp.tencentadapter.common;


import static com.cmp.tencentadapter.common.ErrorEnum.ERR_DEFAULT_CODE;

public class TencentException extends RuntimeException {

    private ErrorEnum errorEnum;

    public TencentException(ErrorEnum errorEnum) {
        super(errorEnum.getDesc());
        this.errorEnum = errorEnum;
    }

    public static TencentException failure() {
        return new TencentException(ERR_DEFAULT_CODE);
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public void setErrorEnum(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }
}
