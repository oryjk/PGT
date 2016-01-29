package com.pgt.pawn.bean;

/**
 * Created by hd on 16-1-28.
 */
public class OnlinePawnCustom extends OnlinePawn {

    private OnlinePawn onlinePawn;

    private String smsCode;

    private String error;


    public void setOnlinePawn(OnlinePawn onlinePawn){ this.onlinePawn = onlinePawn; }

    public OnlinePawn getOnlinePawn(){ return onlinePawn;}

    public void setSmsCode(String smsCode){ this.smsCode = smsCode; }

    public String getSmsCode(){ return smsCode;}

    public void setError(String error){ this.error = error; }

    public String getError(){ return error;}


}
