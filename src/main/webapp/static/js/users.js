function getUsers() {
	
	$.get( "/mall/users")
	 .done(function( r ) {
		
		var tbody = $('table > tbody').clone();
		tbody.find('tr').remove();
		var tr = $('table > tbody > tr');
		
		r.forEach(function(user) {
			tr.clone().find('td').each(function(index, elem) {
				$(elem).text(user[['id', 'nickName', 'gender', 'email'][index]]);
			}).parent()
			  .click(function() {
				   window.location.href = '/mall/static/user.html?id=' + user.id;
			   })
			  .mouseenter(function() {
				  $(this).addClass('active');
			  })
			  .mouseleave(function() {
				  $(this).removeClass('active');
			  })
			   .appendTo(tbody);
		});
		
		$('table > tbody').remove();
		$('table').append(tbody);
		
	 })
	.fail(function( xhr, status, errorThrown ){
		var message = xhr.status + ' ' + xhr.responseJSON.errorMessage;
		$("<div>")
		.text("message")
		.addClass("alert alert-danger")
		. appendTo(".container");
	});
	
}