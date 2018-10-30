$(".languageSelected").click(function(){

	var lang = $(this).attr("value");
	
	var oldURL = window.location.href
	var index = 0;
	var newURL = oldURL;
	index = oldURL.indexOf('?');
	if(index == -1){
	    index = oldURL.indexOf('#');
	}
	if(index != -1){
		var editComputerParamIndex = oldURL.indexOf('computerId');
		if(editComputerParamIndex != -1){
			var param = getUrlParameter('computerId');
			newURL = oldURL.substring(0, index);
			document.location = newURL + "?computerId="+ param + "&lang=" + lang;
		}else{
		    newURL = oldURL.substring(0, index);
			document.location = newURL + "?lang=" + lang;
		}
	}else{
		document.location = newURL + "?lang=" + lang;
	}

});


var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};



