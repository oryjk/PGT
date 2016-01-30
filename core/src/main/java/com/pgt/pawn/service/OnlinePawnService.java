package com.pgt.pawn.service;

import com.pgt.pawn.bean.OnlinePawn;

import java.util.List;

/**
 * Created by hd on 16-1-28.
 */
public interface OnlinePawnService {

	Integer add (OnlinePawn onlinePawn);

	OnlinePawn select (Integer id);

	Integer update (OnlinePawn onlinePawn);

	Integer delete (Integer id);

	List<OnlinePawn> findByPhoneNumber (String phoneNumber);

}
