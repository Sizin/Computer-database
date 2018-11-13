$( document ).ready(function() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var range = url.searchParams.get("range");
	
    
    $("#range" + range).addClass("active");
});

//On load
$(function() {
    // Default: hide edit mode
    $(".editMode").hide();
    
    // Click on "selectall" box
    $("#selectall").click(function () {
        $('.cb').prop('checked', this.checked);
    });

    // Click on a checkbox
    $(".cb").click(function() {
        if ($(".cb").length == $(".cb:checked").length) {
            $("#selectall").prop("checked", true);
        } else {
            $("#selectall").prop("checked", false);
        }
        if ($(".cb:checked").length != 0) {
            $("#deleteSelected").enable();
        } else {
            $("#deleteSelected").disable();
        }
    });

});

// Function setCheckboxValues
(function ( $ ) {

    $.fn.setCheckboxValues = function(formFieldName, checkboxFieldName) {

        var str = $('.' + checkboxFieldName + ':checked').map(function() {
            return this.value;
        }).get().join();
        
        $(this).attr('value',str);
        
        return this;
    };

}( jQuery ));

//Set result per page
$(".pageRange").click(function() {
	var range = $(this).attr("value");
	document.location =  "/CdbSinan/Dashboard?range="+range;
	
	
	
});

$(".page-item").click(function(){
	var page = $(this).attr("value");
	document.location =  "/CdbSinan/Dashboard?page="+page;
});

$("#firstPage").click(function(){
	document.location = "/CdbSinan/Dashboard?page=1";
});

$("#nextPage").click(function(){
	var currentPage = $("#currentPage").attr("value");
	currentPage = parseInt(currentPage);
	var pageToGo = currentPage + 1;
	document.location = "/CdbSinan/Dashboard?page=" + pageToGo;
});

$("#previousPage").click(function(){
	var currentPage = $("#currentPage").attr("value");
	currentPage = parseInt(currentPage);
	var pageToGo = currentPage - 1;
	document.location = "/CdbSinan/Dashboard?page=" + pageToGo;
});

$("#lastPage").click(function(){
	var lastPage = $("#lastPage").attr("value");
	document.location = "/CdbSinan/Dashboard?page=" + lastPage;
});




// Function toggleEditMode
(function ( $ ) {

    $.fn.toggleEditMode = function() {
        if($(".editMode").is(":visible")) {
            $(".editMode").hide();
            $("#editComputer").text("Edit");
        }
        else {
            $(".editMode").show();
            $("#editComputer").text("View");
        }
        return this;
    };

}( jQuery ));


// Function delete selected: Asks for confirmation to delete selected computers, then submits it to the deleteForm
(function ( $ ) {
    $.fn.deleteSelected = function() {
        if (confirm("Are you sure you want to delete the selected computers?")) { 
            $('#deleteForm input[name=selection]').setCheckboxValues('selection','cb');
            $('#deleteForm').submit();
        }
    };
}( jQuery ));



//Event handling
//Onkeydown
$(document).keydown(function(e) {

    switch (e.keyCode) {
        //DEL key
        case 46:
            if($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
                $.fn.deleteSelected();
            }   
            break;
        //E key (CTRL+E will switch to edit mode)
        case 69:
            if(e.ctrlKey) {
                $.fn.toggleEditMode();
            }
            break;
    }
});
