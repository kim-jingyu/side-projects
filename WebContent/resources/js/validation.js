function CheckAddCorporation() {
	
	var copId = document.getElementById("copId");
	var cname = document.getElementById("cname");
	var certification = document.getElementById("certification");
	var foundationDate = document.getElementById("foundationDate");


	
	//기업 아이디 체크
	
	if(!check(/^\d{3}-\d{2}$/, copId,"ex)214-81 과 같은 형식으로 작성하세요. 단, 해외기업일 경우는 000-00으로 입력하세요."))
		return false;
	
	//기업명 체크
	
	if(cname.value.length <1) {
		alert("최소 1자이상은 입력하셔야 합니다.");
		cname.select();
		cname.focus();
		return false;
	}
	
	//기업 설립일자 체크
	if(foundationDate.value.length <1 || foundationDate.value.lenght >8 || isNaN(foundationDate.value)){
		alert("숫자만 입력하세요\n 형식:20200510");
		foundationDate.select();
		foundationDate.focus();
		return false;
	}
	
	//자격증 형식 체크
	if (certification.value.length < 1){
		alert("입력할 자격증이 없는경우 '없음'이라고 입력해주세요");
		certification.select();
		certification.focus();
		return false;
	}
	
	function check(regExp, e, msg){
		if(regExp.test(e.value)){
			return true;
		}
		alert(msg);
		e.select();
		e.focus();
		return false;
	}
	document.newCorporation.submit()
}