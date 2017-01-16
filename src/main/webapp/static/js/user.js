function parseUserId() {
	var searchStr = window.location.search;
	if (searchStr.length === 0) {
		return -1;
	}
	var param = 
		searchStr.slice(1).split('&')
		.map(str => str.split('='))
		.find(v => v[0] === 'id');
	if (!param) {
		return -1;
	}
	return param[1] - 0;
}

function fetch(id) {
	console.log('fetch uri:', '/mall/users/' + id);
	$.get( "/mall/users/" + id)
	 .done(function( r ) {
		 
		console.log('done:', r);
		$('.alert.alert-danger').addClass('hide');
	    $('#user_id').val(r.id);
	    $('#nick_name').text(r.nickName);
	    $('#email').text(r.email);
	    $('#gender').text(r.gender);
	    $('#address').text(r.defaultAddress.address);
	    $('#telephone').text(r.defaultAddress.telephone);
	    $('#password').text(r.password);
	    
	    $('.form-horizontal.hide').removeClass('hide');
	    
	    $('#delete').click(function() {
	    	
	    	
	    	
	    });
	})
	.fail(function( xhr, status, errorThrown ){
		var message = xhr.status + ' ' + xhr.responseJSON.errorMessage;
		$('.form-horizontal').addClass('hide');
		$('.alert.alert-danger').text(message).removeClass('hide');
	});
}


$('#myModal').on('show.bs.modal', function (e) {
	  var text = $('#myModal .modal-body').text().replace('xxx', $('#nick_name').text());
	  $('#myModal .modal-body').text(text);
});

$('#confirm').click(function(){
	
	console.log('delete....', $('#user_id').val());
	
});

function getUser() {
	var id = parseUserId();
	if (id > 0) {
		fetch(id);
		console.log('xxx');
	} else {
		console.err('wrong user id:', id);
	}
}