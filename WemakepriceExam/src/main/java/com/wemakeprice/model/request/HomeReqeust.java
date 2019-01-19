package com.wemakeprice.model.request;


import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeReqeust {
	@NotNull(message="URL을 입력해주세요")
	private String url;

	@NotNull(message="입력 타입을 입력해주세요")
	private String type;

	@NotNull(message="묶음 갯수를 입력해주세요")
	private int printCnt;
}
