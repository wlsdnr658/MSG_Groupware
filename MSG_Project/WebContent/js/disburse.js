function inputNumber(inputMoney){
	inputMoney.value = comma(uncomma(inputMoney.value));
}
function comma(str){
	str = String(str);
	return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,');
}
function uncomma(str){
	str = String(str);
	return str.replace(/[^\d]+/g,'');
}
function calTotal(money){
	money.value = comma(uncomma(money.value));
	var total = Number( ($("#myTotal").val()).replace(",",""));
	
	var reTotal = total + Number(money.value.replace(",",""));
	$("#myTotal").val(comma(uncomma(reTotal)));
	
}
